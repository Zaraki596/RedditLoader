package com.example.redditloader.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditResponse(
    val kind: String = "",
    val data: DataResponse
) : Parcelable

@Parcelize
data class DataResponse(
    val children: List<Children>
) : Parcelable

@Parcelize
data class Children(
    val kind: String = "",
    val data: InnerData
) : Parcelable

@Parcelize
data class InnerData(
    val url: String = ""
) : Parcelable
