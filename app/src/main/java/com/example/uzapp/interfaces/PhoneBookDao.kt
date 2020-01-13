package com.example.uzapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.uzapp.entities.PhoneBookEntity

@Dao
interface PhoneBookDao{

    @Query("SELECT * FROM PhoneBookEntity")
    fun selectAll():List<PhoneBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoneBooks(vararg phoneBooks:PhoneBookEntity)

    @Query("UPDATE PhoneBookEntity SET name = :name, lastname = :lastname, sex = :sex, number = :number, email = :mail, avatar = :avatar WHERE id = :id")
    fun updatePhoneBooks(id:Int, name:String, lastname:String, sex:String, number:String, mail:String, avatar:String)

    @Query("DELETE from PhoneBookEntity")
    fun deleteAllPhoneBooks()

    @Query("DELETE from PhoneBookEntity WHERE id == :id")
    fun deletePhoneBook(id: Int?)

    @Query("SELECT * FROM PhoneBookEntity WHERE id == :id")
    fun selectPhoneBookPosition(id: Int?): List<PhoneBookEntity>

    @Query("SELECT * FROM PhoneBookEntity WHERE  name like :search OR lastname like :search OR email like :search OR number like :search")
    fun findMatchingNumber(search: String?): List<PhoneBookEntity>
}