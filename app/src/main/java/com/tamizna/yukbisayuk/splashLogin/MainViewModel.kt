package com.tamizna.yukbisayuk.splashLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tamizna.yukbisayuk.utils.SharedPreferencesHelper

class MainViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val pageStart : LiveData<Page> =
        userRepository.checkLoggedIn().map {
            if(it) {
                Page.Home
            } else
                Page.Login
        }

}

sealed class Page {
    object Login : Page()
    object Home : Page()
}