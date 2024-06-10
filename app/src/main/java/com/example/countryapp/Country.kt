package com.example.countryapp

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val capital: String,
    val flag: String
) : Parcelable
@Parcelize
data class CountryInfo(
    val name: Name,
    val flags: Flags
) : Parcelable

@Parcelize
data class Name(
    val common: String
) : Parcelable

@Parcelize
data class Flags(
    val svg: String,
    val png: String
) : Parcelable 