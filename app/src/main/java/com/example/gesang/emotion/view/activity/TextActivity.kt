package com.example.gesang.emotion.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController

import com.example.gesang.emotion.R

class TextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.text_activity_layout)
    }

    override fun onSupportNavigateUp() = findNavController(this,R.id.my_nav_host_fragment).navigateUp()

}