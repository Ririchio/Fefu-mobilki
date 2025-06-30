package com.ririchio.fefu.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ririchio.fefu.ui.viewModel.MenuViewModel
import com.ririchio.fefu.R
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.ui.adapter.ActivityItem
import com.ririchio.fefu.ui.fragment.ActivityFragment
import com.ririchio.fefu.ui.fragment.ChangePassFragment
import com.ririchio.fefu.ui.fragment.DetailFragment
import com.ririchio.fefu.ui.fragment.ProfileFragment

class MenuActivity : AppCompatActivity(), ActivityFragment.OnActivityItemClickListener,
    DetailFragment.OnBackClickListener, ChangePassFragment.OnSaveClickListener,
    ProfileFragment.OnChangePassClickListener {

    private var viewModel: MenuViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(this))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    showFragment(ActivityFragment())
                    true
                }

                R.id.nav_profile -> {
                    showFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        showFragment(ActivityFragment())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onActivityItemClicked(activityItem: ActivityItem.Activity) {
        viewModel?.selectActivityItem(activityItem)
        showFragment(DetailFragment())
    }

    override fun onBackClicked() {
        showFragment(ActivityFragment())
    }

    override fun onBackClickListener() {
        showFragment(ProfileFragment())
    }

    override fun onSaveClickListener() {
        showFragment(ActivityFragment())
    }

    override fun onChangePassClickListener() {
        showFragment(ChangePassFragment())
    }
}