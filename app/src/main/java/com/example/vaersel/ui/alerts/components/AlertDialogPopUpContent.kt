import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vaersel.data.alerts.icons.alertIcon

@Composable
fun AlertDialogPopUpContent(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    alerts: List<HashMap<String, String>>
) {
    AlertDialog(
        title = {
            Text("Dagens varsler")
        },
        text = {

            LazyRow {
                items(alerts) {
                    Card (
                        Modifier
                            .width(240.dp)
                            .padding(8.dp)
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier  = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                            ) {

                            if (
                                it["iconString"] != null
                                && it["eventName"] != null
                                && it["description"] != null
                                && it["color"] != null
                            ){
                                Image(painter = painterResource(id = alertIcon(it["iconString"]!!)), contentDescription = "farevarsel ikon", modifier = Modifier.size(60.dp))
                                val color = when(it["color"]) {
                                    "Gul" -> "Gult"
                                    "Rød" -> "Rødt"
                                    else -> "Oransje"
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text  = "$color farevarsel: \n" + it["eventName"]!!,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = it["description"]!!,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Bekreft")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                // Text("Dismiss")
            }
        }
    )
}
