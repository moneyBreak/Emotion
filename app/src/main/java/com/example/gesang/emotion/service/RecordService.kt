package com.example.gesang.emotion.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.util.Log
import com.example.gesang.emotion.R
import com.example.gesang.emotion.model.data.RecorderItem
import com.tencent.mmkv.MMKV
import java.io.File
import java.io.IOException
import java.util.*

@SuppressLint("Registered")
class RecordService : Service() {

    var mFileName :String? =null
    var mFilePath :String? =null

    private var mRecorder: MediaRecorder ?=null

    var mStartTimeMillis :Long = 0
    var mWholeTimeMillis : Long=0

    val mNoteId = null

    private var recorderItem=RecorderItem()

    val kv = MMKV.defaultMMKV()

    override fun onBind(intent: Intent?): IBinder? =null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("ServiceStart:","yes!")
        recordingStart()
        return START_STICKY
    }

    private fun recordingStart() {
        setFileNameAndPath()

        Log.e("StartRecorderFilePath:",mFilePath)
        Log.e("StartRecorderFileName:",mFileName)

        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mRecorder!!.setOutputFile(mFilePath)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mRecorder!!.setAudioChannels(1)
        mRecorder!!.setAudioSamplingRate(44100)
        mRecorder!!.setAudioEncodingBitRate(192000)

        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
            mStartTimeMillis = System.currentTimeMillis()
        }catch (e: IOException){
            Log.e("recorder", "prepare() failed")
            Log.e("prepareE:",e.toString())
        }
    }

    private fun setFileNameAndPath() {

        mFileName = getString(R.string.app_name) + "_" + mNoteId + (System.currentTimeMillis()) + ".mp4"

        mFilePath = Environment.getExternalStorageDirectory().absolutePath

        mFilePath += "/${getString(R.string.app_name)}/" + mFileName

        var file:File = File(mFilePath)

        Log.e("fileIsNull???:",file.toString())

    }

    override fun onDestroy() {
        mRecorder.let { recorderStop() }
        super.onDestroy()
    }

    private fun recorderStop() {
        mRecorder!!.stop()
        mWholeTimeMillis = (System.currentTimeMillis()-mStartTimeMillis)
        mRecorder!!.reset()
        mRecorder!!.release()

//        recorderItem.mName = null
        recorderItem.mFilePath = mFilePath
        recorderItem.mBirth = mStartTimeMillis
        recorderItem.mId = 0
        recorderItem.mLength = mWholeTimeMillis.toInt()


//        Log.e("KvrecordFileName:",mFileName)
//        Log.e("KvrecordFilePath:",mFilePath)
//        kv.encode("temporaryRecorderFileName",mFileName)
//        kv.encode("temporaryRecorderFilePath",mFilePath)

        kv.encode("temporaryRecorderItem",recorderItem)
        Log.e("ServiceEncode:","已经存入了")
        mRecorder = null


    }
}