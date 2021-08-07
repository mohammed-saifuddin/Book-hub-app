package com.example.bookhub.databases

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.bookhub.databases.RegisterHelper


val DATABASE_NAME="book hub"
val TABLE_NAME="register"
val COL_ID="id"
val COL_NAME="name"
val COL_EMAIL="email"
val COL_ADDRESS="address"
val COL_PASSWORD="password"
lateinit var  cursor:Cursor

class DatabaseHelper(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COL_NAME + " VARCHAR," + COL_EMAIL + " VARCHAR PRIMARY KEY ,"+ COL_ADDRESS + " VARCHAR,"
                + COL_PASSWORD + " VARCHAR" + ")")

        db?.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
          db!!.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME)
       onCreate(db)
    }
    fun insert(registerHelper: RegisterHelper){
        val db =this.writableDatabase
        var cv=ContentValues()
        cv.put(COL_NAME,registerHelper.name)
        cv.put(COL_EMAIL,registerHelper.email)
        cv.put(COL_ADDRESS,registerHelper.address)
        cv.put(COL_PASSWORD,registerHelper.password)
        val res=db?.insert(TABLE_NAME,null,cv)
         if(res==-1.toLong()){
             Toast.makeText(context,"registration failed",Toast.LENGTH_SHORT).show()
         }
        else{
             Toast.makeText(context,"registration is successfull",Toast.LENGTH_SHORT).show()
         }

        db.close()

    }
    fun isUserPresent(email:String,password:String):Boolean {
        try {

            val column= arrayOf(email, password)
            val db = writableDatabase

             cursor=db.rawQuery("select * from register where email=? and password=?",column)

            val cursorCount = cursor.count
              cursor.close()

            if (cursorCount > 0){
                return true
            }
                return false



        }catch (e:Exception){
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show()
        }

        return true
    }



}