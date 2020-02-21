package com.runeanim.lineproject.ui.imagedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageDetailViewModel : ViewModel() {
    private val _imagePath = MutableLiveData<String>()
    val imagePath: LiveData<String> = _imagePath

    fun start(path: String) {
        _imagePath.value = path
    }
}
