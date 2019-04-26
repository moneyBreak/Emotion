package com.example.gesang.emotion.model.remote

import android.os.Parcel
import android.os.Parcelable
import okhttp3.Response

class BaseResponse <out T>(val status: Int,val msg:String,val data:T){
}