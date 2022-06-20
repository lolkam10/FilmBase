package com.example.filmbase.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbase.R

class FilmListHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    val poster : ImageView = itemView.findViewById(R.id.item_poster)
    val title : TextView = itemView.findViewById(R.id.item_title)
    val year : TextView = itemView.findViewById(R.id.item_year)

}