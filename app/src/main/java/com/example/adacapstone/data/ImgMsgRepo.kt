package com.example.adacapstone.data

import androidx.lifecycle.LiveData

class ImgMsgRepo(private val imgMsgDao: ImgMsgDao) {

    val readAllData: LiveData<List<ImageMessage>> = imgMsgDao.readAllData()

    suspend fun addImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.addImgMsg(imgMsg)
    }

}