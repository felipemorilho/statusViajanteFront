package br.com.empiricus.statusviajante.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import br.com.empiricus.statusviajante.android.cadastroviagens.cadastroViagens
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            cadastroViagens(onBack = {true})
        }
    }
}

