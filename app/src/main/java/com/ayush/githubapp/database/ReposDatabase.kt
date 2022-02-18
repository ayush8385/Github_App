package com.ayush.hungreed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReposEntity::class], version=1)
abstract class ReposDatabase: RoomDatabase() {
    abstract fun repoDao():ReposDao

    companion object{
        @Volatile
        private var INSTANCE: ReposDatabase?=null

        fun getDatabase(context: Context): ReposDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance=
                    Room.databaseBuilder(context.applicationContext, ReposDatabase::class.java,"repos-db").allowMainThreadQueries().build()
                INSTANCE =instance
                instance
            }
        }
    }
}