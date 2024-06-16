package com.example.vaersel.ui.streak

import com.example.vaersel.data.streak.StreakManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StreakViewModel(
        val streakManager: StreakManager
) {
    // determines if streak pop-up is should be on-screen
    private val _streakPopUpUiState = MutableStateFlow(false)
    val streakPopUpUiState: StateFlow<Boolean> = _streakPopUpUiState.asStateFlow()

    // keeps track of current streak
    private val _currentStreak = MutableStateFlow(streakManager.getCurrentStreakCount())
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    fun closeStreakPopUp() {
        _streakPopUpUiState.value = false
    }

    fun openStreakPopUp() {
        _streakPopUpUiState.value = true
    }

    fun updateStreakUI() {
        _currentStreak.value = streakManager.getCurrentStreakCount()
    }
}