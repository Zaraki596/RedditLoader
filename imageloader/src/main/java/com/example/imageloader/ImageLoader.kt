package com.example.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.Collections.synchronizedMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ImageLoader {
    /*
    *We are maintaining Cache here, and to use the cache we use string as the file key and
    * Bitmap as the value, We need to have certain memory so that it doesn't eat up all the memory
    * */
    private const val TAG = "IMAGELOADER"
    private val cacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
    private val bitMapCache: LruCache<String, Bitmap>

    private val executorService: ExecutorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    /*To handle the duplicate request we are using weakHashmap this will help to store all the
    * request details*/
    private val imageViewMap = synchronizedMap(WeakHashMap<ImageView, String>())

    init {
        bitMapCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap?): Int {
                return bitmap?.byteCount?.div(1024) ?: 0
            }
        }
    }

    fun loadImage(imageView: ImageView, imageUrl: String) {
        require(imageView != null) {
            "ImageLoader:load - ImageView should not be null."
        }
        require(imageUrl != null && imageUrl.isNotEmpty()) {
            "ImageLoader:load - Image Url should not be empty"
        }
        imageView.setImageResource(0)
        imageViewMap[imageView] = imageUrl
        //Checking whether image is present in the cache if it is then we are setting image directly
        val bitmap = checkImageInCache(imageUrl)
        bitmap?.let {
            updateImageView(imageView, it)
        }
        imageView.tag = imageUrl
        executorService.submit {
            val bitmap = downloadImage(imageUrl)
            bitmap?.let {
                if (imageView.tag == imageUrl) {
                    updateImageView(imageView, bitmap)
                }
                bitMapCache.put(imageUrl, bitmap)
            }
        }
    }

    private fun updateImageView(imageView: ImageView, bitmap: Bitmap) {
        uiHandler.post {
            imageView.setImageBitmap(bitmap)
        }
    }

    //    To check whether the given image is available in cache or not
    @Synchronized
    private fun checkImageInCache(imageUrl: String): Bitmap? = bitMapCache.get(imageUrl)

    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(url)
            Log.d(TAG, "downloadImage: $url")
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }
}