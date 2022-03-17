package com.ayush.githubapp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.githubapp.Model.Repos
import com.ayush.githubapp.database.ReposEntity

class RepoAdapter(val context: Context,val itemlist:ArrayList<ReposEntity>) : RecyclerView.Adapter<RepoAdapter.HomeViewHolder>(){
    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.repo_name)
        val desc:TextView=view.findViewById(R.id.repo_desc)

        val details:LinearLayout=view.findViewById(R.id.details_box)
        val share:ImageView=view.findViewById(R.id.share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.repo_list,parent,false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val repo = itemlist[position]
        holder.name.text=repo.repoName
        holder.desc.text=repo.repoDesc

        holder.details.setOnClickListener {
            context.startActivity(Intent(context, Details::class.java).putExtra("id",repo.repoId))
        }

        holder.share.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Github Browser")
                var shareMessage = "Visit this Repo on github named as ${repo.repoName} : ${repo.repoDesc}\n${repo.repoUrl}"

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                context.startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }

    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}