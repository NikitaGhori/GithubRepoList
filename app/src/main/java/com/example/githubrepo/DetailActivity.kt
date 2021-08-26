package com.example.githubrepo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val listItemCount =
            intent.getStringArrayListExtra("repo")
        Glide.with(this).load(listItemCount?.get(0)).into(imgAvatar)
        textViewName . text = listItemCount?.get(1).toString()
        textViewFullname.text=listItemCount?.get(2).toString()
        textViewDesc.text=listItemCount?.get(3).toString()
        textViewSize.text=listItemCount?.get(4).toString()
        textViewStar.text=listItemCount?.get(5).toString()
        textViewLang.text=listItemCount?.get(6).toString()
        textViewType.text=listItemCount?.get(7).toString()
        textViewForks.text=listItemCount?.get(8).toString()
        textViewIssue.text=listItemCount?.get(9).toString()
        textViewWatcher.text=listItemCount?.get(10).toString()
        textViewDefaultBranch.text=listItemCount?.get(11).toString()
    }
}