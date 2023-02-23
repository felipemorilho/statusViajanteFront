package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.R


@Composable
fun topBarComponent(
    modifier: Modifier = Modifier
){
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.85f),
                imageVector = ImageVector.vectorResource(id = R.drawable.logobarra),
                contentDescription = "Icon aviao",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondaryVariant)
            )
        }
    }

}