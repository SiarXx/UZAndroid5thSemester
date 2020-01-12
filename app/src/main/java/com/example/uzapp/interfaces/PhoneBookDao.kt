package com.example.uzapp.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uzapp.entities.PhoneBookEntity

@Dao
interface PhoneBookDao{

    @Query("SELECT * FROM PhoneBookEntity")
    fun selectAll():ArrayList<PhoneBookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhoneBooks(vararg phoneBooks:PhoneBookEntity)

    @Query("DELETE from PhoneBookEntity")
    fun deleteAllPhoneBooks()

    @Query("SELECT * FROM PhoneBookEntity WHERE id == :id")
    fun selectPhoneBookPosition(id: Int?): List<PhoneBookEntity>

    @Query("SELECT * FROM PhoneBookEntity WHERE  name like :search OR lastname like :search OR email like :search OR number like :search")
    fun findMatchingNumber(search: String?): List<PhoneBookEntity>
}