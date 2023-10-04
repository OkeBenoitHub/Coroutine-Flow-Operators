package com.example.flowdemo2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val myFlow = flow {
        for (i in 1..100) {
            emit(i)
            delay(1000L)
        }
    }

    init {
        backPressureDemo()
    }

    private fun backPressureDemo() {
        val myFlow1 = flow {
            for (i in 1..10) {
                Log.i("MYTAG", "Produced $i")
                emit(i)
                delay(1000L)
            }
        }

        viewModelScope.launch {
            myFlow1
                .filter { count ->
                    count % 3 == 0
                }
                .map { it ->
                    showMessage(it)
                }
                .collect {
                    Log.i("MYTAG", "Consumed $it")
                }
        }

    }

    fun showMessage(count: Int): String {
        return "Hello $count"
    }
}