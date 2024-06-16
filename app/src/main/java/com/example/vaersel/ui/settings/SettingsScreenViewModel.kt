package com.example.vaersel.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaersel.ui.theme.AllThemes
import com.example.vaersel.ui.theme.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val sharedPref: SharedPreferences): ViewModel() {
    companion object { // constants
        private const val SELECTED_THEME_KEY = "selected_theme"
        private const val HIGH_CONTRAST_ON_KEY = "high_contrast_on"
        private const val TEMPERATURE_PREFERENCE = "temperature_preference"
        private const val LIGHT_OR_DARK_PREFERENCE = "light_or_dark_preference"
    }

    // which theme is selected
    private val _selectedTheme = MutableStateFlow(
        intToTheme(sharedPref.getInt(SELECTED_THEME_KEY, 0))
    )
    val selectedTheme: StateFlow<Theme> = _selectedTheme.asStateFlow()

    // states for opening the dropdown menus

    private val _themeExpanded = MutableStateFlow(false)
    val themeExpanded: StateFlow<Boolean> = _themeExpanded.asStateFlow()

    private val _tempExpanded = MutableStateFlow(false)
    val tempExpanded: StateFlow<Boolean> = _tempExpanded.asStateFlow()

    private val _lightOrDarkExpanded = MutableStateFlow(false)
    val lightOrDarkExpanded: StateFlow<Boolean> = _lightOrDarkExpanded.asStateFlow()

    // if high contrast is on
    private val _highContrastOn = MutableStateFlow( sharedPref.getBoolean(HIGH_CONTRAST_ON_KEY, false) )
    val highContrastOn: StateFlow<Boolean> = _highContrastOn.asStateFlow()

    // state for temperature preference
    private val _tempPref = MutableStateFlow(
        sharedPref.getInt(TEMPERATURE_PREFERENCE, 0)
    )
    val tempPref: StateFlow<Int> = _tempPref.asStateFlow()

    // if the user wants to use system settings or lock app to light or dark mode
    private val _lightOrDarkPref = MutableStateFlow( sharedPref.getInt(LIGHT_OR_DARK_PREFERENCE, 0) )
    val lightOrDarkPref: StateFlow<Int> = _lightOrDarkPref.asStateFlow()

    // if the UI should be light or dark, based on previous state
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // updates light/dark mode state
    fun correctLightOrDarkMode(bool: Boolean) {
        _isDarkMode.value = when (lightOrDarkPref.value){
            1 -> false
            2 -> true
            else -> bool
        }
    }

    fun themeExpandedChange(bool: Boolean) {
        _themeExpanded.value = bool
    }

    fun tempExpandedChange(bool: Boolean) {
        _tempExpanded.value = bool
    }

    fun lightOrDarkExpandedChange(bool: Boolean) {
        _lightOrDarkExpanded.value = bool
    }

    //updates high contrast state
    fun highContrastChange(bool: Boolean) {
        _highContrastOn.value = bool
        viewModelScope.launch {
            val editor = sharedPref.edit()
            editor.putBoolean(HIGH_CONTRAST_ON_KEY, bool)
            editor.apply()
        }
    }

    fun intToTheme(int: Int): Theme {
        return when (int){
            1 -> AllThemes.ONE.theme
            2 -> AllThemes.TWO.theme
            3 -> AllThemes.THREE.theme
            else -> AllThemes.ZERO.theme
        }
    }

    fun intToTemp (int: Int): String {
        return when(int) {
            -2 -> "Blir ofte for varm"
            -1 -> "Blir noen ganger for varm"
            1 -> "Blir noen ganger for kald"
            2 -> "Blir ofte for kald"
            else -> "Anbefalingene er passelige"
        }
    }

    fun intToLightOrDark(int: Int): String {
        return when(int) {
            1 -> "Lys modus"
            2 -> "Mørk modus"
            else -> "Følg systeminstillinger"
        }
    }

    fun switchTheme(int: Int) {
        _selectedTheme.value = intToTheme(int)
    }

    fun switchTemp(int: Int) {
        _tempPref.value = int
    }

    fun switchLightOrDark(int: Int) {
        _lightOrDarkPref.value = int
    }

    fun saveThemeChoice(int: Int){
        viewModelScope.launch {
            val editor = sharedPref.edit()
            editor.putInt(SELECTED_THEME_KEY, int)
            editor.apply()
        }
    }

    fun saveTempChoice(int: Int) {
        viewModelScope.launch {
            val editor = sharedPref.edit()
            editor.putInt(TEMPERATURE_PREFERENCE, int)
            editor.apply()
        }
    }

    fun saveLightOrDarkChoice(int: Int) {
        viewModelScope.launch {
            val editor = sharedPref.edit()
            editor.putInt(LIGHT_OR_DARK_PREFERENCE, int)
            editor.apply()
        }
    }
}