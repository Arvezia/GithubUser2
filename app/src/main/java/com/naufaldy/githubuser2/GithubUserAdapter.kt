package com.naufaldy.githubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GithubUserAdapter(private val githubUsers: ArrayList<UserData>):
        RecyclerView.Adapter<GithubUserAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(items: ArrayList<UserData>){
        githubUsers.clear()
        githubUsers.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onitemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onitemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername : TextView = itemView.findViewById(R.id.tv_github_username)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_user_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserAdapter.ListViewHolder {
        val view: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.github_list_menu, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.ListViewHolder, position: Int) {
        val users=githubUsers[position]
        Glide.with(holder.itemView.context)
                .load(users.photo)
                .apply(RequestOptions().override(70,100))
                .into(holder.imgPhoto)

        holder.tvUsername.text = users.name
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(githubUsers[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    interface OnItemClickCallback{
    fun onItemClicked(data:UserData)
    }
}