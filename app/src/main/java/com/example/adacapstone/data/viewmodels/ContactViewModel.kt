package com.example.adacapstone.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adacapstone.data.database.MainDatabase
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.repositories.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Contact>>
    private val repository: ContactRepo
    val selectedContactsList: MutableLiveData<List<Contact>> by lazy {
        MutableLiveData<List<Contact>>()
    }

    init {
        val contactDao = MainDatabase.getDatabase(application).contactDao()
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

    fun setSelection(contactList: List<Contact>) {
        selectedContactsList.postValue(contactList)
    }

}