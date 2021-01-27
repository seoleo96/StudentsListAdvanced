package com.example.studentslistadvanced.di

import android.app.Application
import com.example.studentslistadvanced.db.SimpleDB

class DI : Application() {
    val db = SimpleDB.getInstance()
}