package com.ririchio.fefu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ririchio.fefu.R

sealed class ActivityItem {
    data class Section(val title: String = "") : ActivityItem()
    data class Activity(
        val id: String = "",
        val distance: String = "",
        val duration: String = "",
        val type: String = "",
        val timeAgo: String = "",
        val tag: String = "",
        val start: String = "",
        val finish: String = "",
    ) : ActivityItem()
}

class ActivitiesAdapter(
    private val items: List<ActivityItem>,
    private val onClick: (ActivityItem.Activity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SECTION = 0
        private const val TYPE_ACTIVITY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityItem.Section -> TYPE_SECTION
            is ActivityItem.Activity -> TYPE_ACTIVITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SECTION) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_section, parent, false)
            SectionViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_activity, parent, false)
            ActivityViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityItem.Section -> (holder as SectionViewHolder).bind(item)
            is ActivityItem.Activity -> (holder as ActivityViewHolder).bind(item, onClick)
        }
    }

    override fun getItemCount(): Int = items.size

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.textSection)
        fun bind(item: ActivityItem.Section) {
            title.text = item.title
        }
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val distance: TextView = view.findViewById(R.id.textDistance)
        private val duration: TextView = view.findViewById(R.id.textDuration)
        private val type: TextView = view.findViewById(R.id.textType)
        private val timeAgo: TextView = view.findViewById(R.id.textTimeAgo)
        private val tag: TextView = view.findViewById(R.id.textTag)

        fun bind(item: ActivityItem.Activity, onClick: (ActivityItem.Activity) -> Unit) {
            distance.text = item.distance
            duration.text = item.duration
            type.text = item.type
            timeAgo.text = item.timeAgo
            tag.text = item.tag

            itemView.setOnClickListener {
                onClick(item)
            }
        }
    }
}