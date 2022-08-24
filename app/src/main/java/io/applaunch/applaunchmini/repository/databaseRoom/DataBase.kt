package io.applaunch.applaunchmini.repository.databaseRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.applaunch.applaunchmini.repository.databaseRoom.dao.MyDao
import io.applaunch.applaunchmini.repository.databaseRoom.entity.User

@Database(
    entities = [
         User::class
    ], version = 1
)
abstract class DataBase : RoomDatabase() {

    abstract val myDao: MyDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "my_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}