package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.FriendRequest
import com.example.myapplication.data.MockData
import com.example.myapplication.data.User
import com.example.myapplication.ui.components.Avi
import com.example.myapplication.ui.theme.*

@Composable
fun FriendsScreen() {
    // Guarda, por id, se o usuário decidiu seguir alguém que estava em solicitações/sugestões
    var followingOverrides by remember { mutableStateOf(mapOf<String, Boolean>()) }

    val following = MockData.users.filter { it.isFollowing && it.id != "me" }
    val suggested = MockData.users.filter { !it.isFollowing && it.id != "me" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(top = 24.dp)
    ) {
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text("Amigos", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    "${following.size} seguindo · ${MockData.requests.size} solicitações",
                    color = TextMuted, fontSize = 12.sp
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        // ── Solicitações ──
        if (MockData.requests.isNotEmpty()) {
            item { SectionLabel("Solicitações") }
            items(MockData.requests) { req ->
                RequestCard(
                    request = req,
                    isFollowing = followingOverrides[req.id] == true,
                    onToggle = {
                        followingOverrides = followingOverrides + (req.id to (followingOverrides[req.id] != true))
                    }
                )
            }
            item { Spacer(Modifier.height(20.dp)) }
        }

        // ── Seguindo ──
        item { SectionLabel("Seguindo") }
        items(following) { user -> FollowingCard(user) }

        item { Spacer(Modifier.height(20.dp)) }

        // ── Sugestões ──
        if (suggested.isNotEmpty()) {
            item { SectionLabel("Sugestões para você") }
            items(suggested) { user ->
                SuggestedCard(
                    user = user,
                    isFollowing = followingOverrides[user.id] == true,
                    onToggle = {
                        followingOverrides = followingOverrides + (user.id to (followingOverrides[user.id] != true))
                    }
                )
            }
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = TextPrimary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

@Composable
private fun RequestCard(request: FriendRequest, isFollowing: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avi(name = request.name, size = 40)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(request.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("@${request.username} · ${request.reviewCount} reviews", color = TextMuted, fontSize = 11.sp)
        }
        TextButton(onClick = { }) { Text("Ignorar", color = TextMuted, fontSize = 12.sp) }
        Spacer(Modifier.width(4.dp))
        FollowButton(isFollowing, onToggle)
    }
}

@Composable
private fun FollowingCard(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Avi(name = user.name, size = 44)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(user.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text("@${user.username}", color = TextMuted, fontSize = 11.sp)
            }
            if (user.lastSeenLabel.isNotEmpty()) {
                Text("${user.lastSeenLabel} atrás", color = TextFaint, fontSize = 10.sp)
            }
        }
        if (user.nowPlayingId != null) {
            val track = MockData.tracks.find { it.id == user.nowPlayingId }
            if (track != null) {
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceLight)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    com.example.myapplication.ui.components.Cover(g1 = track.g1, g2 = track.g2, size = 32, radius = 8)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(track.title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(track.artist, color = TextMuted, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestedCard(user: User, isFollowing: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avi(name = user.name, size = 40)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(user.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("@${user.username} · ${user.reviewCount} reviews", color = TextMuted, fontSize = 11.sp)
        }
        FollowButton(isFollowing, onToggle)
    }
}

@Composable
private fun FollowButton(isFollowing: Boolean, onToggle: () -> Unit) {
    Button(
        onClick = onToggle,
        colors = if (isFollowing) {
            ButtonDefaults.buttonColors(containerColor = GreenAccent.copy(alpha = 0.15f), contentColor = GreenAccent)
        } else {
            ButtonDefaults.buttonColors(containerColor = PurplePrimary, contentColor = androidx.compose.ui.graphics.Color.White)
        },
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(34.dp)
    ) {
        Text(if (isFollowing) "Seguindo" else "Seguir", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}
