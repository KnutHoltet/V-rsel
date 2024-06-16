package com.example.vaersel.hamburgerbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class HamburgerItem(
    val name: String,
    val filledIcon: ImageVector,
    val unfilledIcon: ImageVector
)

fun menuList(): List<HamburgerItem>{
    val options = listOf(
        HamburgerItem(
            name = "Hjem",
            filledIcon = Icons.Filled.Home,
            unfilledIcon = Icons.Outlined.Home
        ),
        HamburgerItem(
            name = "Instillinger",
            filledIcon = Icons.Filled.Settings,
            unfilledIcon = Icons.Outlined.Settings
        ),
        HamburgerItem(
            name = "Om Oss",
            filledIcon = Icons.Filled.Face,
            unfilledIcon = Icons.Outlined.Face
        )
    )
    return options
}