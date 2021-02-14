package com.example.adacapstone.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.adacapstone.data.database.MainDatabase
import com.example.adacapstone.data.model.Contact
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
import com.example.adacapstone.data.relations.ImgMsgWithContacts
import com.example.adacapstone.data.repository.IMCRelationsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class IMCRelationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IMCRelationsRepo
    private val currentImgMsgId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    lateinit var contactsList: LiveData<List<ImgMsgWithContacts>>

    init {
        val imcRelationsDao = MainDatabase.getDatabase(application).imcRelationsDao()
        repository = IMCRelationsRepo(imcRelationsDao)
    }

    fun addIMCCrossRef(imcCrossRef: ImgMsgContactCrossRef) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIMCCrossRef(imcCrossRef)
        }
    }

    fun setImgMsgId(imgMsgId: Int) {
        currentImgMsgId.value = imgMsgId
        contactsList = repository.getContactsOfImgMsg(currentImgMsgId.value!!)
    }

}