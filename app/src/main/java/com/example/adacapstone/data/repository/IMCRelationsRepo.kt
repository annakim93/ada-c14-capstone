package com.example.adacapstone.data.repository

import android.media.Image
import androidx.lifecycle.LiveData
import com.example.adacapstone.data.dao.ContactDao
import com.example.adacapstone.data.dao.IMCRelationsDao
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.model.ImageMessage
import com.example.adacapstone.data.relations.ContactWithImgMsgs
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
import com.example.adacapstone.data.relations.ImgMsgWithContacts

class IMCRelationsRepo(private val imcRelationsDao: IMCRelationsDao) {

    fun getContactsOfImgMsg(imgMsgId: Int): LiveData<List<ImgMsgWithContacts>> {
        return imcRelationsDao.getContactsOfImgMsg(imgMsgId)
    }

    fun getImgMsgsOfContact(contactId: Int): LiveData<List<ContactWithImgMsgs>> {
        return imcRelationsDao.getImgMsgsOfContact(contactId)
    }

    suspend fun addIMCCrossRef(crossRef: ImgMsgContactCrossRef) {
        imcRelationsDao.addIMCCrossRef(crossRef)
    }

}