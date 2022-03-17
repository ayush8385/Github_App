package com.ayush.githubapp.database

import androidx.lifecycle.LiveData

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