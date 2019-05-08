package com.example.gesang.emotion.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.gesang.emotion.R
import com.example.gesang.emotion.model.data.Note
import com.example.gesang.emotion.model.data.RecorderItem
import com.example.gesang.emotion.util.Voice
import com.example.gesang.emotion.view.adapter.MyListAdapter
import com.example.gesang.emotion.view.selfview.EditContentMenu
import com.example.gesang.emotion.view.selfview.RecordAudioDialogFragment
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.edit_activity_layout.*
import java.io.File
import java.io.IOException

const val LEFT_BOTTOM_BUTTON = 0
const val RIGHT_BOTTOM_BUTTON = 1

const val RESULT_IMAGE = 100

const val PERMISSION_PICTURE = 1
const val PERMISSION_VOICE = 2
const val PERMISSION_RECORDER = 3

class EditActivity : AppCompatActivity(),View.OnClickListener {
//    override fun onCancel() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onRecorderItemReturn(item: RecorderItem?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    private var position : Int = 0

    private lateinit var leftContentList : EditContentMenu
    private lateinit var leftContentAdapter: MyListAdapter
    private lateinit var rightChoiceList : EditContentMenu
    private lateinit var rightChoiceAdapter: MyListAdapter
    private lateinit var thisView:View

    private lateinit var mRecorderFragment : RecordAudioDialogFragment
    private var mPlayer : MediaPlayer ?=null
    private lateinit var mPlayerAnimation :AnimationDrawable
    private var height=0

    private var mRecorderItem=RecorderItem()

    private var topState = false
    private var remindState = false
    private var  isPlaying = false

    val kv = MMKV.defaultMMKV()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity_layout)

        initView()
        initClick()
        initPopup()
        popClick()

    }

    private fun popClick() {
        leftContentList.mEditList.setOnItemClickListener {
            parent, view, position, id ->
            leftContentList.listPop.dismiss()
            when(position){
                0 ->{
                    checkPer(arrayOf(android.Manifest.permission_group.STORAGE),
                            PERMISSION_PICTURE,
                            openPics())
                }
                1 ->{
                    editTextContent.selectionEnd
                    checkPer(arrayOf(android.Manifest.permission.RECORD_AUDIO),
                            PERMISSION_VOICE,
                            takeAudio())
                }
                2 ->{
                    checkPer(arrayOf(android.Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERMISSION_RECORDER,
                            takeRecorder())
                }
            }
        }
    }
/*
录音存储及上传
 */
    private fun takeRecorder(){
        mRecorderFragment = RecordAudioDialogFragment.newInstance()
        mRecorderFragment.show(supportFragmentManager, RecordAudioDialogFragment::class.java.simpleName)

    mRecorderFragment.setOnCancelListener(object : RecordAudioDialogFragment.OnAudioCancelListener {
        override fun onCancel() {//录音被取消了
            deleteRecorder()
        }
        override fun onRecorderItemReturn() {
            recorderLayout.visibility = View.VISIBLE
        }
    })
//        mRecorderFragment.setOnCancelListener {
////            mRecorderFragment.dismiss()
//            recorderLayout.visibility = View.VISIBLE
//            initialRecorder()
////            playerTimeText.text = mPlayer!!.duration.toString()
//            //显示时间
//        }

    }

    private  fun checkPer(per: Array<String>, perType:Int, f: Unit?){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            per[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, per, perType)
                Log.e("checkRequestPermission:","尽管请求了，还是在请求")
            }else {
                Log.e("checkJump:","这里跳过了")
                f
            }
        }
    }
    /*
    听写
     */
    private fun takeAudio() {
        val mDialog = setDialog()
        mDialog.show()
    }

    private fun setDialog(): RecognizerDialog {
        val dialog = RecognizerDialog(this, null)
        //需要清空参照语法和subject
        dialog.setParameter(SpeechConstant.CLOUD_GRAMMAR,null)
        dialog.setParameter(SpeechConstant.SUBJECT,null)
        dialog.setParameter(SpeechConstant.RESULT_TYPE,"json")//返回json类文本
        dialog.setParameter(SpeechConstant.ENGINE_TYPE,"cloud")
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn")//听写语种
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin")//返回语言
        dialog.setParameter(SpeechConstant.VAD_BOS,"3000")//前置静默
        dialog.setParameter(SpeechConstant.VAD_EOS,"1500")//后置静默
        dialog.setParameter(SpeechConstant.ASR_PTT,"1")//有无标点，1为有标点

        dialog.setListener(VoiceListener())

        return dialog
    }
    /*
    实现了dialog监听的内部类
     */
    inner class VoiceListener: RecognizerDialogListener{
        override fun onResult(recognizerResult: RecognizerResult?, isLast: Boolean) {
            isLast.let {
                Log.e("recognizerResultString:",recognizerResult!!.resultString.toString())
                val result = transformVoice(recognizerResult!!.resultString)
                Log.e("resultVoice:",result)
                val editable = editTextContent.editableText
//                Log.e("editableGet!:",editable.toString())
                editable.append(result)
            }
        }

        private fun transformVoice(resultString: String?): String {
            Log.e("resultString:",resultString)
            val voiceBean :Voice = Gson().fromJson(resultString!!)
            Log.e("voiceBean:",voiceBean.toString())

            val sb = StringBuffer()
            val ws = voiceBean.ws
            Log.e("ws:",ws.toString())
            for(words in ws){
                Log.e("sbAppend?:",words.cw[0].w)
                sb.append(words.cw[0].w)
            }

            Log.e("sbResult:",sb.toString())
            return sb.toString()
        }

        override fun onError(p0: SpeechError?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

/*
拿图片
 */
    private fun openPics() {
        var intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent,RESULT_IMAGE)
    }

    private fun initPopup() {
        thisView = findViewById(R.id.popParent)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        height = metrics.heightPixels

        leftContentList = EditContentMenu(this, R.layout.edit_content_popupwindow_layout,
                MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        rightChoiceList = EditContentMenu(this, R.layout.edit_content_popupwindow_layout,
                MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        leftContentAdapter = MyListAdapter(this,2)
        leftContentList.mEditList.adapter = leftContentAdapter

        rightChoiceAdapter = MyListAdapter(this,3)
        rightChoiceList.mEditList.adapter = rightChoiceAdapter

        leftContentList.listPop.setOnDismissListener {
            Log.e("onDismiss???","successl")
        }
        rightChoiceList.listPop.setOnDismissListener {
            Log.e("onDismiss???","successr")
        }

    }

    private fun initClick() {
        back.setOnClickListener(this)
        top.setOnClickListener(this)
        remind.setOnClickListener(this)
        leftBottomButton.setOnClickListener(this)
        rightBottomButton.setOnClickListener(this)

        closePicButton.setOnClickListener(this)
        closeRecorderButton.setOnClickListener(this)

        recorderLayout.setOnClickListener(this)
    }

    private fun initView() {
        val jsonString = intent.getStringExtra("note")
        val note : Note = Gson().fromJson(jsonString!!)

        Log.e("noteData",jsonString)//这里是错的

        editTitle.text = Editable.Factory().newEditable(note.title)
        editTextContent.text = Editable.Factory().newEditable(note.content)
        //设置默认参数，接收 Image的url，和recorder的url

        mPlayerAnimation = playerButton.background as AnimationDrawable

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            when(requestCode){
                PERMISSION_PICTURE -> {openPics() }
                PERMISSION_VOICE -> {takeAudio()}
                PERMISSION_RECORDER -> {takeRecorder()}
            }
        }else {
            Toast.makeText(this,"你同意了吗？1",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            RESULT_IMAGE ->{
                if(resultCode == Activity.RESULT_OK && data!=null){
                    var bitmap:Bitmap ?= null
                    pictureLayout.visibility = View.VISIBLE
                    showImage(data.data)
                }
            }
        }
    }

    private fun showImage(data: Uri?)=Glide.with(this).load(data).fitCenter().into(editImage)

    override fun finish() {
//        dataList[position].title = editTitle.text.toString()
//        dataList[position].content = editTextContent.text.toString()
        //这里需要一个扫描图片上传的线程工作流
        //扫描音频上传


        super.finish()
    }

    override fun onClick(v: View?){
        when(v!!.id){
            R.id.back -> finish()
            R.id.top -> {
                if(topState){top.setBackgroundResource(R.mipmap.icon_top)}
                else{top.setBackgroundResource(R.mipmap.icon_untop)}
                topState = !topState
                //添加置顶
            }
            R.id.remind ->{
                if(remindState){remind.setBackgroundResource(R.drawable.icon_memo)}
                else{remind.setBackgroundResource(R.drawable.icon_unmemo)}
                remindState = !remindState
                //设置提醒时间
            }
            R.id.leftBottomButton ->{
                Log.e("buttonHeight:",height.toString())
                Log.e("leftButtonHei:",leftBottomButton.height.toString())
                leftContentList.showAtLocation(thisView,Gravity.BOTTOM,0,(height*0.12).toInt())
                rightChoiceList.listPop.isShowing.let { rightChoiceList.listPop.dismiss() }
            }

            R.id.rightBottomButton ->{
                Log.e("rightBottom:","被点击了，但是没显示")
                rightChoiceList.showAtLocation(thisView,Gravity.BOTTOM,0,(height*0.12).toInt())
                leftContentList.listPop.isShowing.let { leftContentList.listPop.dismiss() }
            }

            R.id.recorderLayout ->{
                Log.e("startPlayer:","被点击了")
                if(isPlaying){
                    stopPlayer()
                }else{
                    startPlayer()
                }
                isPlaying = !isPlaying
            }

            R.id.closePicButton -> {
                pictureLayout.visibility = View.GONE
            }

            R.id.closeRecorderButton -> {
                recorderLayout.visibility = View.GONE
                deleteRecorder()
            }
        }
    }
    /*
    *清除生成的录音文件
     */
    private fun deleteRecorder() {
        val deletItem = kv.decodeParcelable("temporaryRecorderItem",RecorderItem::class.java)
        deletItem.let {
            val file = File(deletItem.mFilePath)
            file.delete()
            kv.encode("temporaryRecorderItem","")
        }
    }

    private fun startPlayer() {
        Log.e("startPlayer:","yes")
        mPlayerAnimation.start()
        try {
            Log.e("tryStartVoice:","start!")
            initialRecorder()
        } catch ( e: IOException) {
            Log.e("准备状态？", "prepare() failed");
            Log.e("player???:",e.toString())
        }

        mPlayer!!.setOnCompletionListener {
            stopPlayer()
        }
    }

    private fun stopPlayer() {
        Log.e("stopPlayer:","yes")
        //停止动画
        mPlayerAnimation.stop()
        mPlayerAnimation.selectDrawable(0)
        //暂停动画
        mPlayerAnimation = playerButton.background as AnimationDrawable

        stopVoice()
    }

    /*
    *停止声音,释放资源
     */
    private fun stopVoice() {
        mPlayer!!.stop()
        mPlayer!!.reset()
        mPlayer!!.release()
        mPlayer = null
    }

    /*
    初始化MediaPlayer，获取文件路径
     */
    private fun initialRecorder(){
        mRecorderItem = kv.decodeParcelable("temporaryRecorderItem",RecorderItem::class.java)
        val file = File(mRecorderItem.mFilePath)
        val uri = Uri.fromFile(file)

        val builder = AudioAttributes.Builder()
        builder.apply {
            setLegacyStreamType(AudioManager.STREAM_MUSIC)
        }

        mPlayer = MediaPlayer().apply {
            setAudioAttributes(builder.build())
            setDataSource(this@EditActivity,uri)
            prepare()
            start()
        }
    }
}