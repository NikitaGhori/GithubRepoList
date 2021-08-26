package com.example.githubrepo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList


class GithubDataAdapter(val resp: List<GithubRepositoryResponse?>, val context: Context) :
    RecyclerView.Adapter<GithubDataAdapter.MyViewHolder>() , Filterable{
    var repoFilterList : List<GithubRepositoryResponse?>? = null
    init {
        repoFilterList=resp
    }

    private var onClickListener: OnClickListener?= null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtName: TextView = view.findViewById(R.id.textViewName)
        var txtFname: TextView = view.findViewById(R.id.textViewFullname)
        var txtStar: TextView=view.findViewById(R.id.textViewStar)
        var imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        var llMain: LinearLayout=view.findViewById(R.id.llMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_repo_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.txtName.text = repoFilterList!![position]?.name
        holder.txtFname.text = repoFilterList!![position]?.full_name
        holder.txtStar.text=repoFilterList!![position]?.stargazers_count.toString()
        Glide.with(context).load(repoFilterList!!!![position]?.owner?.avatar_url).into(holder.imgAvatar)

        holder.llMain.setOnClickListener {
            if(onClickListener != null)
            {
                onClickListener!!.onClick(position, repoFilterList!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return repoFilterList!!.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener{
        fun onClick(position: Int, fliterList: List<GithubRepositoryResponse?>)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if(constraint.isNullOrBlank()) {
                    repoFilterList = resp
                } else {
                    val charSearch = constraint.toString().toUpperCase()

                    val resultList  = mutableListOf<GithubRepositoryResponse?>()

                    for (row in resp) {
                        if (row?.name?.lowercase(Locale.ROOT)!!.contains(charSearch.lowercase(Locale.ROOT))) {

                            resultList.add(row)

                        }
                    }
                    repoFilterList = resultList

                }
                val filterResults = FilterResults()
                filterResults.values = repoFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               repoFilterList = (results?.values as? List<GithubRepositoryResponse?>)?.orEmpty()

                notifyDataSetChanged()
            }

        }

    }
}

