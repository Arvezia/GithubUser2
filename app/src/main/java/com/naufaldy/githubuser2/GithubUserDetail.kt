package com.naufaldy.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_github_user_detail.*

class GithubUserDetail : AppCompatActivity() {
    companion object{
    const val EXTRA_USER ="extra user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.followers,
                R.string.following
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        val user: UserData? =intent.getParcelableExtra(EXTRA_USER)

        if (user != null) {


            Glide.with(this)
                    .load(user.avatar)
                    .into(profile_picture)
            dt_name.text = user.name
            dt_username.text = user.username
            dt_followers.text ="Followers  ${user.followers}"
            dt_following.text = "Following  ${user.following}"

            tabViewPage(user.username!!)
        }

    }
    private fun tabViewPage(username:String){
        val detailPagerAdapter = DetailPagerAdapter(this,username)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){
            tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

}