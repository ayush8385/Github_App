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
import com.ayush.githubapp.Model.Branches
import com.ayush.githubapp.Model.Issues
import com.ayush.githubapp.Model.Repos
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class IssueAdapter(val context: Context) : RecyclerView.Adapter<IssueAdapter.HomeViewHolder>(){
    val itemlist= arrayListOf<Issues>()
    fun updateIssue(items:ArrayList<Issues>){
        itemlist.clear()
        itemlist.addAll(items)
        notifyDataSetChanged()
    }

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.issue_creator)
        val title:TextView=view.findViewById(R.id.issue_title)
        val image:CircleImageView=view.findViewById(R.id.issue_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.issue_list,parent,false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val repo = itemlist[position]
        holder.title.text=repo.title
        holder.name.text=repo.name
        Picasso.get().load(repo.img).into(holder.image)

    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}