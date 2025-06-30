package com.ririchio.fefu.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.ui.activity.MainActivity
import com.ririchio.fefu.ui.activity.MapActivity
import com.ririchio.fefu.ui.adapter.ActivitiesAdapter
import com.ririchio.fefu.ui.adapter.ActivityItem
import com.ririchio.fefu.ui.viewModel.MenuViewModel
import com.ririchio.fefu.utils.Const
import com.ririchio.fefu.utils.getHourWord
import com.ririchio.fefu.utils.getMinuteWord
import com.ririchio.fefu.utils.isSameDay
import com.ririchio.fefu.utils.toString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityFragment : Fragment() {
    val itemsOther = listOf(
        ActivityItem.Section("Вчера"),
        ActivityItem.Activity(
            distance = "14.32 км",
            duration = "2 часа 46 минут",
            type = "Серфинг \uD83C\uDFC4",
            timeAgo = "14 часов назад",
            tag = "@van_darkholme",
            start = "14:49",
            finish = "16:31"
        ),
        ActivityItem.Activity(
            distance = "228 м",
            duration = "14 часов 48 минут",
            type = "Качели",
            timeAgo = "14 часов назад",
            tag = "@techniquepasha",
            start = "10:50",
            finish = "02:32"
        ),
        ActivityItem.Activity(
            distance = "10 км",
            duration = "1 час 10 минут",
            type = "Езда на кадилак",
            timeAgo = "14 часов назад",
            tag = "@morgen_shtren",
            start = "12:30",
            finish = "13:30"
        ),
    )

    var listener: OnActivityItemClickListener? = null
    private var viewModel: MenuViewModel? = null

    var list: MutableList<ActivityItem> = mutableListOf()

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

        viewModel = ViewModelProvider(requireActivity())[MenuViewModel::class.java]

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val playBtn = view.findViewById<ImageView>(R.id.play)
        val emptyText = view.findViewById<LinearLayout>(R.id.emptyText)

        viewModel?.getAllActive {

            if (it?.isNotEmpty() == true) emptyText.visibility = View.GONE
            else recyclerView.visibility = View.GONE

            val now = System.currentTimeMillis()

            val groupedItems = it.orEmpty()
                .sortedByDescending { item -> item.date_start }
                .groupBy { item ->
                    val startDate = Date(item.date_start)
                    when {
                        isSameDay(startDate, Date(now)) -> "Сегодня"
                        isSameDay(startDate, Date(now - 86_400_000)) -> "Вчера"
                        else -> SimpleDateFormat("dd MMMM yyyy", Locale("ru")).format(startDate)
                    }
                }

            groupedItems.forEach { (sectionTitle, group) ->
                list.add(ActivityItem.Section(sectionTitle))

                group.forEach { item ->
                    val durationInMillis = item.date_end - item.date_start
                    val totalMinutes = durationInMillis / 1000 / 60
                    val hours = totalMinutes / 60
                    val minutes = totalMinutes % 60

                    val diffMillis = now - item.date_end
                    val diffHours = diffMillis / (1000 * 60 * 60)

                    list.add(
                        ActivityItem.Activity(
                            id = item.id,
                            distance = item.distance,
                            duration =
                                if (hours <= 0) "$minutes ${getMinuteWord(minutes.toInt())}"
                                else if (minutes <= 0) "$hours ${getHourWord(hours.toInt())}"
                                else "$hours ${getHourWord(hours.toInt())} $minutes ${getMinuteWord(minutes.toInt())}",
                            type = item.type,
                            timeAgo = "$diffHours ${getHourWord(diffHours.toInt())} назад",
                            start = Date(item.date_start).toString("HH:mm"),
                            finish = Date(item.date_end).toString("HH:mm"),
                        )
                    )
                }
            }

            setAdapter(recyclerView, list)
        }

        playBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            startActivity(intent)
        }

        setTab(tabLayout) {
            when (it) {
                0 -> {
                    setAdapter(recyclerView, list)
                }

                1 -> {
                    emptyText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    setAdapter(recyclerView, itemsOther)
                }
            }
        }

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