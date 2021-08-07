package com.example.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bookhub.R
import com.example.bookhub.databases.DatabaseHelper
import com.example.bookhub.databases.RegisterHelper

class Forgot : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var reset:Button

    val context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        reset=findViewById(R.id.reset)
        reset.setOnClickListener {
            //val email=email.text.toString()
            //val password=password.text.toString()




                Toast.makeText(this,"first register your self", Toast.LENGTH_SHORT).show()


        }
    }
}