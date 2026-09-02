package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.FriendsScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.SearchScreen
import com.example.myapplication.ui.theme.BgDark
import com.example.myapplication.ui.theme.CardBg
import com.example.myapplication.ui.theme.PurplePrimary
import com.example.myapplication.ui.theme.SoundAppTheme
import com.example.myapplication.ui.theme.TextMuted

// As 3 telas que a tela principal alterna entre si.
enum class AppScreen { BUSCAR, AMIGOS, PERFIL }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoundAppTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    // Estado que guarda qual tela está sendo exibida no momento.
    var currentScreen by remember { mutableStateOf(AppScreen.BUSCAR) }

    Surface(color = BgDark, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = BgDark,
            bottomBar = { BottomBar(currentScreen) { currentScreen = it } }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentScreen) {
                    AppScreen.BUSCAR -> SearchScreen()
                    AppScreen.AMIGOS -> FriendsScreen()
                    AppScreen.PERFIL -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
private fun BottomBar(current: AppScreen, onSelect: (AppScreen) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomBarItem("Buscar", Icons.Outlined.Search, Icons.Filled.Search, current == AppScreen.BUSCAR) {
            onSelect(AppScreen.BUSCAR)
        }
        BottomBarItem("Amigos", Icons.Outlined.Groups, Icons.Filled.Groups, current == AppScreen.AMIGOS) {
            onSelect(AppScreen.AMIGOS)
        }
        BottomBarItem("Perfil", Icons.Outlined.Person, Icons.Filled.Person, current == AppScreen.PERFIL) {
            onSelect(AppScreen.PERFIL)
        }
    }
}

@Composable
private fun BottomBarItem(
    label: String,
    outlinedIcon: ImageVector,
    filledIcon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = if (selected) filledIcon else outlinedIcon,
            contentDescription = label,
            tint = if (selected) PurplePrimary else TextMuted
        )
        Text(
            text = label,
            color = if (selected) PurplePrimary else TextMuted,
            fontSize = 11.sp
        )
    }
}
