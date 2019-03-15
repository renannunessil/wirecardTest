package br.com.renannunessil.wirecardtest.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import br.com.renannunessil.wirecardtest.data.local.AppDataBase
import br.com.renannunessil.wirecardtest.data.local.dao.UserDAO
import br.com.renannunessil.wirecardtest.data.model.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(private val userDAO: UserDAO, private val executorService: ExecutorService) {
    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(application: Application): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(
                    AppDataBase.getAppDatabase(application).userDao(),
                    Executors.newSingleThreadExecutor()
                ).also { INSTANCE = it }
            }

    }

    fun saveUser(user: User) {
        executorService.execute { userDAO.saveUser(user) }
    }

    fun deleteUser(user: User) {
        executorService.execute { userDAO.deleteUser(user) }
    }

    fun getUser(): User? {
        return executorService.submit(userDAO::getUser).get()
    }
}