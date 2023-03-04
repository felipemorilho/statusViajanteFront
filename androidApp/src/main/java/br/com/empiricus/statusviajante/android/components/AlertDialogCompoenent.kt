import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyAlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false; onDismiss() },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false; onDismiss() },
                ) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
