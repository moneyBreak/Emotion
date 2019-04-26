package com.example.gesang.emotion.view.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.gesang.emotion.R
import com.example.gesang.emotion.base.BaseFragment
import com.example.gesang.emotion.model.data.Note
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.edit_activity_layout.*

class EditNoteFragment : BaseFragment() {

    lateinit var mFragment:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_activity_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFragment = view

        initView()

    }

    private fun initView() {
        //这里直接读取数据库数据吧

        val jsonString = arguments!!.getString("note")
        val note : Note = Gson().fromJson(jsonString!!)

        Log.e("noteData",jsonString)//这里是错的

        editTitle.text = Editable.Factory().newEditable(note.title)
        editTextContent.text = Editable.Factory().newEditable(note.content)

    }
}