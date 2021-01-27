package com.example.studentslistadvanced.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    val firstName: String,
    val lastName: String? = null,
    val grade: Double? = null,
    val image: String? = null,
    val id: Int
) : Parcelable
