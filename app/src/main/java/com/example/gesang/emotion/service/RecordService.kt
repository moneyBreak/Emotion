package com.example.gesang.emotion.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.util.Log
import com.example.gesang.emotion.R
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
//    var  mIncreatTimeTask : TimerTask? = null

    val mNoteId = null

    val kv = MMKV.defaultMMKV()

    override fun onBind(intent: Intent?): IBinder? =null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("ServiceStart:","yes!")
        recordingStart()
        return START_STICKY
    }

    private fun recordingStart() {
        setFileNameAndPath()

        Log.e("RecorderFilePath:",mFilePath)
        Log.e("RecorderFileName:",mFileName)

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
        recorderStop()
        super.onDestroy()
    }

    private fun recorderStop() {
        mRecorder!!.stop()
        mWholeTimeMillis = (System.currentTimeMillis()-mStartTimeMillis)
        mRecorder!!.release()

        kv.encode("temporaryRecorderFileName",mFileName)
        Log.e("recordFileName:",mFileName)
        Log.e("recordFilePath:",mFilePath)
        kv.encode("temporaryRecorderFilePath",mFilePath)
//
//        mIncreatTimeTask.let {
//            mIncreatTimeTask!!.cancel()
//            mIncreatTimeTask = null
//        }
        mRecorder = null

    }
}