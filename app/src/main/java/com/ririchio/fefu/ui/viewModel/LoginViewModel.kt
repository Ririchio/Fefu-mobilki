package com.ririchio.fefu.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ririchio.fefu.data.storage.MainDatabase
import com.ririchio.fefu.data.storage.dao.DaoUser
import com.ririchio.fefu.data.storage.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private var database: MainDatabase? = null
    private var userDao: DaoUser? = null
    var user: User? = User()

    fun setDataBase(mainDatabase: MainDatabase) {
        database = mainDatabase
        userDao = mainDatabase.userDao()
    }

    fun isAuth(token: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val loginUser = userDao?.getUser(token)?.flowOn(Dispatchers.IO)?.first()
            if (!loginUser.isNullOrEmpty()) {
                user = loginUser[0]
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    fun login(login: String, pass: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val loginUser = userDao?.login(login, pass)?.flowOn(Dispatchers.IO)?.first()
            user = loginUser
            callback(loginUser)
        }
    }

    fun reg(user: User) {
        this.user = user
        viewModelScope.launch {
            userDao?.insert(user)
        }
    }

    fun update(user: User) {
        this.user = user
        viewModelScope.launch {
            userDao?.update(user)
        }
    }
}