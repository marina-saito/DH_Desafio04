package com.example.desafio04.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafio04.R
import com.example.desafio04.domain.Game
import kotlinx.android.synthetic.main.item_recycler.view.*

class GamesAdapter(val listener: OnClickGameListener): RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    var listGames = listOf<Game>()

    inner class GameViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val ivRecycler: ImageView = view.ivRecycler
        val tvTitleRecycler: TextView = view.tvTitleRecycler
        val tvYearRecycler: TextView = view.tvYearRecycler

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onClickGame(position)
            }
        }
    }

    interface OnClickGameListener {
        fun onClickGame(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapter.GameViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapter.GameViewHolder, position: Int) {
        var game = listGames[position]
        Glide.with(holder.itemView).asBitmap()
                .load(game.url)
                .into(holder.ivRecycler)
        holder.tvTitleRecycler.text = game.name
        holder.tvYearRecycler.text = game.year.toString()
    }

    override fun getItemCount() = listGames.size

}