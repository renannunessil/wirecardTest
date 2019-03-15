package br.com.renannunessil.wirecardtest.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import br.com.renannunessil.wirecardtest.data.model.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    fun getUser(): User?
}