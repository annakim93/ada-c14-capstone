package com.example.adacapstone.data.repositories

import androidx.lifecycle.LiveData
import com.example.adacapstone.data.dao.ImgMsgDao
import com.example.adacapstone.data.model.ImageMessage

class ImgMsgRepo(private val imgMsgDao: ImgMsgDao) {

    val readAllData: LiveData<List<ImageMessage>> = imgMsgDao.readAllData()

    suspend fun addImgMsg(imgMsg: ImageMessage): Long {
        return imgMsgDao.addImgMsg(imgMsg)
    }

    suspend fun updateImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.updateImgMsg(imgMsg)
    }

    suspend fun deleteImgMsg(imgMsg: ImageMessage) {
        imgMsgDao.deleteImgMsg(imgMsg)
    }

}