package com.example.adacapstone.data.repository

import androidx.lifecycle.LiveData
import com.example.adacapstone.data.ImgMsgDao
import com.example.adacapstone.data.model.ImageMessage

class ImgMsgRepo(private val imgMsgDao: ImgMsgDao) {

    val readAllData: LiveData<List<ImageMessage>> = imgMsgDao.readAllData()

    suspend fun addImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.addImgMsg(imgMsg)
    }

    suspend fun updateImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.updateImgMsg(imgMsg)
    }

}