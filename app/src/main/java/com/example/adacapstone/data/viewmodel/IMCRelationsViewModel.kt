package com.example.adacapstone.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.adacapstone.data.database.MainDatabase
import com.example.adacapstone.data.relations.ImgMsgContactCrossRef
import com.example.adacapstone.data.repository.IMCRelationsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IMCRelationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IMCRelationsRepo

    init {
        val imcRelationsDao = MainDatabase.getDatabase(application).imcRelationsDao()
        repository = IMCRelationsRepo(imcRelationsDao)
    }

    fun addIMCCrossRef(imcCrossRef: ImgMsgContactCrossRef) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIMCCrossRef(imcCrossRef)
        }
    }

}