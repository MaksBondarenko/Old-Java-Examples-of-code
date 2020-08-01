package com.example.prm_02

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "description")
class Note(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var photoId : String,
    var photoDate : String,
    var latitude: Double,
    var longitude: Double,
    var content: String
)