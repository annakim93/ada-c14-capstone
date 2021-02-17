package com.example.adacapstone.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adacapstone.data.database.MainDatabase
import com.example.adacapstone.data.repositories.ImgMsgRepo
import com.example.adacapstone.data.model.ImageMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImgMsgViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ImgMsgRepo
    val readAllData: LiveData<List<ImageMessage>>
    val latestImgMsgId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    private val _navigateToUpdateFrag = MutableLiveData<ImageMessage?>()
    val navigateToUpdateFrag: LiveData<ImageMessage?>
        get() = _navigateToUpdateFrag
//    val currentLatitude: MutableLiveData<Double> by lazy {
//        MutableLiveData<Double>()
//    }
//    var currentLongitude: MutableLiveData<Double> by lazy {
//        MutableLiveData<Double>
//    }

    init {
        val imgMsgDao = MainDatabase.getDatabase(application).imgMsgDao()
        repository = ImgMsgRepo(imgMsgDao)
        readAllData = repository.readAllData
    }

    fun addImgMsg(imgMsg: ImageMessage) {
        // Dispatchers.IO --> run in background/worker thread (not main thread)
        viewModelScope.launch(Dispatchers.IO) {
            latestImgMsgId.postValue(repository.addImgMsg(imgMsg))
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

//    fun setLatitude(lat: Double) {
//        currentLatitude.postValue(lat)
//    }
//
//    fun setLongitude(long: Double) {
//        currentLongitude.postValue(long)
//    }

    fun onImgMsgClicked(imgMsg: ImageMessage) {
        _navigateToUpdateFrag.value = imgMsg
    }

    fun onUpdateFragNavigated() {
        _navigateToUpdateFrag.value = null
    }

}