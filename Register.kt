package com.example.bookhub.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.bookhub.R
import com.example.bookhub.databases.DatabaseHelper
import com.example.bookhub.databases.RegisterHelper
import com.example.bookhub.fragment.Dash
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

import kotlin.Exception

class Register : AppCompatActivity() {

    lateinit var name:EditText
    lateinit var email:EditText

    lateinit var address:EditText
    lateinit var password:EditText

    lateinit var register:Button

     var context= this
    lateinit var sharedPreferences: SharedPreferences



  

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        name=findViewById(R.id.name)

        email=findViewById(R.id.email)

        address=findViewById(R.id.address)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register)


        val db = DatabaseHelper(context)
         sharedPreferences=getSharedPreferences("register",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()

        register.setOnClickListener {
            val name1 = name.text.toString().trim()




            val email1 = email.text.toString().trim()
            val password1 = password.text.toString().trim()
            val address1 = address.text.toString().trim()
            editor.putString("name",name1)
            editor.putString("email",email1)
            editor.putString("address",address1)
            editor.apply()
            if(name1.isEmpty()){
                Toast.makeText(this,"please enter the name",Toast.LENGTH_SHORT).show()
            }else if(email1.isEmpty()){
                Toast.makeText(this,"please enter the email",Toast.LENGTH_SHORT).show()
            }else if(password1.isEmpty()){
                Toast.makeText(this,"please enter the password",Toast.LENGTH_SHORT).show()
            }else if(address1.isEmpty()){
                Toast.makeText(this,"please enter the address",Toast.LENGTH_SHORT).show()
            }else{
                val isExists=db.isUserPresent(email1,password1)
                if(isExists){
                    Toast.makeText(this,"Email already exists",Toast.LENGTH_SHORT).show()
                }
                else{
                    try {



                        val registerHelper = RegisterHelper(name1, email1, address1, password1)

                        val db = DatabaseHelper(context)
                        db.insert(registerHelper)
                        db.close()
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)



                    }catch (e:Exception){

                        Toast.makeText(this, "error occured  ", Toast.LENGTH_SHORT).show()
                    }
                }

            }



        }


    }



}