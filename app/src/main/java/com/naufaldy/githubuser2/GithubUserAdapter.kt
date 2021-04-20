package com.naufaldy.githubuser2

import android.content.Intent
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.naufaldy.githubuser2.databinding.ActivityGithubUserDetailBinding.bind
import com.naufaldy.githubuser2.databinding.ActivityMainBinding.bind
import com.naufaldy.githubuser2.databinding.GithubListMenuBinding
import kotlinx.android.synthetic.main.github_list_menu.view.*

class GithubUserAdapter:
        RecyclerView.Adapter<GithubUserAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? =null
    private val mData = ArrayList<UserData>()


    fun setData(items: ArrayList<UserData>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

   inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       /*private val binding = GithubListMenuBinding.bind(itemView)
       binding.tvGithubUsername.text = userData.username*/
        fun bind (userData: UserData){
            with(itemView){
                Glide.with(itemView)
                        .load(userData.avatar)
                        //.apply(RequestOptions().override(100, 100))
                        .into(itemView.img_user_photo)
                itemView.tv_github_username.text = userData.username

                }
            }
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.github_list_menu,viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(mData[position])

        val data = mData[position]
        listViewHolder.itemView.setOnClickListener{
        val userDataIntent = UserData(
                data.username,
                data.name,
                data.following,
                data.followers,
                data.avatar
        )
            val intent = Intent(it.context, GithubUserDetail::class.java)
            intent.putExtra(GithubUserDetail.EXTRA_USER,userDataIntent)
            it.context.startActivity(intent)
        }
        }

    override fun getItemCount(): Int = mData.size
    }
    interface OnItemClickCallback{
    fun onItemClicked(data:UserData)
    }
