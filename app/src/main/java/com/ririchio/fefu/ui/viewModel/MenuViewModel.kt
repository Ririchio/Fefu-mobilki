package com.ririchio.fefu.ui.viewModel;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.data.storage.dao.DaoActive
import com.ririchio.fefu.data.storage.models.Active
import com.ririchio.fefu.ui.adapter.ActivityItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class MenuViewModel : ViewModel() {
    var selectedActivityItem = ActivityItem.Activity()


    private var database: MainDatabase? = null
    private var activeDao: DaoActive? = null
    private var list: List<Active>? = listOf()

    fun setDataBase(mainDatabase: MainDatabase) {
        database = mainDatabase
        activeDao = mainDatabase.activeDao()
    }

    fun selectActivityItem(activityItem: ActivityItem.Activity) {
        selectedActivityItem = activityItem
    }

    fun addActive(active: Active) {
        viewModelScope.launch {
            activeDao?.insert(active)
        }
    }

    fun getAllActive(callback: (List<Active>?) -> Unit) {
        viewModelScope.launch {
            list = activeDao?.getAll()?.flowOn(Dispatchers.IO)?.first()
            callback(list)
        }
    }

    fun deleteActive(active: ActivityItem.Activity) {
        viewModelScope.launch {
            val item = list?.first { it.id == active.id }
            if (item != null) activeDao?.delete(item)
        }
    }
}
