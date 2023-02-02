package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun topBarComponent(
    modifier: Modifier = Modifier,
    onClickNav: () -> Unit = {}
){
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
    ){
        IconButton(onClick = onClickNav){
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = ""
            )
        }
    }

}