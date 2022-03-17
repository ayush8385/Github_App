package com.ayush.githubapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ayush.githubapp.Model.Branches
import com.ayush.githubapp.Model.Issues
import com.ayush.githubapp.database.ReposEntity
import com.ayush.githubapp.database.ReposViewModel

class Details : AppCompatActivity() {
    lateinit var repoName:TextView
    lateinit var repoDesc:TextView
    lateinit var repoDetail: ReposEntity

    lateinit var getBranch:Button
    lateinit var issues:Button
    lateinit var recyclerView: RecyclerView
    lateinit var branchAdapter:BranchAdapter
    lateinit var issueAdapter :IssueAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    var branchlist = arrayListOf<Branches>()
    var issuelist = arrayListOf<Issues>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        repoName=findViewById(R.id.reponame)
        repoDesc=findViewById(R.id.repodesc)
        recyclerView=findViewById(R.id.branch_recyler)
        //issuerecyclerView=findViewById(R.id.issue_recyler)
        layoutManager = LinearLayoutManager(this)

        getBranch=findViewById(R.id.branches)
        issues=findViewById(R.id.issues)

        supportActionBar!!.title="Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

        repoDetail = ReposViewModel(application).getReposById(intent.getStringExtra("id")!!)

        repoName.text=repoDetail.repoName
        repoDesc.text=repoDetail.repoDesc

        recyclerView.layoutManager=layoutManager

        issues.text="Issues(${repoDetail.repoIssues})"


        getBranch.setOnClickListener {
            branchAdapter= BranchAdapter(this)
            recyclerView.adapter=branchAdapter
            branchlist.clear()
            branchAdapter.updateBranch(branchlist)

            issues.setBackgroundResource(R.drawable.addbtn_back)
            getBranch.setBackgroundResource(R.drawable.rembtn_back)

            getBranch.setTextColor(ContextCompat.getColor(this,R.color.white))
            issues.setTextColor(ContextCompat.getColor(this,R.color.black))


            val url="https://api.github.com/repos/${repoDetail.repoOwner}/${repoDetail.repoName}/branches"
            val queue= Volley.newRequestQueue(this)

            val jsonObjectRequest=object : JsonArrayRequest(Method.GET,url,null, Response.Listener {
                try{
                    for(i in 0 until it.length()){
                        val data = it.getJSONObject(i)
                        val detail = data.getJSONObject("commit")

                        branchlist.add(Branches(data.getString("name"),detail.getString("sha"),detail.getString("url"),repoDetail.repoId))
                    }

                    branchAdapter.updateBranch(branchlist)

                }
                catch (e:Exception){
                    Toast.makeText(applicationContext,"Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                Toast.makeText(this, "Volley error occurred!!!", Toast.LENGTH_SHORT).show()
            }){}
            queue.add(jsonObjectRequest)
        }

        issues.setOnClickListener {
            issueAdapter= IssueAdapter(this)
            recyclerView.adapter=issueAdapter
            issuelist.clear()
            issueAdapter.updateIssue(issuelist)


            getBranch.setTextColor(ContextCompat.getColor(this,R.color.black))
            issues.setTextColor(ContextCompat.getColor(this,R.color.white))

            getBranch.setBackgroundResource(R.drawable.addbtn_back)
            issues.setBackgroundResource(R.drawable.rembtn_back)



            val url="https://api.github.com/repos/${repoDetail.repoOwner}/${repoDetail.repoName}/issues?state=open"
            val queue= Volley.newRequestQueue(this)

            val jsonObjectRequest=object : JsonArrayRequest(Method.GET,url,null, Response.Listener {
                try{
                    for(i in 0 until it.length()){
                        val data = it.getJSONObject(i)
                        val detail = data.getJSONObject("user")

                        issuelist.add(Issues(data.getString("title"),detail.getString("login"),detail.getString("avatar_url")))
                    }

                    issueAdapter.updateIssue(issuelist)

                }
                catch (e:Exception){
                    Toast.makeText(applicationContext,"Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                Toast.makeText(this, "Volley error occurred!!!", Toast.LENGTH_SHORT).show()
            }){}
            queue.add(jsonObjectRequest)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.delete){
            Toast.makeText(applicationContext,"Deleted Successfully",Toast.LENGTH_SHORT).show()
            ReposViewModel(application).deleteRepo(repoDetail)
            finish()
        }
        if(item.itemId==R.id.open_browser){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(repoDetail.repoUrl)
            startActivity(i)
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}