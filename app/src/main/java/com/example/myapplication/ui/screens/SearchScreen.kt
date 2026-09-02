package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.MockData
import com.example.myapplication.data.Track
import com.example.myapplication.ui.components.Cover
import com.example.myapplication.ui.theme.*

private data class Genre(val label: String, val g1: Color, val g2: Color, val emoji: String)

private val GENRES = listOf(
    Genre("Eletrônico", GradientPurpleDark1, GradientPurpleDark2, "⚡"),
    Genre("Indie", GradientGreenDark1, GradientGreenDark2, "🎸"),
    Genre("Ambient", GradientCyan1, GradientCyan2, "🌊"),
    Genre("Synthwave", GradientViolet1, GradientViolet2, "🔮"),
    Genre("Experimental", GradientPink1, GradientPink2, "🧪"),
    Genre("Lo-fi", GradientBrown1, GradientBrown2, "☕")
)

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    val matchedTracks = if (query.isNotEmpty()) {
        MockData.tracks.filter {
            it.title.contains(query, ignoreCase = true) ||
                it.artist.contains(query, ignoreCase = true) ||
                it.album.contains(query, ignoreCase = true)
        }
    } else emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Buscar",
            color = TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Buscar músicas, artistas ou álbuns...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = TextMuted) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CardBg,
                unfocusedContainerColor = CardBg,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedBorderColor = PurplePrimary,
                unfocusedBorderColor = SurfaceLight
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(24.dp))

        if (query.isNotEmpty()) {
            // ── Resultados da busca ──
            if (matchedTracks.isEmpty()) {
                Text(
                    text = "Nenhum resultado para \"$query\"",
                    color = TextMuted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            } else {
                Text(
                    text = "MÚSICAS",
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBg)
                ) {
                    matchedTracks.forEach { track -> TrackRow(track) }
                }
            }
        } else {
            // ── Categorias ──
            SectionTitle("Descubra novas músicas")
            GenreGrid()

            Spacer(Modifier.height(28.dp))

            // ── Álbuns populares ──
            SectionTitle("Álbuns populares")
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MockData.albums.forEach { album ->
                    Column(modifier = Modifier.width(112.dp)) {
                        Cover(g1 = album.g1, g2 = album.g2, size = 112, radius = 14)
                        Spacer(Modifier.height(6.dp))
                        Text(album.title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                        Text(album.artist, color = TextMuted, fontSize = 11.sp, maxLines = 1)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = TextPrimary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

@Composable
private fun GenreGrid() {
    // Grade 2 colunas simples usando Row + Column (sem LazyVerticalGrid, que não foi visto em aula)
    val rows = GENRES.chunked(2)
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        rows.forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                pair.forEach { genre -> GenreCard(genre, Modifier.weight(1f)) }
                if (pair.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun GenreCard(genre: Genre, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(listOf(genre.g1, genre.g2)))
            .clickable { }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(genre.emoji, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Text(genre.label, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun TrackRow(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cover(g1 = track.g1, g2 = track.g2, size = 44, radius = 10)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(track.title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("${track.artist} · ${track.album}", color = TextMuted, fontSize = 12.sp)
        }
    }
}
