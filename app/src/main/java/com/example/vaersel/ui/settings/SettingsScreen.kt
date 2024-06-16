package com.example.vaersel.ui.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel, innerPadding: PaddingValues, modifier: Modifier = Modifier){

    LazyColumn(
        modifier
            .padding(innerPadding)
    )
    {
        item{ Spacer(modifier = Modifier.height(16.dp)) }
        item{
            Text("Tilpasning:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .wrapContentSize(Alignment.BottomStart))
        }

        item{ Spacer(Modifier.height(8.dp)) }

        // dropdown menu for selecting preferred temperature adjustment
        item{
            ExposedDropdownMenuBox(
                expanded = viewModel.tempExpanded.collectAsState().value,
                onExpandedChange = {viewModel.tempExpandedChange(it)},
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = 20.dp)
                    .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            ) {

                TextField(
                    value = viewModel.intToTemp(viewModel.tempPref.collectAsState().value),
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.tempExpanded.collectAsState().value)},
                    modifier = modifier
                        .menuAnchor()
                        .width(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                ExposedDropdownMenu(modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(300.dp),expanded = viewModel.tempExpanded.collectAsState().value, onDismissRequest = { viewModel.tempExpandedChange(false) }) {
                    val tempChoice: List<Int> = listOf(-2, -1, 0, 1, 2)
                    tempChoice.forEach{ option -> DropdownMenuItem(
                        text = {
                            Text(viewModel.intToTemp(option))
                        },
                        onClick = {
                            viewModel.switchTemp(option)
                            viewModel.saveTempChoice(option)
                            viewModel.tempExpandedChange(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    ) }
                }
            }
        }

        item{
            Text("Utseende:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .wrapContentSize(Alignment.BottomStart))
        }

        item{ Spacer(Modifier.height(8.dp)) }

        item{
            Text("Tema:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .wrapContentSize(Alignment.BottomStart))
        }

        item{ Spacer(Modifier.height(4.dp)) }

        // dropdown menu for changing theme
        item{
            ExposedDropdownMenuBox(
                expanded = viewModel.themeExpanded.collectAsState().value,
                onExpandedChange = {viewModel.themeExpandedChange(it)},
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = 20.dp)
                    .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            ) {

                TextField(
                    value = viewModel.selectedTheme.collectAsState().value.themeName,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.themeExpanded.collectAsState().value)},
                    modifier = modifier
                        .menuAnchor()
                        .width(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                ExposedDropdownMenu(modifier = Modifier.clip(RoundedCornerShape(12.dp)),expanded = viewModel.themeExpanded.collectAsState().value, onDismissRequest = { viewModel.themeExpandedChange(false) }) {
                    val themeChoice: List<Int> = listOf(0, 1, 2, 3)

                    themeChoice.forEach{ option -> DropdownMenuItem(
                        text = {
                            Text(viewModel.intToTheme(option).themeName)
                               },
                        onClick = {
                            viewModel.switchTheme(option)
                            viewModel.saveThemeChoice(option)
                            viewModel.themeExpandedChange(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) }
                }
            }
        }

        item{
            Text("Lys/mørk modus:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .wrapContentSize(Alignment.BottomStart))
        }

        item{ Spacer(Modifier.height(4.dp)) }

        //dropdown menu for switching between light mode, dark mode or system settings
        item{
            ExposedDropdownMenuBox(
                expanded = viewModel.lightOrDarkExpanded.collectAsState().value,
                onExpandedChange = {viewModel.lightOrDarkExpandedChange(it)},
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(bottom = 20.dp)
                    .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            ) {

                TextField(
                    value = viewModel.intToLightOrDark(viewModel.lightOrDarkPref.collectAsState().value),
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.lightOrDarkExpanded.collectAsState().value)},
                    modifier = modifier
                        .menuAnchor()
                        .width(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                ExposedDropdownMenu(expanded = viewModel.lightOrDarkExpanded.collectAsState().value, onDismissRequest = { viewModel.lightOrDarkExpandedChange(false) }) {
                    val lightOrDarkChoice: List<Int> = listOf(0, 1, 2)
                    lightOrDarkChoice.forEach{ option -> DropdownMenuItem(
                        text = {
                            Text(viewModel.intToLightOrDark(option))
                        },
                        onClick = {
                            viewModel.switchLightOrDark(option)
                            viewModel.saveLightOrDarkChoice(option)
                            viewModel.lightOrDarkExpandedChange(false)
                            viewModel.correctLightOrDarkMode(
                                when(option) {
                                    1 -> false
                                    2 -> true
                                    else -> viewModel.isDarkMode.value
                                }
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    ) }
                }
            }
        }

        // switch on and off high contrast mode
        item{
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .wrapContentSize(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ){
                Switch(
                    checked = viewModel.highContrastOn.collectAsState().value,
                    onCheckedChange = {viewModel.highContrastChange(it)},
                    modifier = modifier
                        .wrapContentSize(Alignment.CenterEnd)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    "Høy Kontrast",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
    }
}