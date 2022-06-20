package com.example.filmbase.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbase.R
import com.example.filmbase.api.dataClasses.FilmShort
import com.example.filmbase.viewModels.MyViewModel
import com.squareup.picasso.Picasso

class FilmSearchListAdapter(private val films: LiveData<List<FilmShort>>)
    : RecyclerView.Adapter<FilmListHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item,parent,false)
        return FilmListHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListHolder, position: Int) {
        Picasso.get().load(films.value?.get(position)?.Poster).into(holder.poster)
        holder.title.text = films.value?.get(position)?.Title
        holder.year.text = films.value?.get(position)?.Year.toString()
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("filmID", films.value?.get(position)?.imdbID)
            it.findNavController().navigate(R.id.action_searchFragment_to_searchDetailsFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return films.value?.size?:0
    }
}