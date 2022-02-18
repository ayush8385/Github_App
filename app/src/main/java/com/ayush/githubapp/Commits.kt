package com.ayush.githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ayush.githubapp.Model.Branches
import com.ayush.githubapp.Model.Commit
import com.ayush.githubapp.Model.Repos
import com.ayush.hungreed.database.ReposEntity
import com.digitalhain.daipsisearch.Activities.Room.ReposViewModel

class Commits : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var commitAdapter:CommitAdapter
    lateinit var repoDetail:ReposEntity

    var commitlist = arrayListOf<Commit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)

        recyclerView=findViewById(R.id.commit_recycler)
        layoutManager=LinearLayoutManager(this)

        val branchName = intent.getStringExtra("branch_name")
        supportActionBar!!.title="Commits"
        supportActionBar!!.subtitle=branchName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

        repoDetail = ReposViewModel(application).getReposById(intent.getStringExtra("id")!!)

        val url="https://api.github.com/repos/${repoDetail.repoOwner}/${repoDetail.repoName}/commits?sha=${branchName}"
        val queue= Volley.newRequestQueue(this)

        val jsonObjectRequest=object : JsonArrayRequest(Method.GET,url,null, Response.Listener {
            try{
                for(i in 0 until it.length()){
                    val data = it.getJSONObject(i)
                    val detail = data.getJSONObject("commit")
                    val user = data.getJSONObject("author")

                    commitlist.add(Commit(detail.getString("message"),user.getString("login"),user.getString("avatar_url"),data.getString("sha"),detail.getJSONObject("author").getString("date")))
                }


                commitAdapter=CommitAdapter(this,commitlist)
                recyclerView.adapter=commitAdapter
                recyclerView.layoutManager=layoutManager

            }
            catch (e:Exception){
                Toast.makeText(applicationContext,"Error Occurred", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
            Toast.makeText(this, "Volley error occurred!!!", Toast.LENGTH_SHORT).show()
        }){}
        queue.add(jsonObjectRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}