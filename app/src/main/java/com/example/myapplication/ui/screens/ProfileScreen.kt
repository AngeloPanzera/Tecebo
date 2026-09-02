package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.MockData
import com.example.myapplication.ui.components.Avi
import com.example.myapplication.ui.components.Cover
import com.example.myapplication.ui.components.Stars
import com.example.myapplication.ui.theme.*

private enum class ProfileTab(val label: String) {
    OVERVIEW("Visão Geral"), ALBUMS("Álbuns"), SONGS("Músicas"), REVIEWS("Reviews")
}

@Composable
fun ProfileScreen() {
    val me = MockData.users.first { it.id == "me" }
    var tab by remember { mutableStateOf(ProfileTab.OVERVIEW) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(top = 32.dp)
    ) {
        // ── Cabeçalho ──
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Avi(name = me.name, size = 80)
            Spacer(Modifier.height(10.dp))
            Text(me.name, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("@${me.username}", color = PurplePrimary, fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Text(me.bio, color = TextSecondary, fontSize = 13.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(Modifier.height(14.dp))
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
            ) { Text("Editar perfil", fontSize = 13.sp, fontWeight = FontWeight.SemiBold) }
        }

        Spacer(Modifier.height(20.dp))

        // ── Stats ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardBg)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(me.albumCount.toString(), "Álbuns")
            StatItem(me.songCount.toString(), "Músicas")
            StatItem(me.friendCount.toString(), "Amigos")
            StatItem(me.reviewCount.toString(), "Reviews")
        }

        Spacer(Modifier.height(16.dp))

        // ── Tabs ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CardBg)
                .padding(4.dp)
        ) {
            ProfileTab.values().forEach { t ->
                val selected = tab == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selected) PurplePrimary else CardBg)
                        .clickable { tab = t }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        t.label,
                        color = if (selected) androidx.compose.ui.graphics.Color.White else TextFaint,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        when (tab) {
            ProfileTab.OVERVIEW -> OverviewTab()
            ProfileTab.ALBUMS -> AlbumsTab()
            ProfileTab.SONGS -> SongsTab()
            ProfileTab.REVIEWS -> ReviewsTab()
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextFaint, fontSize = 10.sp)
    }
}

@Composable
private fun OverviewTab() {
    Column(Modifier.padding(horizontal = 20.dp)) {
        Text("RECENTEMENTE OUVIDO", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MockData.tracks.forEach { track ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(76.dp)) {
                    Cover(g1 = track.g1, g2 = track.g2, size = 76, radius = 12)
                    Spacer(Modifier.height(6.dp))
                    Text(track.title, color = TextSecondary, fontSize = 10.sp, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun AlbumsTab() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MockData.albums.forEach { album ->
            Column(modifier = Modifier.width(90.dp)) {
                Cover(g1 = album.g1, g2 = album.g2, size = 90, radius = 12)
                Spacer(Modifier.height(6.dp))
                Text(album.title, color = TextPrimary, fontSize = 11.sp, maxLines = 1)
                Text(album.artist, color = TextFaint, fontSize = 10.sp, maxLines = 1)
            }
        }
    }
}

@Composable
private fun SongsTab() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
    ) {
        MockData.tracks.forEachIndexed { i, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${i + 1}", color = TextFaint, fontSize = 12.sp, modifier = Modifier.width(20.dp))
                Cover(g1 = track.g1, g2 = track.g2, size = 44, radius = 10)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(track.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text("${track.artist} · ${track.album}", color = TextMuted, fontSize = 11.sp)
                }
                Text("${track.playCount}", color = TextFaint, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun ReviewsTab() {
    val myReviews = MockData.reviews.filter { it.userId == "me" }
    Column(Modifier.padding(horizontal = 20.dp)) {
        if (myReviews.isEmpty()) {
            Text("Nenhuma review ainda", color = TextMuted, fontSize = 13.sp)
        } else {
            myReviews.forEach { review ->
                val track = MockData.tracks.first { it.id == review.trackId }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardBg)
                        .padding(14.dp)
                ) {
                    Cover(g1 = track.g1, g2 = track.g2, size = 52, radius = 12)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(track.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text(track.artist, color = TextMuted, fontSize = 11.sp)
                        Spacer(Modifier.height(4.dp))
                        Stars(value = review.rating, size = 13)
                        Spacer(Modifier.height(4.dp))
                        Text(review.text, color = TextSecondary, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
