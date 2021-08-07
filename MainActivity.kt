package com.example.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bookhub.R
import com.example.bookhub.databases.DatabaseHelper


import android.content.Intent as Intent1

class MainActivity : AppCompatActivity() {
    lateinit var login:Button
    lateinit var register:TextView
    lateinit var forgot:TextView
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var db: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login=findViewById(R.id.login)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register)
        db= DatabaseHelper(this)


        forgot=findViewById(R.id.forgot)
        forgot.setOnClickListener {
            val intent= Intent1(this, Forgot::class.java)
            startActivity(intent)
        }
        register.setOnClickListener {
            val intent= Intent1(this, Register::class.java)
            startActivity(intent)
        }
        login. setOnClickListener {
            val email=email.text.toString().trim()
            val pass=password.text.toString().trim()
            if(email.isEmpty()||pass.isEmpty()){
                Toast.makeText(this,"Please enter the credentials",Toast.LENGTH_SHORT).show()
            }else{
                        val isRes=db.isUserPresent(email,pass)
                        if(isRes){
                            val intent=Intent1(this,Dashboard::class.java)

                            startActivity(intent)
                            Toast.makeText(this,"Welcome to the book hub app",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Invalid credentials",Toast.LENGTH_SHORT).show()
                        }

                    }


                }
            }
        }







