package com.example.bookhub

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.databases.BookDatabase
import com.example.bookhub.databases.BookEntity
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap
import kotlin.Exception

class Description : AppCompatActivity() {
    lateinit var image1:ImageView
    lateinit var desc:TextView
    lateinit var aboutus:TextView
    lateinit var name1:TextView
    lateinit var authorName:TextView
    lateinit var price:TextView
    lateinit var favourites:Button
    lateinit var rating:TextView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
   lateinit var toolbar: Toolbar
    private var bookId:String?="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        image1=findViewById(R.id.image1)
        desc=findViewById(R.id.description)
        aboutus=findViewById(R.id.aboutus)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book details"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        name1=findViewById(R.id.name1)
        authorName=findViewById(R.id.authorName)
        price=findViewById(R.id.textPrice)
        favourites=findViewById(R.id.favourites)
        val atitle=intent.getStringExtra("name")
        supportActionBar?.title=atitle
        rating=findViewById(R.id.textRating)

        progressBar=findViewById(R.id.progressBar)
        progressBar.visibility= View.VISIBLE
        progressLayout=findViewById(R.id.progressLayout)
        progressLayout.visibility=View.VISIBLE
        if(intent!=null){
            bookId=intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this@Description,"some went wrong",Toast.LENGTH_LONG).show()
        }
        if(bookId == "100"){
            Toast.makeText(this@Description,"error",Toast.LENGTH_LONG).show()
        }

        val queue=Volley.newRequestQueue(this@Description)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonParams=JSONObject()
        jsonParams.put("book_id",bookId)
        val jsonObjectRequest=object:JsonObjectRequest(Request.Method.POST,url,jsonParams,Response.Listener {
            try {
                val success=it.getBoolean("success")
                if(success){
                    val bookJsonObject=it.getJSONObject("book_data")
                    progressLayout.visibility=View.GONE
                    val bookImageUrl=bookJsonObject.getString("image")
                    Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(image1)
                    name1.text=bookJsonObject.getString("name")
                    authorName.text=bookJsonObject.getString("author")
                    price.text=bookJsonObject.getString("price")
                    rating.text=bookJsonObject.getString("rating")
                    desc.text=bookJsonObject.getString("description")
                    val bookEntity=BookEntity(
                        bookId?.toInt() as Int,
                        name1.text.toString(),
                        authorName.text.toString(),
                        price.text.toString(),
                        rating.text.toString(),
                        desc.text.toString(),
                        bookImageUrl
                    )
                    val checkFav=DBAsynTask(applicationContext,bookEntity,1).execute()
                    val isFav=checkFav.get()
                    if(isFav){
                        favourites.text="Remove from favourites"
                        val fav=ContextCompat.getColor(applicationContext,R.color.darkblue)
                        favourites.setBackgroundColor(fav)
                    }
                    else{
                        favourites.text="Add to favourites"
                        val nofav=ContextCompat.getColor(applicationContext,R.color.purple_500)
                        favourites.setBackgroundColor(nofav)
                    }
                    favourites.setOnClickListener {


                        if (!DBAsynTask(applicationContext, bookEntity, 1).execute().get()) {
                            val async = DBAsynTask(applicationContext, bookEntity, 2).execute()
                            val result = async.get()
                            if (result) {
                                Toast.makeText(
                                    this,
                                    "Book is added to the favourites",
                                    Toast.LENGTH_SHORT
                                ).show()
                                favourites.text = "Remove from favourites"

                                val fav =
                                 ContextCompat.getColor(applicationContext, R.color.darkblue)
                                favourites.setBackgroundColor(fav)
                            } else {
                                Toast.makeText(
                                    this,
                                    "some error occured in insert",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val async = DBAsynTask(applicationContext, bookEntity, 3).execute()
                            val result = async.get()
                            if (result) {
                                Toast.makeText(
                                    this,
                                    "Book is removed from the favourites",
                                    Toast.LENGTH_SHORT
                                ).show()
                                favourites.text = "Add to favourites"
                                val nofav = ContextCompat.getColor(
                                  applicationContext,
                                R.color.purple_500
                                )
                                favourites.setBackgroundColor(nofav)
                            } else {
                                Toast.makeText(
                                    this,
                                    "some error occured in delete",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }

                }else{
                    Toast.makeText(this,"some thing went wrong",Toast.LENGTH_SHORT).show()
                }

            }catch (e:JSONException){
                Toast.makeText(this,"app crashed",Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
            Toast.makeText(this,"volley errorcrashed",Toast.LENGTH_SHORT).show()
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["Content-type"]="application/json"
                headers["token"]="43b1b8b7a83d74"
                return headers

            }
        }

        queue.add(jsonObjectRequest)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    class DBAsynTask(val context: Context,val bookEntity: BookEntity,val  mode:Int):AsyncTask<Void,Void,Boolean>(){
        val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode){
                1->{

                 val book:BookEntity?=db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return  book!=null
                }
                2->{
                    db.bookDao().insert(bookEntity)
                    db.close()
                    return true

                }
                3->{
                    db.bookDao().delete(bookEntity)
                    db.close()
                    return true

                }
            }
            return false
        }

    }
}