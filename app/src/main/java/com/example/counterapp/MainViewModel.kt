package com.example.counterapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _stateflow = MutableStateFlow(0)
    val stateflow = _stateflow.asStateFlow()

    private val _sharedflow = MutableSharedFlow<Boolean>(replay = 1)
    val sharedflow = _sharedflow.asSharedFlow()

    fun incrementCounter() {
        if (_stateflow.value < 98) {
            _stateflow.value++
        }
    }

    fun decrementCounter() {
        if (_stateflow.value > 0) {
            _stateflow.value--
        }
    }

    fun resetCounter() {
        _stateflow.value = 0
    }

    fun toggleAutoIncrement(enabled: Boolean, context: Context) {
        viewModelScope.launch {
            _sharedflow.emit(enabled)
            if (enabled) {
                while (_stateflow.value < 98 && _sharedflow.replayCache.lastOrNull() == true) {
                    delay(1100L)
                    incrementCounter()
                }
                if (_stateflow.value == 98) {
                    Toast.makeText(context, "Reached Maximum Value of 98", Toast.LENGTH_SHORT).show()
                    _sharedflow.emit(false)
                }
            }
        }
    }
}
