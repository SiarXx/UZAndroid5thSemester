package com.example.uzapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PhoneBookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "lastname") var lastName: String? = null,
    @ColumnInfo(name = "sex") var sex: String? = null,
    @ColumnInfo(name = "number") var number: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "avatar") var avatar: String? = null
    ):Serializable