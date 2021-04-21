package com.naufaldy.githubuser2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
    private val listUser = ArrayList<UserData>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchUser()
        showRecyclerList()
        getUserData()


       //users.addAll(UserData.listdata)
    }

    private fun showRecyclerList(){
        adapter = GithubUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun searchUser(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            //val searchView = binding.searchView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            if(query.isNotEmpty()){
                listUser.clear()
                showRecyclerList()
                getSearchUser(query)
                showLoading(true)
                getUserData()
            } else{
            return true
            }
            return true
        }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
    })
    }

    private fun onItemClicked(data: UserData){
    val moveUser = Intent(this@MainActivity, GithubUserDetail::class.java)
        moveUser.putExtra(GithubUserDetail.EXTRA_USER, data)
    }

    private fun getUserData(){

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {


                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    //val responseObject = JSONObject(result)
                    val jsonArray = JSONArray(result)
                        for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                            var userData = UserData(username)
                        listUser.add(userData)

                    }

                    getUserDetail(listUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    /*Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()*/

                }

            }


            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {

                val errorMessage =when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", error.message.toString())
                /*Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()*/
            }
        })
    }
    private fun getSearchUser(query: String) {

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

            val url = "https://api.github.com/search/users?q=${query}"

            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {


                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val jsonArray = JSONArray(result)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val username: String = jsonObject.getString("login")
                            var userData = UserData(username)
                            listUser.add(userData)

                        }

                        getUserDetail(listUser)

                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                        /*Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()*/

                    }

                }


                override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {

                    val errorMessage = when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                    Log.d("onFailure", error.message.toString())
                    /*Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()*/
                }
            })
        }


    private fun getUserDetail(listUser: ArrayList<UserData>) {



        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        for ((i,userData ) in listUser.withIndex()) {
            var urluser = "https://api.github.com/users/${userData.username}"

            client.get(urluser, object : AsyncHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val github = JSONObject(result)
                        val username: String? = github.getString("login").toString()
                        val name: String? = github.getString("name").toString()
                        val avatar: String? = github.getString("avatar_url").toString()
                        val followers: String? = github.getString("followers")
                        val following: String? = github.getString("following")
                        listUser[i].name = name
                        listUser[i].avatar = avatar
                        listUser[i].followers = followers
                        listUser[i].following = following

                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }


                override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
        adapter.setData(listUser)
        showLoading(false)
    }

}




