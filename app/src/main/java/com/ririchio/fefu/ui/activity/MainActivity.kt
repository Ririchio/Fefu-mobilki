package com.ririchio.fefu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.ui.viewModel.LoginViewModel
import com.ririchio.fefu.utils.Const

class MainActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(this))
        viewModel?.isAuth(PrefManager(this).getString(Const.TOKEN) ?: "") {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }

        val regBtn = findViewById<Button>(R.id.buttonRegister)
        regBtn.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        val loginBtn = findViewById<TextView>(R.id.textLoginLink)
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}