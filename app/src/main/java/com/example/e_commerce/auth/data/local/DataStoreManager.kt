package com.example.e_commerce.auth.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.core.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(Constants.USERS_INFO_FILE)

class DataStoreManager(appContext: Context) {

    private val tokenDataStore = appContext.dataStore
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun setUserInfo(user: UserInfoDB) {
        tokenDataStore.edit {preferences->
                preferences[Constants.USER_EMAIL] = user.email
                preferences[Constants.USER_PASSWORD] = user.password
                preferences[Constants.USER_ID] = user.id
                preferences[Constants.USER_TOKEN] = user.token
        }
    }

    val infoUser: Flow<UserInfoDB> = tokenDataStore.data.map { preferences ->
        UserInfoDB(
            preferences[Constants.USER_EMAIL] ?: "",
            preferences[Constants.USER_PASSWORD] ?: "",
            preferences[Constants.USER_TOKEN] ?: "",
            preferences[Constants.USER_ID]?: "",
            )
    }












}