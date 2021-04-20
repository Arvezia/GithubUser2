package com.naufaldy.githubuser2

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel: ViewModel() {
    private val dataArray = ArrayList<UserData>()
    private val dataArrayMutable = MutableLiveData<ArrayList<UserData>>()


}