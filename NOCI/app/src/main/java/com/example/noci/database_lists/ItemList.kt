package com.example.noci.database_lists


import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "list_table")
data class ItemList @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "noteTitle")
    val title: String = "",

    @ColumnInfo(name = "noteDateSet")
    val noteDate: String = "",

    @ColumnInfo(name = "noteDate")
    val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString(),

) : Parcelable