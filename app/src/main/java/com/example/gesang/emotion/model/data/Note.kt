package com.example.gesang.emotion.model.data

import java.util.*

data class Note(
        var birth:String?=null,
        var title:String?=null,
        var content:String?=null,
        var date:String?=null,
        var imageUrl:String?=null,
        var record:String?=null,
        var lastDate:String?=null,
        var labels:String?=null) {

    fun getSet(): MutableSet<String?> = mutableSetOf(
    this.birth,this.title,this.content,this.imageUrl,this.record,this.date,this.lastDate
    )

    fun setLastDate(){
        birth.let {
            System.currentTimeMillis().toString()
        }
        lastDate = System.currentTimeMillis().toString()
    }


}