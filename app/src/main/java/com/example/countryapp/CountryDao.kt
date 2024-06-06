package com.example.countryapp

// CountryDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert
    suspend fun insert(country: FavoriteCountry)

    @Query("SELECT * FROM favorite_countries")
    suspend fun getAllFavorites(): List<FavoriteCountry>

    @Query("DELETE FROM favorite_countries WHERE name = :name")
    suspend fun deleteByName(name: String)
}
