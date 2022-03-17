package com.ayush.githubapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReposViewModel(application: Application): AndroidViewModel(application) {
    val allRepos: LiveData<List<ReposEntity>>
    val repository: ReposRepository

    init {
        val database= ReposDatabase.getDatabase(application).repoDao()
        repository= ReposRepository(database)
        allRepos=repository.allRepos
    }


    fun insertRepo(repoEntity: ReposEntity) =viewModelScope.launch (Dispatchers.IO){
        repository.insert(repoEntity)
    }

    fun deleteRepo(repoEntity: ReposEntity)=viewModelScope.launch (Dispatchers.IO){
        repository.delete(repoEntity)
    }

    fun getReposById(id:String):ReposEntity{
        return repository.getRepoById(id)
    }
}