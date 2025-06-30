package com.ririchio.fefu.ui.viewModel;

import androidx.lifecycle.ViewModel
import com.ririchio.fefu.ui.adapter.ActivityItem


class MenuViewModel : ViewModel() {
    var selectedActivityItem = ActivityItem.Activity()

    fun selectActivityItem(activityItem: ActivityItem.Activity) {
        selectedActivityItem = activityItem
    }
}
