package com.example.githubrepo

import android.app.AlertDialog
import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var githubDataAdapter: GithubDataAdapter


    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        connectivity = this.getSystemService(Service.CONNECTIVITY_SERVICE)
                as ConnectivityManager


        if ( connectivity != null)
        {
            info = connectivity!!.activeNetworkInfo

            if (info != null)
            {
                if (info!!.state == NetworkInfo.State.CONNECTED)
                {
                    getGithubRepoDataActivity()
                }

            }
            else
            {
                showAlertDialog()
            }
        }


        repo_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                githubDataAdapter.filter.filter(newText)
                return false
            }

        })

    }

    private fun getGithubRepoDataActivity() {
        var rf = Retrofit.Builder()
            .baseUrl(RepoInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RepoInterface::class.java)
        var call = API.getRepoData()

        call?.enqueue(object : Callback<List<GithubRepositoryResponse?>> {
            override fun onFailure(call: Call<List<GithubRepositoryResponse?>>, t: Throwable) {
                Log.d("Response", "-->" + t.message)

            }


            override fun onResponse(
                call: Call<List<GithubRepositoryResponse?>>,
                response: Response<List<GithubRepositoryResponse?>>
            ) {

                val recyclerViewRepo: RecyclerView = findViewById(R.id.recyclerViewRepo)
                githubDataAdapter =
                    GithubDataAdapter(response.body()!!, applicationContext)
                val layoutManagerRepo =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                recyclerViewRepo.layoutManager = layoutManagerRepo
                recyclerViewRepo.adapter =githubDataAdapter


                githubDataAdapter.setOnClickListener(object :
                    GithubDataAdapter.OnClickListener {

                    override fun onClick(position: Int, filterList: List<GithubRepositoryResponse?>) {
                         //Toast.makeText(applicationContext, "Clicked "+response.body()!![position]?.name, Toast.LENGTH_SHORT).show()
                        var detailList=ArrayList<String?>()
                        detailList.add(filterList[position]?.owner?.avatar_url)
                        detailList.add(filterList[position]?.name)
                        detailList.add(filterList[position]?.full_name)
                        detailList.add(filterList[position]?.description)
                        detailList.add(filterList[position]?.size.toString())
                        detailList.add(filterList[position]?.stargazers_count.toString())
                        detailList.add(filterList[position]?.language)
                        detailList.add(filterList[position]?.owner?.type)
                        detailList.add(filterList[position]?.forks.toString())
                        detailList.add(filterList[position]?.open_issues.toString())
                        detailList.add(filterList[position]?.watchers.toString())
                        detailList.add(filterList[position]?.default_branch)
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {  }
                        intent.putExtra("repo", detailList)
                        startActivity(intent)
                    }
                })
            }

        })
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("GithubRepo App")
        alertDialog.setMessage("Internet Unavailable, Please check your connection")
        alertDialog.setPositiveButton(
            "Ok"
        ) { _, _ ->
            //Toast.makeText(this@MainActivity, "Alert dialog closed.", Toast.LENGTH_LONG).show()
        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}