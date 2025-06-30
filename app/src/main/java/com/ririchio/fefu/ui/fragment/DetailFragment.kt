package com.ririchio.fefu.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.google.android.material.appbar.MaterialToolbar
import com.ririchio.fefu.R
import com.ririchio.fefu.ui.viewModel.MenuViewModel

class DetailFragment : Fragment() {

    var listener: OnBackClickListener? = null
    private var viewModel: MenuViewModel? = null

    interface OnBackClickListener {
        fun onBackClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnBackClickListener
            ?: throw ClassCastException("$context must implement OnBackClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val backBtn = view.findViewById<MaterialToolbar>(R.id.toolbar_login)
        val distText = view.findViewById<TextView>(R.id.dist)
        val timeText = view.findViewById<TextView>(R.id.time)
        val timeBackText = view.findViewById<TextView>(R.id.timeBack)
        val timeAgo = view.findViewById<TextView>(R.id.timeAgo)
        val trashIcon = view.findViewById<ImageView>(R.id.ic_trash)

        viewModel = ViewModelProvider(requireActivity())[MenuViewModel::class.java]

        val selectedItem = viewModel?.selectedActivityItem

        if (selectedItem?.tag?.isNotEmpty() == true) {
            trashIcon.visibility = GONE
        }

        backBtn.title = selectedItem?.type
        distText.text = selectedItem?.distance
        timeText.text = selectedItem?.duration
        timeBackText.text = selectedItem?.timeAgo
        timeAgo.text = "Старт: ${selectedItem?.start} | Финиш: ${selectedItem?.finish}"

        backBtn.setOnClickListener {
            listener?.onBackClicked()
        }
        return view
    }
}