package com.example.gesang.emotion.model.data

import android.os.Parcel
import android.os.Parcelable

class RecorderItem() :Parcelable {

    var mName: String? = null
    var mFilePath: String? = null
    var mId: Int = 0
    var mLength: Int = 0
    var mBirth: Long = 0
    constructor(parcel: Parcel) : this() {
        mName = parcel.readString()
        mFilePath = parcel.readString()
        mId = parcel.readInt()
        mLength = parcel.readInt()
        mBirth = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mName)
        parcel.writeString(mFilePath)
        parcel.writeInt(mId)
        parcel.writeInt(mLength)
        parcel.writeLong(mBirth)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecorderItem> {
        override fun createFromParcel(parcel: Parcel): RecorderItem {
            return RecorderItem(parcel)
        }

        override fun newArray(size: Int): Array<RecorderItem?> {
            return arrayOfNulls<RecorderItem>(size)
        }
    }
}