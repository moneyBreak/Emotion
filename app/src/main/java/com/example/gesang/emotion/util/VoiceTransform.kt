package com.example.gesang.emotion.util

import android.speech.tts.Voice
import com.google.gson.Gson
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.ui.RecognizerDialogListener


//class VoiceListener: RecognizerDialogListener{
//    override fun onResult(recognizerResult: RecognizerResult?, isLast: Boolean) {
//        isLast.let {
//           val result = transformVoice(recognizerResult!!.resultString)
//        }
//    }
//
//    private fun transformVoice(resultString: String?): String {
//        val gson =Gson()
//        val voiceBean = gson.fromJson(resultString,
//                com.example.gesang.emotion.util.Voice::class.javaObjectType)
//
//        var sb = StringBuffer()
//        val ws = voiceBean.ws
//        for(words in ws){
//            sb.append(words.cw[0].w)
//        }
//
//        return sb.toString()
//    }
//
//    override fun onError(p0: SpeechError?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}

class Voice{
    var ws = mutableListOf<WSBean>()

    class WSBean{
        var cw = mutableListOf<CWBean>()
    }
    class CWBean{
        lateinit var w :String
    }
}