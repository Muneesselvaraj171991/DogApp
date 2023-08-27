package com.dog.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val name: String,
    val subBreedName: String,
    val imageUrl: String
) : Parcelable