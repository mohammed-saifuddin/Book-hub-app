package com.example.bookhub.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub.Description
import com.example.bookhub.R
import com.example.bookhub.adapter.Favourites



import com.example.bookhub.databases.BookDatabase
import com.example.bookhub.databases.BookEntity



class Favourite : Fragment() {
    private lateinit var recyclerFavourite: RecyclerView

    private lateinit var layoutManager: RecyclerView.LayoutManager

    private var dbBookList = listOf<BookEntity>()


   lateinit var  recyclerAdapter: Favourites
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var list:RelativeLayout
    lateinit var secondary:RelativeLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view= inflater.inflate(R.layout.fragment_favourite, container, false)
        recyclerFavourite=view.findViewById(R.id.recyclerFavourite)
        secondary=view.findViewById(R.id.secondary)

        list=view.findViewById(R.id.list)

        progressBar=view.findViewById(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        progressLayout=view.findViewById(R.id.progressLayout)
        progressLayout.visibility=View.VISIBLE
        layoutManager= GridLayoutManager(activity as Context,2)
        dbBookList=RetrieveFavourites(activity as Context).execute().get()
          if(dbBookList.isEmpty()){
              secondary.visibility=View.VISIBLE
              progressLayout.visibility=View.GONE
              list.visibility=View.GONE
          }else{

                  secondary.visibility=View.GONE
                  list.visibility=View.VISIBLE
              progressLayout.visibility=View.GONE



                  recyclerAdapter=Favourites(activity as Context,dbBookList)

                  recyclerFavourite.adapter=recyclerAdapter
                  recyclerFavourite.layoutManager=layoutManager


          }
        return view
    }

   class RetrieveFavourites(val context:Context):AsyncTask<Void,Void,List<BookEntity>>(){
       private val db=Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
       override fun doInBackground(vararg params: Void?): List<BookEntity> {
            var res=db.bookDao().getAllBooks()
           return res

       }

   }
}