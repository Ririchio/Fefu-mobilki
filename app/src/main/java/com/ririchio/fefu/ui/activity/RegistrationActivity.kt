package com.ririchio.fefu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.ririchio.fefu.R
import com.ririchio.fefu.data.PrefManager
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.data.storage.models.User
import com.ririchio.fefu.ui.viewModel.LoginViewModel
import com.ririchio.fefu.utils.Const
import java.util.UUID

class RegistrationActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel?.setDataBase(MainDatabase.getInstance(this))

        val regBtn = findViewById<Button>(R.id.buttonSubmitRegister)
        val login = findViewById<TextInputEditText>(R.id.login)
        val pass = findViewById<TextInputEditText>(R.id.pass)
        val repPass = findViewById<TextInputEditText>(R.id.repPass)
        val name = findViewById<TextInputEditText>(R.id.name)

        setColor()

        regBtn.setOnClickListener {
            if (login.text!!.isEmpty()) {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (name.text!!.isEmpty()) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.text!!.isEmpty()) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (repPass.text.toString() != pass.text.toString()) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val id = UUID.randomUUID().toString()
            viewModel?.reg(User(
                id = id,
                login = login.text.toString(),
                name = name.text.toString(),
                pass = pass.text.toString()
            ))
            PrefManager(this).setString(Const.TOKEN, id)

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val backBtn = findViewById<MaterialToolbar>(R.id.toolbar_login)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setColor() {
        val textView = findViewById<TextView>(R.id.textPrivacy)
        val fullText =
            "Нажимая на кнопку, вы соглашаетесь с политикой конфиденциальности и пользовательским соглашением"

        val spannable = SpannableString(fullText)

        val policyStart = fullText.indexOf("политикой конфиденциальности")
        val policyEnd = policyStart + "политикой конфиденциальности".length

        val agreementStart = fullText.indexOf("пользовательским соглашением")
        val agreementEnd = agreementStart + "пользовательским соглашением".length

        val color = ContextCompat.getColor(this, R.color.purple) // или другой
        spannable.setSpan(
            ForegroundColorSpan(color),
            policyStart,
            policyEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(color),
            agreementStart,
            agreementEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable
    }
}