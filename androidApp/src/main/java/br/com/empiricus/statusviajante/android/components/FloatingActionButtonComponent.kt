package br.com.empiricus.statusviajante.android.components
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*

@Composable
fun MyFloatingActionButtonDelete(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    message: String,
    title: String
){

    var dialogOpen by remember { mutableStateOf(false) }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = { dialogOpen = false },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = {
                    onConfirm()
                    dialogOpen = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    onCancel()
                    dialogOpen = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    FloatingActionButton(
        onClick = {dialogOpen = true},
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(Icons.Filled.Delete, contentDescription = "Adicionar")
    }
}
