package com.example.adacapstone.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adacapstone.data.ImgMsgDatabase
import com.example.adacapstone.data.repository.ImgMsgRepo
import com.example.adacapstone.data.model.ImageMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImgMsgViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ImageMessage>>
    private val repository: ImgMsgRepo
    private val _navigateToUpdateFrag = MutableLiveData<ImageMessage>()
    val navigateToUpdateFrag: LiveData<ImageMessage>
        get() = _navigateToUpdateFrag

    init {
        val imgMsgDao = ImgMsgDatabase.getDatabase(application).imgMsgDao()
        repository = ImgMsgRepo(imgMsgDao)
        readAllData = repository.readAllData
    }

    fun addImgMsg(imgMsg: ImageMessage) {
        // Dispatchers.IO --> run in background/worker thread (not main thread)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addImgMsg(imgMsg)
        }
    }

    fun updateImgMsg(imgMsg: ImageMessage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateImgMsg(imgMsg)
        }
    }

    fun deleteImgMsg(imgMsg: ImageMessage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteImgMsg(imgMsg)
        }
    }

    fun onImgMsgClicked(imgMsg: ImageMessage) {
        _navigateToUpdateFrag.value = imgMsg
    }

    fun onUpdateFragNavigated() {
        _navigateToUpdateFrag.value = null
    }

}