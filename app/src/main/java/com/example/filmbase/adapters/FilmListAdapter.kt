package com.example.filmbase.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.filmbase.R
import com.example.filmbase.firebase.classes.Film
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso

class FilmListAdapter(options : FirebaseRecyclerOptions<Film>) : FirebaseRecyclerAdapter<Film, FilmListHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListHolder {
        return FilmListHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.film_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: FilmListHolder, position: Int, model: Film) {
        Picasso.get().load(model.poster).into(holder.poster)
        holder.title.text = model.title
        holder.year.text = model.year.toString()
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("filmID", model.id)
            it.findNavController().navigate(R.id.action_libraryFragment_to_detailsFragment,bundle)
        }
    }
}