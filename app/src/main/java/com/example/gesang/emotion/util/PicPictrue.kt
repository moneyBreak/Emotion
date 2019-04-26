package com.example.gesang.emotion.util

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap

import android.net.Uri

import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log

fun handleImage(context: Context?, intent: Intent?) {
    val uri = intent!!.data
    Log.e("ImageUri:",uri.toString())
    if(DocumentsContract.isDocumentUri(context,uri)){
        var docId = DocumentsContract.getDocumentId(uri)
        if ("com.android.provicers.media.documents" == uri.authority) {
            val id = docId.split(":")[1]
            val selection = MediaStore.Images.Media._ID + "=" + id
            val imagePath = getImagePath(context!!,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
        }else if ("com.android.providers.downloads.documents"== uri.authority){
            val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
            )
            val imagePath = getImagePath(context!!,contentUri, null)
        }
    }else if ("content"==uri.scheme){
        //不是document类型的Uri，就使用普通方式
        val imagePath = getImagePath(context!!,uri,null)
    }

}

private fun getImagePath(context: Context, uri: Uri?, selection: String?): String? {
    var path: String? = null
    val corsor = context.contentResolver.query(uri, null, selection, null, null)

    corsor.let {
        if (it.moveToFirst()) {
            path = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
        }
        it.close()
    }
    return path
}


