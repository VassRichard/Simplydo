package com.example.noci.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "noteTitle")
    val title: String = "",

    @ColumnInfo(name = "noteDescription")
    val description: String = "",

    @ColumnInfo(name = "noteDate")
    val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString(),

    @ColumnInfo(name = "noteType")
    val type: Int = -1

)