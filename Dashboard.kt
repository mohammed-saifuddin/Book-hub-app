package com.example.bookhub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bookhub.R
import com.example.bookhub.fragment.Aboutus
import com.example.bookhub.fragment.Dash
import com.example.bookhub.fragment.Favourite
import com.example.bookhub.fragment.Profile
import com.google.android.material.navigation.NavigationView

class Dashboard : AppCompatActivity() {
    lateinit var drawer: DrawerLayout
    lateinit var frame: FrameLayout
    lateinit var coordinator: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var navigation: NavigationView
    var previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        drawer=findViewById(R.id.drawer)
        frame=findViewById(R.id.frame)
        coordinator=findViewById(R.id.coordinator)
        toolbar=findViewById(R.id.toolbar)
        navigation=findViewById(R.id.navigation)
        setUpToolBar()
        openHome()
        navigation.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.dashboard ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame, Dash()
                    ).commit()
                    supportActionBar?.title="All Books"
                    drawer.closeDrawers()

                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame, Profile()
                    ).commit()
                    supportActionBar?.title="My profile"
                    drawer.closeDrawers()
                }
                R.id.favourite ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame, Favourite()
                    ).commit()
                    supportActionBar?.title="Favourite Books"
                    drawer.closeDrawers()
                }
                R.id.aboutus ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame, Aboutus()
                    ).commit()

                    supportActionBar?.title="About us"
                    drawer.closeDrawers()
                }
                R.id.logout ->{
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this)
                   Toast.makeText(this,"you are logged out",Toast.LENGTH_SHORT).show()
                    drawer.closeDrawers()
                }


            }
            return@setNavigationItemSelectedListener true
        }
        val actionBarDrawerToggle=ActionBarDrawerToggle(this,drawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
    fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book hub"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        if(id==android.R.id.home){
            drawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openHome(){
        val fragment= Dash()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,fragment)
        transaction.commit()
        supportActionBar?.title="All Books"
        navigation.setCheckedItem(R.id.dashboard)
    }
    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            !is Dash -> openHome()

            else -> super.onBackPressed()
        }
    }
}