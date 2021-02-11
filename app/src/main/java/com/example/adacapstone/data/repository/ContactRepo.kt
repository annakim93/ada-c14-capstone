package com.example.adacapstone.data.repository

import androidx.lifecycle.LiveData
import com.example.adacapstone.data.dao.ContactDao
import com.example.adacapstone.data.model.Contact

class ContactRepo(private val contactDao: ContactDao) {

    val readAllData: LiveData<List<Contact>> = contactDao.readAllData()

    suspend fun addContact(contact: Contact) {
        contactDao.addContact(contact)
    }

    suspend fun updateImgMsg(contact: Contact) {
        contactDao.updateContact(contact)
    }

    suspend fun deleteImgMsg(contact: Contact) {
        contactDao.deleteContact(contact)
    }

}