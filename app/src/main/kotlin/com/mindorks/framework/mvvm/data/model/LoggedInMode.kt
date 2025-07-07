package com.mindorks.framework.mvvm.data.model

enum class LoggedInMode(val type: Int) {
    LOGGED_OUT(0),
    GOOGLE(1),
    FACEBOOK(2),
    SERVER(3)
}