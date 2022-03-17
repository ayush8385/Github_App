package com.ayush.githubapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class ReposEntity(

    @PrimaryKey @ColumnInfo(name = "repo_id") val repoId: String,
    @ColumnInfo(name = "repo_owner") val repoOwner:String,
    @ColumnInfo(name = "repo_name") val repoName : String,
    @ColumnInfo(name = "repo_desc") val repoDesc: String,
    @ColumnInfo(name = "repo_url") val repoUrl:String,
    @ColumnInfo(name = "open_issues") val repoIssues:Int

)
