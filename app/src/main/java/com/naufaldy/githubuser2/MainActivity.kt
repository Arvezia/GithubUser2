package com.naufaldy.githubuser2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.naufaldy.githubuser2.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(){

    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserData()
        showRecyclerList()


       //users.addAll(UserData.listdata)
    }

    private fun showRecyclerList(){
    binding.rvUsers.layoutManager =LinearLayoutManager(this)
    val githubUserAdapter = GithubUserAdapter(this)
    binding.rvUsers.adapter = githubUserAdapter
    //githubUserAdapter.setOnItemClickCallback(this)
    }

/*    private fun onItemClicked(data: UserData){
    val moveUser = Intent(this@MainActivity, GithubUserDetail::class.java)
        moveUser.putExtra(GithubUserDetail.EXTRA_USER, data)
    }*/

    private fun getUserData(){
    binding.progressBar.visibility = View.VISIBLE
    val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
    val url = "https://api.github.com/users"

    client.get(url, object : AsyncHttpResponseHandler() {
        override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
            binding.progressBar.visibility = View.INVISIBLE

            val result = String(responseBody)
            Log.d(TAG, result)
            try {
                val responseObject = JSONObject(result)
                val jsonArray = JSONArray(result)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val username: String = jsonObject.getString("login")
                    getUserDetail(username)
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                e.printStackTrace()
            }
        }


        override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
            binding.progressBar.visibility = View.INVISIBLE
            val errorMessage =when (statusCode){
                401 -> "$statusCode : Bad Request"
                403 -> "$statusCode : Forbidden"
                404 -> "$statusCode : Not Found"
                else -> "$statusCode : ${error.message}"
            }
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
    private fun getUserDetail(userId: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com//$userId"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
            val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val github = JSONObject(result)
                    val username: String? =github.getString("login").toString()
                    val name: String? = github.getString("name").toString()
                    val avatar : String? = github.getString("avatar_url").toString()
                    val followers : String? = github.getString("followers")
                    val following : String? = github.getString("following")
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {

            }
        })
    }
}




