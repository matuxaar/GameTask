package com.example.gametask

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CoinViewModel : ViewModel() {
    private val _coins = mutableStateOf(0)
    val coins: State<Int> = _coins

    fun updateCoins(newCoins: Int) {
        _coins.value = newCoins
    }
}