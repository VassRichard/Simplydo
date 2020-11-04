package com.example.noci.database_lists.items

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "item_table")
data class Items @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "nameTitle")
    val name: String = "",

    @ColumnInfo(name = "noteId")
    val noteId: Int

) : Parcelable