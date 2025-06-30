package com.ririchio.fefu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.ririchio.fefu.R
import com.ririchio.fefu.ui.adapter.ActivitiesAdapter
import com.ririchio.fefu.ui.adapter.ActivityItem

class ActivityFragment : Fragment() {

    val itemsMy = listOf(
        ActivityItem.Section("Вчера"),
        ActivityItem.Activity(
            "14.32 км",
            "2 часа 46 минут",
            "Серфинг \uD83C\uDFC4",
            "14 часов назад",
            start = "14:49",
            finish = "16:31"
        ),
        ActivityItem.Section("Май 2022 года"),
        ActivityItem.Activity(
            "1 000 м", "60 минут", "Велосипед 🚴", "28.05.2022",
            start = "14:00",
            finish = "16:00"
        )
    )

    val itemsOther = listOf(
        ActivityItem.Section("Вчера"),
        ActivityItem.Activity(
            "14.32 км",
            "2 часа 46 минут",
            "Серфинг \uD83C\uDFC4",
            "14 часов назад",
            "@van_darkholme"
        ),
        ActivityItem.Activity(
            "228 м",
            "14 часов 48 минут",
            "Качели",
            "14 часов назад",
            "@techniquepasha"
        ),
        ActivityItem.Activity(
            "10 км",
            "1 час 10 минут",
            "Езда на кадилак",
            "14 часов назад",
            "@morgen_shtren"
        ),
    )

    var listener: OnActivityItemClickListener? = null

    interface OnActivityItemClickListener {
        fun onActivityItemClicked(activityItem: ActivityItem.Activity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnActivityItemClickListener
            ?: throw ClassCastException("$context must implement OnActivityItemClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        var items = itemsMy

        setTab(tabLayout) {
            when (it) {
                0 -> {
                    items = itemsMy
                    setAdapter(recyclerView, items)
                }

                1 -> {
                    items = itemsOther
                    setAdapter(recyclerView, items)
                }
            }
        }
        setAdapter(recyclerView, items)

        return view
    }

    private fun setAdapter(recyclerView: RecyclerView, items: List<ActivityItem>) {
        val adapter = ActivitiesAdapter(items) { activityItem ->
            listener?.onActivityItemClicked(activityItem)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setTab(tabLayout: TabLayout, onChange: (Int) -> Unit) {
        tabLayout.addTab(tabLayout.newTab().setText("Мои"))
        tabLayout.addTab(tabLayout.newTab().setText("Пользователей"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { onChange(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}