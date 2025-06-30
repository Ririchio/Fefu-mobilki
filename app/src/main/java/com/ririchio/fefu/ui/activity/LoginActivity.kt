package com.ririchio.fefu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.ui.viewModel.LoginViewModel
import com.ririchio.fefu.ui.viewModel.MenuViewModel
import com.ririchio.fefu.utils.Const

class LoginActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(this))

        val login = findViewById<TextInputEditText>(R.id.login)
        val pass = findViewById<TextInputEditText>(R.id.pass)

        val backBtn = findViewById<MaterialToolbar>(R.id.toolbar_login)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.buttonLogin)
        loginBtn.setOnClickListener {
            viewModel?.login(login.text.toString(), pass.text.toString()) {
                if (it == null) Toast.makeText(this, "Не правильный логин или пароль", Toast.LENGTH_LONG).show()
                else {
                    PrefManager(this).setString(Const.TOKEN, it.id)
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}