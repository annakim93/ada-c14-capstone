package com.example.adacapstone.data.repository

import android.media.Image
import androidx.lifecycle.LiveData
import com.example.adacapstone.data.dao.ContactDao
import com.example.adacapstone.data.dao.IMCRelationsDao
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef

class IMCRelationsRepo(private val imcRelationsDao: IMCRelationsDao) {

//    val readAllData: LiveData<List<Contact>> = imcRelationsDao.readAllData()

    fun getContactsOfImgMsg(imgMsgId: Int) {
        imcRelationsDao.getContactsOfImgMsg(imgMsgId)
    }

    fun getImgMsgsOfContact(contactId: Int) {
        imcRelationsDao.getImgMsgsOfContact(contactId)
    }

    suspend fun addIMCCrossRef(crossRef: ImgMsgContactCrossRef) {
        imcRelationsDao.addIMCCrossRef(crossRef)
    }

}