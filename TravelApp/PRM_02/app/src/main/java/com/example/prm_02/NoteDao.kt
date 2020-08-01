package com.example.prm_02

import android.database.sqlite.SQLiteDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {

    @Query("SELECT * FROM description")
    fun getAll(): List<Note>

    @Query("SELECT * FROM description WHERE id = :id")
    fun getNoteById(id: Int): Note

    @Insert
    fun insert(note: Note)

}