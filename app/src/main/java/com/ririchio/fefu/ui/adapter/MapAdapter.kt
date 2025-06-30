package com.ririchio.fefu.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ririchio.fefu.R
import androidx.core.graphics.toColorInt

data class MapItem(
    val name: String = "",
    var isSelect: Boolean = false
)

class MapAdapter(private val items: List<MapItem>, private val onClick: (MapItem) -> Unit) : RecyclerView.Adapter<MapAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView = itemView.findViewById(R.id.item)
        val text: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.text.text = item.name
        holder.layout.setOnClickListener { onClick(item) }
        if (item.isSelect) {
            holder.layout.strokeColor = "#4B09F3".toColorInt()
        }
    }
}