package com.ririchio.fefu.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.ui.activity.MainActivity
import com.ririchio.fefu.ui.activity.MenuActivity
import com.ririchio.fefu.ui.adapter.ActivityItem
import com.ririchio.fefu.ui.fragment.ActivityFragment.OnActivityItemClickListener
import com.ririchio.fefu.ui.viewModel.LoginViewModel
import com.ririchio.fefu.utils.Const

class ProfileFragment : Fragment() {

    interface OnChangePassClickListener {
        fun onChangePassClickListener()
    }

    var listener: OnChangePassClickListener? = null
    private var viewModel: LoginViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnChangePassClickListener
            ?: throw ClassCastException("$context must implement OnChangePassClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(requireActivity()))

        val login = view.findViewById<TextView>(R.id.login)
        val name = view.findViewById<TextView>(R.id.name)
        val save = view.findViewById<TextView>(R.id.save)
        val logout = view.findViewById<Button>(R.id.logout)

        viewModel?.isAuth(PrefManager(requireContext()).getString(Const.TOKEN) ?: "") {
            login.text = viewModel?.user?.login
            name.text = viewModel?.user?.name
        }

        val changePass = view.findViewById<TextView>(R.id.changePass)

        changePass.setOnClickListener{
            listener?.onChangePassClickListener()
        }
        save.setOnClickListener{
            viewModel?.let {
                it.update(
                    it.user!!.copy(
                        login = login.text.toString(),
                        name = name.text.toString()
                    )
                )
            }
            Toast.makeText(requireContext(), "Сохранено!", Toast.LENGTH_LONG).show()
        }
        logout.setOnClickListener{
            PrefManager(requireContext()).removeKey(Const.TOKEN)
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}