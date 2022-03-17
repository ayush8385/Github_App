package com.ayush.githubapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.githubapp.database.ReposEntity
import com.ayush.githubapp.database.ReposViewModel

class MainActivity : AppCompatActivity() {
    lateinit var addRepo:Button
    var listrepos= arrayListOf<ReposEntity>()
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var repoadapter:RepoAdapter
    lateinit var tracktext:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addRepo=findViewById(R.id.add_btn)
        tracktext=findViewById(R.id.track)

        addRepo.setOnClickListener {
            startActivity(Intent(this,AddRepository::class.java))
        }

        recyclerView = findViewById(R.id.repo_reccyler)

        layoutManager = LinearLayoutManager(this)


//        val url="https://api.github.com/repos/JetBrains/kotlin"
//        val queue= Volley.newRequestQueue(this)
//
//        val jsonObjectRequest=object : JsonObjectRequest(Method.GET,url,null, Response.Listener {
//            try{
//                val repoobj = ReposEntity(it.getString("id"),"JetBrains",it.getString("name"),it.getString("description"),it.getString("html_url"),it.getInt("open_issues"))
//
//                listrepos.add(repoobj)
//
//                if(listrepos.isEmpty()){
//                    addRepo.visibility=View.VISIBLE
//                    tracktext.visibility=View.VISIBLE
//                }
//                else{
//                    addRepo.visibility=View.GONE
//                    tracktext.visibility=View.GONE
//                }
//
//                ReposViewModel(application).insertRepo(repoobj)
//
//
//
//            }
//            catch (e:Exception){
//                Toast.makeText(applicationContext,"Error Occurred", Toast.LENGTH_SHORT).show()
//            }
//        }, Response.ErrorListener {
//            Toast.makeText(this, "Volley error occurred!!!", Toast.LENGTH_SHORT).show()
//        }){}
//        queue.add(jsonObjectRequest)


        ReposViewModel(application).allRepos.observe(this, Observer { list->
            list?.let {

                listrepos.clear()
                listrepos.addAll(list)

                if(listrepos.isEmpty()){
                    addRepo.visibility=View.VISIBLE
                    tracktext.visibility=View.VISIBLE
                }
                else{
                    addRepo.visibility=View.GONE
                    tracktext.visibility=View.GONE
                }

                repoadapter= RepoAdapter(this,listrepos)
                recyclerView.adapter=repoadapter
                recyclerView.layoutManager=layoutManager
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.add_repo){
            startActivity(Intent(this,AddRepository::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}