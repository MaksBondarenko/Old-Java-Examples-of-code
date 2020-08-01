package com.example.prm_02

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 3)
abstract class NotesDB : RoomDatabase() {
    abstract fun notes(): NoteDao
}