package br.com.empiricus.statusviajante.android.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.R

@Composable
fun SplashScreen() {
    MyApplicationTheme {
        Scaffold() {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {
                    Image(
                        painter = painterResource(id = R.drawable.logo_branca),
                        contentDescription = "",
                        modifier = Modifier.width(250.dp)
                            .height(250.dp),
                    )              }
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenpreview(){
    SplashScreen ()
}