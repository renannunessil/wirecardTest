package br.com.renannunessil.wirecardtest.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.renannunessil.wirecardtest.data.local.dao.UserDAO
import br.com.renannunessil.wirecardtest.data.model.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        private val DATABASE_NAME = "userDatabase"
        private lateinit var instance: AppDataBase

        fun getAppDatabase(context: Context): AppDataBase {
            synchronized(AppDataBase::class.java) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATABASE_NAME
                )
                    .build()
            }
            return instance
        }
    }
}