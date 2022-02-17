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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ayush.githubapp.Model.Repos
import org.json.JSONArray
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    lateinit var addRepo:Button
    var allrepos= arrayListOf<Repos>()
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


        val url="https://api.github.com/repos/ayush8385/Flow"
        val queue= Volley.newRequestQueue(this)

        val jsonObjectRequest=object : JsonObjectRequest(Method.GET,url,null, Response.Listener {
            try{
                val repoobj = Repos(it.getString("name"),it.getString("description"))

                allrepos.add(repoobj)

                if(allrepos.isEmpty()){
                    addRepo.visibility=View.VISIBLE
                    tracktext.visibility=View.VISIBLE
                }
                else{
                    addRepo.visibility=View.GONE
                    tracktext.visibility=View.GONE
                }

                repoadapter= RepoAdapter(this,allrepos)
                recyclerView.adapter=repoadapter
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