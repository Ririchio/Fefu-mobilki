package com.ririchio.fefu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ririchio.fefu.R
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.data.storage.models.Active
import com.ririchio.fefu.ui.adapter.ActivitiesAdapter
import com.ririchio.fefu.ui.adapter.ActivityItem
import com.ririchio.fefu.ui.adapter.MapAdapter
import com.ririchio.fefu.ui.adapter.MapItem
import com.ririchio.fefu.ui.viewModel.MenuViewModel
import com.ririchio.fefu.utils.getRandomDistance
import com.ririchio.fefu.utils.getRandomTimes
import java.util.Calendar
import java.util.Date

class MapActivity : AppCompatActivity() {

    var list = listOf(MapItem("Велосипед", true), MapItem("Бег"), MapItem("Шаг"))
    private var viewModel: MenuViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(this))

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        setAdapter(recyclerView, list)

        val saveBtn = findViewById<Button>(R.id.save)
        saveBtn.setOnClickListener {
            val times = getRandomTimes()

            viewModel?.addActive(
                Active(
                    type = list.first { it.isSelect }.name,
                    distance = getRandomDistance(),
                    date_start = times[0],
                    date_end = times[1]
                )
            )

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter(recyclerView: RecyclerView, items: List<MapItem>) {
        val adapter = MapAdapter(items) {
            list = list.map { item ->
                MapItem(
                    item.name,
                    isSelect = item.name == it.name
                )
            }
            setAdapter(recyclerView, list)
        }
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }
}

