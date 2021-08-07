package com.example.bookhub.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface BookDao {
    @Insert
    fun insert(bookEntity: BookEntity)

    @Delete
    fun delete(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>

    @Query("select * from books where book_id=:bookId")
    fun getBookById(bookId:String):BookEntity
}