package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp


@Composable
fun topBarComponent(
    modifier: Modifier = Modifier
){
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
    ){
        Image(
            modifier = Modifier.fillMaxWidth(),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondaryVariant),
            imageVector = Icons.Default.Home,
            contentDescription = "Logo Status Viajante",
        )
    }

}