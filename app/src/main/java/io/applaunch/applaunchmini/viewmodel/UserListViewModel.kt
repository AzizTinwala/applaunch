package io.applaunch.applaunchmini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.applaunch.applaunchmini.repository.databaseRoom.DataBase
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User
import kotlinx.coroutines.launch

class UserListViewModel(application: Application) : AndroidViewModel(application) {
    var user: LiveData<MutableList<User>> = MutableLiveData()

    init {
        viewModelScope.launch {
            getUsers()
        }
    }


    private fun getUsers() {

        user =
            DataBase.getInstance(getApplication<Application>().applicationContext).myDao.getUser()
    }

     suspend fun insertUser(user: User) {

        DataBase.getInstance(getApplication<Application>().applicationContext).myDao.insertUser(user)
    }


}