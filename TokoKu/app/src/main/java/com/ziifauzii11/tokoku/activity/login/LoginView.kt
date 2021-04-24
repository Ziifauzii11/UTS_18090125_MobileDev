package com.ziifauzii11.tokoku.activity.login

import com.ziifauzii11.tokoku.model.User

interface LoginView {
    fun onSuccessLogin(user: User?)
    fun onErrorLogin(msg: String?)
}