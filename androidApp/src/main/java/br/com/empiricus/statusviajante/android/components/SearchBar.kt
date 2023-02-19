package br.com.empiricus.statusviajante.android.components

import br.com.empiricus.statusviajante.android.R

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { "Search" },
                modifier = Modifier
                    .focusable(true)
                    .fillMaxWidth(0.6f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                maxLines = 1,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                onClick = {
                    onSearch(searchQuery)
                }
            ) {
                Text(text = "Buscar")
            }
        }
    }
}
