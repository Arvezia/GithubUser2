package com.naufaldy.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GithubUserDetail : AppCompatActivity() {
    companion object{
    const val EXTRA_USER ="extra user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)
    }
}