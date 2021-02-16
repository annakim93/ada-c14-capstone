package com.example.adacapstone.data.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.adacapstone.data.database.MainDatabase
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
import com.example.adacapstone.data.relations.ImgMsgWithContacts
import com.example.adacapstone.data.repositories.IMCRelationsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IMCRelationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IMCRelationsRepo
    private val currentImgMsgId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    lateinit var originalContactsList: LiveData<List<ImgMsgWithContacts>>
    lateinit var contactsList: LiveData<List<ImgMsgWithContacts>>
    lateinit var sendList: LiveData<List<ImgMsgWithContacts>>

    init {
        val imcRelationsDao = MainDatabase.getDatabase(application).imcRelationsDao()
        repository = IMCRelationsRepo(imcRelationsDao)
    }

    fun addIMCCrossRef(imcCrossRef: ImgMsgContactCrossRef) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIMCCrossRef(imcCrossRef)
        }
    }

    fun deleteIMCCrossRef(imcCrossRef: ImgMsgContactCrossRef) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIMCCrossRef(imcCrossRef)
        }
    }

    fun setImgMsgId(imgMsgId: Int) {
        currentImgMsgId.value = imgMsgId
        contactsList = repository.getContactsOfImgMsg(imgMsgId)
    }

    fun setOriginalContactsList(imgMsgId: Int) {
        originalContactsList = repository.getContactsOfImgMsg(imgMsgId)
    }

    fun setSendList(imgMsgId: Int) {
        sendList = repository.getContactsOfImgMsg(imgMsgId)
    }

}