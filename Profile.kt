package com.example.bookhub.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.bookhub.R
import com.example.bookhub.databases.DatabaseHelper
import org.w3c.dom.Text


class Profile : Fragment() {
    lateinit var image1:ImageView
    lateinit var name1:TextView
    lateinit var email:TextView
    lateinit var address:TextView
    private lateinit var db:DatabaseHelper
    lateinit var sharedPreferences: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        image1=view.findViewById(R.id.image1)
        name1=view.findViewById(R.id.name1)
        email=view.findViewById(R.id.email)
        address=view.findViewById(R.id.address)
         sharedPreferences=(activity as FragmentActivity).getSharedPreferences("register",Context.MODE_PRIVATE)
        val n=sharedPreferences.getString("name",name1.text.toString())
        val e=sharedPreferences.getString("email",email.text.toString())
        val a=sharedPreferences.getString("address",address.text.toString())
        name1.text=n
        email.text=e
        address.text=a

        return view

    }


}