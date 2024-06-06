package com.example.countryapp

// FavoriteCountry.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_countries")
data class FavoriteCountry(
    @PrimaryKey val name: String,
    val capital: String,
    val flag: String
)
