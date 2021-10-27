package com.example.mypoetry.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.HotComment
import com.example.mypoetry.Model.Saying
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.application.BaseApplication

class SayingAdapter(private val hotCommentList: List<HotComment>,var mineViewModel: MineViewModel,val data: MutableLiveData<User>): RecyclerView.Adapter<SayingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.sayingPhoto)
        val itemName: TextView = view.findViewById(R.id.sayingName)
        val itemTime: TextView = view.findViewById(R.id.sayingTime)
        val itemWord: TextView = view.findViewById(R.id.sayingWord)
        val likeImage: ImageView = view.findViewById(R.id.likeImage)
    }
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.saying_item,parent,false)

       val viewHolder = ViewHolder(view)

       viewHolder.likeImage.setOnClickListener {
           val position = viewHolder.adapterPosition
           val hotComment = hotCommentList[position]

           if(!data.value?.getSayingPoetList()?.contains(hotComment.content)!!){
               viewHolder.likeImage.setImageResource(R.drawable.likesaying)
               data.value?.getSayingPoetList()!!.add(hotComment.content)
               mineViewModel.update(data.value!!)

           }else{
               viewHolder.likeImage.setImageResource(R.drawable.nolikesaying)
               data.value?.getSayingPoetList()!!.remove(hotComment.content)
               mineViewModel.update(data.value!!)
           }

       }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return hotCommentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotComment = hotCommentList[position]
        holder.itemName.text = hotComment.nickname
        holder.itemTime.text = hotComment.published_date.substring(5,hotComment.published_date.length)
        holder.itemWord.text = hotComment.content
        if(data.value?.getSayingPoetList() != null && !data.value?.getSayingPoetList()?.contains(hotComment.content)!!){
            holder.likeImage.setImageResource(R.drawable.nolikesaying)
        }else{
            holder.likeImage.setImageResource(R.drawable.likesaying)
        }
        Glide.with(BaseApplication.context).load(hotComment.avatar_url).into(holder.itemImage)
    }

}