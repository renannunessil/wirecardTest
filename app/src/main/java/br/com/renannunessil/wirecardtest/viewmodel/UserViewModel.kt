package br.com.renannunessil.wirecardtest.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import br.com.renannunessil.wirecardtest.data.model.User
import br.com.renannunessil.wirecardtest.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository = UserRepository.getInstance(application)

    fun getUser() : User? {
        return repository.getUser()
    }

    fun saveUser(user: User) {
        repository.saveUser(user)
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }
}