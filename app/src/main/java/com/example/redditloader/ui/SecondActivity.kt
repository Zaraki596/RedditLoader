package com.example.redditloader.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.redditloader.R
import com.example.redditloader.utils.load
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        getImageUrl()?.let { ivSecondScreen.load(it) }
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getImageUrl(): String? = intent.getStringExtra(KEY_SECOND_ACTIVITY)

    companion object {
        const val KEY_SECOND_ACTIVITY = "key_second"
    }
}