package com.example.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.Description
import com.example.bookhub.R
import com.example.bookhub.databases.BookEntity
import com.squareup.picasso.Picasso

class Favourites(val context: Context, val bookItem:List<BookEntity>):RecyclerView.Adapter<Favourites.FavouritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite,parent,false)
        return FavouritesViewHolder(view)
    }
    override fun getItemCount(): Int {
        return bookItem.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val book=bookItem[position]
        holder.bookName.text=book.bookName
        holder.bookAuthor.text=book.bookAuthor
        holder.bookPrice.text=book.bookPrice
        holder.bookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)
        holder.card.setOnClickListener {
           // val intent= Intent(context,Description::class.java)
            //intent.putExtra("book_id",book.book_id)
           // intent.putExtra("name",book.bookName)
            //context.startActivity(intent)
            Toast.makeText(context,"you have favourites ${holder.bookName.text}",Toast.LENGTH_SHORT).show()
        }
    }


    class FavouritesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val bookName:TextView=view.findViewById(R.id.favName1)
        val bookAuthor:TextView=view.findViewById(R.id.favauthorName)
        val bookPrice:TextView=view.findViewById(R.id.favprice)
        val bookRating:TextView=view.findViewById(R.id.favrating)
        val bookImage: ImageView =view.findViewById(R.id.favimage1)
        val card: LinearLayout =view.findViewById(R.id.card)
    }
}