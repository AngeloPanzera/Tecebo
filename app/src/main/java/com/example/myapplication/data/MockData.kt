package com.example.myapplication.data

import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.*

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val albumId: String,
    val g1: Color,
    val g2: Color,
    val playCount: Int = 0
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val g1: Color,
    val g2: Color
)

data class User(
    val id: String,
    val name: String,
    val username: String,
    val bio: String = "",
    val isFollowing: Boolean = false,
    val nowPlayingId: String? = null,
    val lastSeenLabel: String = "",
    val albumCount: Int = 0,
    val songCount: Int = 0,
    val friendCount: Int = 0,
    val reviewCount: Int = 0
)

data class Review(
    val id: String,
    val userId: String,
    val trackId: String,
    val rating: Int,
    val text: String,
    val dateLabel: String = ""
)

data class FeedItem(
    val id: String,
    val userId: String,
    val trackId: String,
    val reviewId: String? = null,
    val timeLabel: String
)

data class FriendRequest(
    val id: String,
    val name: String,
    val username: String,
    val reviewCount: Int
)

object MockData {

    val tracks = listOf(
        Track("t1", "Orbit Decay", "Nova Crestwood", "Starless Hours", "a1", GradientPurpleDark1, GradientPurpleDark2, 18420),
        Track("t2", "Glass Architecture", "Solstice Effect", "Glass Architecture", "a4", GradientPink1, GradientPink2, 9210),
        Track("t3", "Midnight Gradient", "Zara Nightfall", "Dusk Protocol", "a3", GradientViolet1, GradientViolet2, 32110),
        Track("t4", "Void Waltz", "Nova Crestwood", "Starless Hours", "a1", GradientPurpleDark1, GradientPurpleDark2, 7040),
        Track("t5", "Last Signal", "The Amber Syndicate", "Chrome Sirens", "a2", GradientCyan1, GradientCyan2, 15230),
        Track("t6", "Neon Hymn", "Zara Nightfall", "Dusk Protocol", "a3", GradientViolet1, GradientViolet2, 21870)
    )

    val albums = listOf(
        Album("a1", "Starless Hours", "Nova Crestwood", GradientPurpleDark1, GradientPurpleDark2),
        Album("a2", "Chrome Sirens", "The Amber Syndicate", GradientCyan1, GradientCyan2),
        Album("a3", "Dusk Protocol", "Zara Nightfall", GradientViolet1, GradientViolet2)
    )

    val users = listOf(
        User("me", "Alex Nova", "alexnova", "chasing sounds through the dark. sempre em loop.",
            albumCount = 142, songCount = 1847, friendCount = 64, reviewCount = 89, nowPlayingId = "t3"),
        User("u1", "Ana Torres", "stellar_ana", isFollowing = true, nowPlayingId = "t1", lastSeenLabel = "15 min", reviewCount = 28),
        User("u2", "Kai Santos", "midnight_kai", isFollowing = true, nowPlayingId = "t3", lastSeenLabel = "2h", reviewCount = 41),
        User("u3", "Celestia Ren", "celestia_ren", isFollowing = true, lastSeenLabel = "32 min", reviewCount = 19),
        User("u4", "Dani Cruz", "dani_sounds", isFollowing = false, reviewCount = 28),
        User("u5", "Omar Solis", "wave_omar", isFollowing = false, reviewCount = 103)
    )

    val requests = listOf(
        FriendRequest("r1", "Dani Cruz", "dani_sounds", 28),
        FriendRequest("r2", "Omar Solis", "wave_omar", 103)
    )

    val reviews = listOf(
        Review("rv1", "u1", "t1", 5, "Uma obra de arte. Nova Crestwood entregou algo completamente diferente do que eu esperava.", "há 15 min"),
        Review("rv2", "u3", "t2", 4, "Muito bom, mas alguns momentos se arrastam.", "há 32 min")
    )

    val feed = listOf(
        FeedItem("f1", "u1", "t1", "rv1", "15 min"),
        FeedItem("f2", "u3", "t2", "rv2", "32 min"),
        FeedItem("f3", "u2", "t3", null, "1h")
    )
}
