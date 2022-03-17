package com.ayush.githubapp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.githubapp.Model.Branches
import com.ayush.githubapp.Model.Commit
import com.ayush.githubapp.Model.Repos
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class CommitAdapter(val context: Context,val itemlist:ArrayList<Commit>) : RecyclerView.Adapter<CommitAdapter.HomeViewHolder>(){


    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.commit_name)
        val desc:TextView=view.findViewById(R.id.commit_desc)
        val image:CircleImageView=view.findViewById(R.id.commit_img)
        val date:TextView=view.findViewById(R.id.date)
        val sha:TextView=view.findViewById(R.id.sha)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.commit_list,parent,false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val repo = itemlist[position]
        holder.name.text=repo.name
        holder.desc.text=repo.desc
        holder.date.text=repo.date
        Picasso.get().load(repo.img).into(holder.image)

        holder.sha.text=repo.sha.take(6)
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}