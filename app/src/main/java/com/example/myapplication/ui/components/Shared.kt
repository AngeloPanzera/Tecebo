package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.PurplePrimary
import com.example.myapplication.ui.theme.TextPrimary

/** Capa de música/álbum: um quadrado com gradiente e ícone de nota musical */
@Composable
fun Cover(g1: Color, g2: Color, size: Int = 48, radius: Int = 10) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(radius.dp))
            .background(Brush.linearGradient(listOf(g1, g2))),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.MusicNote,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.25f),
            modifier = Modifier.size((size / 2.2).dp)
        )
    }
}

/** Avatar circular com as iniciais do nome (sem carregar imagem de rede) */
@Composable
fun Avi(name: String, size: Int = 40, online: Boolean = false) {
    val initials = name.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString("")
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(PurplePrimary.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.uppercase(),
            color = TextPrimary,
            fontSize = (size / 2.5).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Linha de estrelas para exibir uma nota (1 a 5), somente leitura */
@Composable
fun Stars(value: Int, size: Int = 14) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= value) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null,
                tint = if (i <= value) Color(0xFF22C55E) else Color(0xFF3D3D55),
                modifier = Modifier.size(size.dp)
            )
        }
    }
}
