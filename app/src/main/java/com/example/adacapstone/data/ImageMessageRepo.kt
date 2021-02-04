package com.example.adacapstone.data

import androidx.lifecycle.LiveData

class ImageMessageRepo(private val imgMsgDao: ImageMessageDao) {

    val readAllData: LiveData<List<ImageMessage>> = imgMsgDao.readAllData()

    suspend fun addImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.addImgMsg(imgMsg)
    }

}