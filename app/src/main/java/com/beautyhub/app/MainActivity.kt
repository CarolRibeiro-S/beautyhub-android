package com.beautyhub.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.beautyhub.app.ui.theme.BeautyHubTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = LanguageManager.getSavedLanguage(newBase)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = newBase.resources.configuration
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Adicione esta linha para inicializar o SessionManager
        SessionManager.init(this)

        setContent {
            BeautyHubTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}