package com.example.countryapp

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface CountryApi {
    @GET("v3.1/all")
    suspend fun getAllCountries(): List<Country>
    fun getCountryByName(@Path("name") name: String): Call<List<CountryInfo>>
}