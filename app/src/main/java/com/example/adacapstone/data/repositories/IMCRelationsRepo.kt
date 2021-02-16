package com.example.adacapstone.data.repositories

import androidx.lifecycle.LiveData
import com.example.adacapstone.data.dao.IMCRelationsDao
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

    suspend fun deleteIMCCrossRef(crossRef: ImgMsgContactCrossRef) {
        imcRelationsDao.deleteIMCCrossRef(crossRef)
    }

}