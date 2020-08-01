package com.example.prm_02

import android.content.Context
import androidx.room.Room

class DbAccess private constructor(applicationContext: Context) {
    companion object {
        private var dbAccess: DbAccess? = null
        fun getInstance(applicationContext: Context): DbAccess {
            return dbAccess ?: DbAccess(applicationContext).also { dbAccess = it }
        }
    }

    val db by lazy {
        Room.databaseBuilder(applicationContext, NotesDB::class.java, "notes.db").fallbackToDestructiveMigration()
            .build()
    }
}