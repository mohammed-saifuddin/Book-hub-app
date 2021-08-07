package com.example.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.Description
import com.example.bookhub.R
import com.example.bookhub.utils.Book
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.util.zip.Inflater

class RecyclerAdapter(val context:Context,val itemList:ArrayList<Book>):RecyclerView.Adapter<RecyclerAdapter.DashboardAdapter>(){


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardAdapter, position: Int) {
         val book=itemList[position]

        holder.bookName.text=book.bookName
        holder.bookAuthor.text=book.bookAuthor
        holder.bookPrice.text=book.bookPrice
        holder.bookRating.text=book.bookRating
        //holder.bookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)
        holder.card.setOnClickListener {

                val intent= Intent(context,Description::class.java)
                intent.putExtra("book_id",book.book_id)
            intent.putExtra("name",book.bookName)
                context.startActivity(intent)
                //Toast.makeText(context,"you have clicked on ${holder.bookName.text}",Toast.LENGTH_LONG).show()


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard,parent,false)
        return DashboardAdapter(view)
    }
    class DashboardAdapter(view: View):RecyclerView.ViewHolder(view){
        val bookName:TextView=view.findViewById(R.id.name1)
        val bookAuthor:TextView=view.findViewById(R.id.authorName)
        val bookPrice:TextView=view.findViewById(R.id.price)
        val bookRating:TextView=view.findViewById(R.id.rating)
        val bookImage:ImageView=view.findViewById(R.id.image1)
        val card:LinearLayout=view.findViewById(R.id.card)

    }
}