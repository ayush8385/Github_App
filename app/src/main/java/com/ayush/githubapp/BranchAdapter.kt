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
import com.ayush.githubapp.Model.Repos
import com.ayush.hungreed.database.ReposEntity

class BranchAdapter(val context: Context) : RecyclerView.Adapter<BranchAdapter.HomeViewHolder>(){
    val itemlist= arrayListOf<Branches>()
    fun updateBranch(items:ArrayList<Branches>){
        itemlist.clear()
        itemlist.addAll(items)
        notifyDataSetChanged()
    }

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.branch_name)
        val box:RelativeLayout=view.findViewById(R.id.parent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.branch_list,parent,false)

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val repo = itemlist[position]
        holder.name.text=repo.name

        holder.box.setOnClickListener {
            context.startActivity(Intent(context,Commits::class.java).putExtra("branch_name",repo.name).putExtra("id",repo.ownerId))
        }
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}