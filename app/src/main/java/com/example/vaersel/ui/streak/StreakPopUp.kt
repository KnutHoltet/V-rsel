package com.example.vaersel.ui.streak

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vaersel.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreakPopUp(
        onDismissRequest: () -> Unit,
        currentStreak: Int,
        streakRecord: Int
) {
    BasicAlertDialog(onDismissRequest = {
        onDismissRequest()
    }
    ) {
        Card {
            Row {
                Column {
                    Text("Nåværende streak: $currentStreak",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("Streak rekord: $streakRecord",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                    )
                }

                Column(Modifier.padding(vertical = 8.dp)) {
                    val streakFlame = R.drawable.streak_flame
                    Icon(
                        painter = painterResource(id = streakFlame),
                        contentDescription = "Streak description",
                        tint = Color(0xFFE99F39),
                        modifier = Modifier.size(90.dp)
                    )
                }
            }
        }
    }
}

