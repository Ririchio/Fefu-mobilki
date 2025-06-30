package com.ririchio.fefu.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.ui.adapter.ActivityItem
import com.ririchio.fefu.ui.viewModel.LoginViewModel
import com.ririchio.fefu.utils.Const

class ChangePassFragment : Fragment() {

    interface OnSaveClickListener {
        fun onBackClickListener()
        fun onSaveClickListener()
    }

    var listener: OnSaveClickListener? = null
    private var viewModel: LoginViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnSaveClickListener
            ?: throw ClassCastException("$context must implement onSaveClickListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_pass, container, false)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(requireActivity()))
        viewModel?.isAuth(PrefManager(requireContext()).getString(Const.TOKEN) ?: "") {}

        val user = viewModel?.user
        val oldPass = view.findViewById<TextInputEditText>(R.id.oldPass)
        val pass = view.findViewById<TextInputEditText>(R.id.newPass)
        val repPass = view.findViewById<TextInputEditText>(R.id.newPassRep)

        val backBtn = view.findViewById<MaterialToolbar>(R.id.toolbar_login)
        val btn = view.findViewById<Button>(R.id.changePassBtn)

        btn.setOnClickListener{
            if (oldPass.text.toString() != user?.pass) {
                Toast.makeText(requireContext(), "Неправильный старый пароль", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.text.toString() != repPass.text.toString()) {
                Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel?.let {
                it.update(
                    it.user!!.copy(
                        pass = pass.text.toString(),
                    )
                )
            }
            Toast.makeText(requireContext(), "Сохранено!", Toast.LENGTH_LONG).show()
            listener?.onBackClickListener()
        }
        backBtn.setOnClickListener{
            listener?.onSaveClickListener()
        }

        return view
    }
}