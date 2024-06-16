package com.example.vaersel.ui.home.animation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimationViewModel() {

    /* States handling popup visibility */

    private val _clothesPopUpUiState = MutableStateFlow(false)
    val clothesPopUpUiState: StateFlow<Boolean> = _clothesPopUpUiState.asStateFlow()

    private val _rainPopUpUiState = MutableStateFlow(false)
    val rainPopUpUiState: StateFlow<Boolean> = _rainPopUpUiState.asStateFlow()

    private val _windPopUpUiState = MutableStateFlow(false)
    val windPopUpUiState: StateFlow<Boolean> = _windPopUpUiState.asStateFlow()

    /* State handling wind-animation pause*/
    private val _showAnimation = MutableStateFlow(true)
    val animationState: StateFlow<Boolean> = _showAnimation.asStateFlow()

    fun closeClothesPopUp() {
        _clothesPopUpUiState.value = false
    }
    fun openClothesPopUp() {
        _clothesPopUpUiState.value = true
    }
    fun closeRainPopUp() {
        _rainPopUpUiState.value = false
    }
    fun openRainPopUp() {
        _rainPopUpUiState.value = true
    }
    fun closeWindPopUp() {
        _windPopUpUiState.value = false
    }
    fun openWindPopUp() {
        _windPopUpUiState.value = true
    }
    fun animationStateChange(){
        _showAnimation.value = !_showAnimation.value
    }
}