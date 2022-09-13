package com.example.e_commerce.core.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {


    const val USERS="users"
    const val POSTS="Posts"
    const val COMMENTS="Comments"
    const val LIKES="Likes"
    const val CHATS="Chats"


    val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
    val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
    val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD")
    val USER_ID = stringPreferencesKey("USER_ID")

    const val USERS_INFO_FILE: String = "USER_INFO"


    const val BASE_URL="https://emarket3.herokuapp.com/api/"

    const val CONTENT_TYPE="application/json"


    const val EXCEPTION_IO = "Couldn't reach the server, please check your internet connection"
    const val EXCEPTION = "An unexpected error occurred"



}

typealias AppIds=com.example.e_commerce.R.id


