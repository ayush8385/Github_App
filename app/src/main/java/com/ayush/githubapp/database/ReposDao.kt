package com.ayush.githubapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ReposDao {
    @Insert(onConflict = REPLACE)
    fun insertRepo(repsEntity: ReposEntity)

    @Delete
    fun deleteRepo(repoEntity: ReposEntity)

    @Query("SELECT * FROM repos")
    fun getAllRepos():LiveData<List<ReposEntity>>

    @Query("SELECT * FROM repos where repo_id=:repoId")
    fun getRepobyId(repoId:String):ReposEntity
}