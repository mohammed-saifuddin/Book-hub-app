package com.example.bookhub.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.adapter.RecyclerAdapter
import com.example.bookhub.utils.Book
import com.example.bookhub.utils.ConnectionManager
import org.json.JSONException
import java.util.*


class Dash : Fragment() {

lateinit var recyclerView:RecyclerView
lateinit var layoutManager:RecyclerView.LayoutManager
lateinit var recyclerAdapter:RecyclerAdapter

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
val booklist= arrayListOf<String>()
    val books= arrayListOf<Book>(


    )
    var ratingComparator=Comparator<Book>{book1,book2->
      if(book1.bookRating.compareTo(book2.bookRating,true)==0){
          book1.bookName.compareTo(book2.bookName,true)
      }else {
          book1.bookRating.compareTo(book2.bookRating,true)
      }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dash, container, false)
        setHasOptionsMenu(true)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout=view.findViewById(R.id.progressLayout)

        progressLayout.visibility=View.VISIBLE
        recyclerView=view.findViewById(R.id.recyclerView)

        layoutManager=LinearLayoutManager(activity)

        val queue= Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {
                try {
                    progressLayout.visibility=View.GONE

                    val success = it.getBoolean("success")
                    if (success) {
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image"),

                                )
                            books.add(bookObject)
                            recyclerAdapter = RecyclerAdapter(activity as Context, books)
                            recyclerView.adapter = recyclerAdapter
                            recyclerView.layoutManager = layoutManager
                        }

                    } else {
                        Toast.makeText(context, "success failed", Toast.LENGTH_LONG).show()
                    }
                }catch (e:JSONException){
                    Toast.makeText(context, "some unexpected error occured", Toast.LENGTH_LONG).show()
                }


            }, Response.ErrorListener {



                    Toast.makeText(context, "volley error occured", Toast.LENGTH_LONG).show()

            }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "43b1b8b7a83d74"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setMessage("No Internet connection ")
            dialog.setTitle("Error")
            dialog.setPositiveButton("open setting"){text,listener->
                val setting= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(setting)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.rating, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==R.id.sort){
            Collections.sort(books,ratingComparator)
            books.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

}