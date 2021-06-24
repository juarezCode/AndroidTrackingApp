package com.juarez.trackingmapapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val greeting = MutableLiveData<String>()

    init {
        greeting.postValue("hola")
    }

}