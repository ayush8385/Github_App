package com.ayush.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ayush.githubapp.database.ReposEntity
import com.ayush.githubapp.database.ReposViewModel


class AddRepository : AppCompatActivity() {
    lateinit var ownername:EditText
    lateinit var reponame:EditText
    lateinit var addbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repository)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

        ownername=findViewById(R.id.owner)
        reponame=findViewById(R.id.repo)
        addbtn=findViewById(R.id.addrep)

        addbtn.setOnClickListener {
            val url="https://api.github.com/repos/${ownername.text}/${reponame.text}"
            val queue= Volley.newRequestQueue(this)

            val jsonObjectRequest=object : JsonObjectRequest(Method.GET,url,null, Response.Listener {
                try{
                    ReposViewModel(application).insertRepo(ReposEntity(it.getString("id"),ownername.text.toString(),it.getString("name"),it.getString("description"),it.getString("html_url"),it.getInt("open_issues")))
                    finish()
                }
                catch (e:Exception){
                    Toast.makeText(applicationContext,"Repository doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                Toast.makeText(applicationContext,"Repository doesn't exist", Toast.LENGTH_SHORT).show()
            }){}
            queue.add(jsonObjectRequest)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}