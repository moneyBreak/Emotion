package com.example.gesang.emotion.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.widget.ImageView
import java.io.*

/*
在执行这个函数时考虑新开线程执行
质量压缩，先写到这里，也许以后有用
 */

fun BitmapFactory.bitmapCompress(cr: ContentResolver,uri: Uri): Bitmap? {
    var sucess = false
    var resultBitmap :Bitmap ?=null
    var file: File?=null

    try {
        var targetBitmap= MediaStore.Images.Media.getBitmap(cr,uri)

        val path = Environment.getExternalStorageDirectory().path + "/pic"

        file = File(path)

        file.mkdirs()

        val time = System.currentTimeMillis()

        file = File("$file/$time.png")

        var out: OutputStream
        out = FileOutputStream(file.path)

        sucess = targetBitmap.compress(Bitmap.CompressFormat.JPEG,50,out)
    }catch (e: FileNotFoundException){
        e.printStackTrace()
    }catch (e:IOException){
        e.printStackTrace()
    }

    sucess.let {
        resultBitmap = MediaStore.Images.Media.getBitmap(cr,Uri.fromFile(file))
    }

    return resultBitmap
}

/*
尺寸压缩
 */

fun BitmapFactory.bitmapShow (context:Context,uri: Uri,view: ImageView){
    val filePathColum= arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri,filePathColum,null,null,null)
    cursor.moveToFirst()
    val columIndex = cursor.getColumnIndex(filePathColum[0])
    val imagePath = cursor.getString(columIndex)
    cursor.close()

    //压缩
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imagePath,options)
    options.inJustDecodeBounds = false
    //得到压缩尺寸，可以读入内存
    //options.inSampleSize = caculateSampleSize(options,context.getSystemService(Context.WINDOW_SERVICE))
    val result = BitmapFactory.decodeFile(imagePath,options)
    view.setImageBitmap(result)






}
/*
计算压缩系数
 */

//fun caculateSampleSize(options: BitmapFactory.Options, systemService: Any): Int {
//    var size = 1
//    val width = options.outWidth
//    val height = options.outHeight
//
//    val wm = systemService
//    var dm = DisplayMetrics()
//
//
//
//
//
//
//
//
//}



