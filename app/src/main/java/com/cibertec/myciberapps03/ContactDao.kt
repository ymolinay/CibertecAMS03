package com.cibertec.myciberapps03

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact)

    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): List<Contact>

    @Delete
    fun delete(contact: Contact)
}