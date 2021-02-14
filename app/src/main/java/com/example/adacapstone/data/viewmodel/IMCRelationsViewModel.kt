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
    val currentImgMsgId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    lateinit var contactsList: LiveData<List<ImgMsgWithContacts>>

//    fun getUsersWithNameLiveData(): LiveData<List<String>>? {
//        return Transformations.switchMap(
//                nameQueryLiveData
//        ) { name: Any? -> myDataSource.getUsersWithNameLiveData(name) }
//    }

    // Instance variable that stores the current list of words. This will automatically change when currentDictionaryId value changes.
//    private val words: LiveData<List<Word>> = Transformations.switchMap<Any, List<Word>>(currentDictionaryId) { dictionaryId: Any? -> mWordRepository.getWordByDictionaryId(dictionaryId) }

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

    fun getContactsOfImgMsg(): LiveData<List<ImgMsgWithContacts>> {
        return contactsList
    }

}