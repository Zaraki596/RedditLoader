package com.example.redditloader.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.redditloader.R
import com.example.redditloader.data.model.State
import com.example.redditloader.data.network.ApiHelper
import com.example.redditloader.data.network.RetrofitBuilder
import com.example.redditloader.ui.viewmodel.MainViewModel
import com.example.redditloader.ui.viewmodel.ViewModelFactory
import com.example.redditloader.utils.gone
import com.example.redditloader.utils.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val mRedditListAdapter = RedditListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupViewModel()
        viewModel.getImages()
        setUpObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.redditImageApiService))
        ).get(MainViewModel::class.java)
    }

    private fun initViews() {
        recyclerImage.adapter = mRedditListAdapter
    }

    private fun setUpObservers() {
        viewModel.redditImageLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> progressBar.visible()
                is State.Success -> {
                    progressBar.gone()
                    Log.d(Companion.TAG, "setUpObservers: ${state.data.data.children}")
                    mRedditListAdapter.submitList(state.data.data.children)
                }
                is State.Error -> {
                    progressBar.gone()
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        private const val TAG = "MAINACTIVITY"
    }
}