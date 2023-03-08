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

@Composable
fun ConfirmAlertDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onCancel.invoke() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(8.dp)
            ) {
                TextButton(onClick = {onCancel.invoke()}) {
                    Text(text = "Cancelar")
                }
                TextButton(onClick = onConfirm) {
                    Text(text = "Confirmar")
                }
            }
        }
    )
}
