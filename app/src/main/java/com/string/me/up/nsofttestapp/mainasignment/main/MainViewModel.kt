package com.string.me.up.nsofttestapp.mainasignment.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.string.me.up.nsofttestapp.mainasignment.util.Task
import java.util.concurrent.LinkedBlockingDeque

class MainViewModel : ViewModel() {

    var currentList = MutableLiveData<LinkedBlockingDeque<Task>>()

    fun updateCurrentList(
        _currentList: LinkedBlockingDeque<Task>,
        currentItem: Task? = null
    ) {
        currentList.postValue(_currentList)
    }

    fun setRandomDelay(): Long {
        return (1000L..3000L).random()
    }
}