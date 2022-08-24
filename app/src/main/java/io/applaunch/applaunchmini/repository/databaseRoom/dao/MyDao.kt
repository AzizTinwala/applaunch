package io.applaunch.applaunchmini.repository.databaseRoom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User

@Dao
interface MyDao {
    /** User Query Start */

    //Insert Category
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    //Get Product Sub Category from database
    @Transaction
    @Query("SELECT * FROM User")
    fun getUser(): LiveData<MutableList<User>>

    /** User Query End */


}