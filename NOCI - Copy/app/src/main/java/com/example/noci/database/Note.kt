package com.example.noci.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "noteTitle")
    val title: String = "",

    @ColumnInfo(name = "noteDateSet")
    val noteDate: String = "",

    @ColumnInfo(name = "noteDate")
    val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString(),

    @ColumnInfo(name = "noteType")
    val type: Int = -1

) : Parcelable

//{
//    fun toMap(): HashMap<String, Any> {
//        return hashMapOf(
//            "id" to id,
//            "title" to title,
//            "noteDate" to noteDate,
//            "date" to date,
//            "type" to type,
//        )
//    }
//
//    constructor(map: HashMap<String, Any>) : this(
//        map["id"] as Int,
//        map["email"] as String,
//        map["name"] as String,
//        map["password"] as String,
//        map["image"] as Int,
//    )
//
//}