package com.example.redditloader.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redditloader.data.model.RedditResponse
import com.example.redditloader.data.model.State
import com.example.redditloader.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val mainRepo: MainRepository) : ViewModel() {

    private val _redditImageLiveData = MutableLiveData<State<RedditResponse>>()

    val redditImageLiveData: LiveData<State<RedditResponse>> = _redditImageLiveData

    fun getImages() {
        _redditImageLiveData.value = State.loading()
        viewModelScope.launch() {
            val mainResponse = mainRepo.getImages()
            if (mainResponse.data.children.isNotEmpty()) {
                Log.d(TAG, "getImages: $mainResponse")
                _redditImageLiveData.value = State.success(mainResponse)
            } else {
                _redditImageLiveData.value = State.error("No Data Available")
            }
        }
    }

    companion object {
        private const val TAG = "VIEWMODEL"
    }
}

/*
*  withContext(Dispatchers.Main) {

                mainResponse.let {
                    if (it.data.children.isNotEmpty()) {
                        Log.d(TAG, "getImages: $mainResponse")
                        _redditImageLiveData.value = State.success(mainResponse)
                    } else {
                        _redditImageLiveData.value = State.error("No Data Available")
                    }
                }
            }*/