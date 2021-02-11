package com.example.adacapstone.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.adacapstone.data.database.ContactDatabase
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.repository.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Contact>>
    private val repository: ContactRepo

    init {
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = ContactRepo(contactDao)
        readAllData = repository.readAllData
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
        }
    }

}