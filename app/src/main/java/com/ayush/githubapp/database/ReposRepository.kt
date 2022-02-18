package com.digitalhain.daipsisearch.Activities.Room

import androidx.lifecycle.LiveData
import com.ayush.hungreed.database.ReposDao
import com.ayush.hungreed.database.ReposEntity

class ReposRepository(private val repoDao: ReposDao) {
    val allRepos: LiveData<List<ReposEntity>> = repoDao.getAllRepos()

    suspend fun insert(repoEntity: ReposEntity){
       repoDao.insertRepo(repoEntity)
    }

    suspend fun delete(repoEntity: ReposEntity){
        repoDao.deleteRepo(repoEntity)
    }

    fun getRepoById(id:String):ReposEntity{
        return repoDao.getRepobyId(id)
    }


//    suspend fun delete(noteEntity: NoteEntity){
//        noteDao.delete(noteEntity)
//    }
}