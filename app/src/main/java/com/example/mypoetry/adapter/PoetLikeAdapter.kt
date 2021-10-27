package com.example.mypoetry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypoetry.Model.OnItemOnClickListener
import com.example.mypoetry.R
import com.example.mypoetry.application.BaseApplication

class PoetLikeAdapter(var likepoet: ArrayList<String>): RecyclerView.Adapter<PoetLikeAdapter.ViewHolder>() {

    lateinit var mOnItemOnClickListener: OnItemOnClickListener
    fun setOnItemClickListener(listener: OnItemOnClickListener ){
        this.mOnItemOnClickListener = listener;
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.poet_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(BaseApplication.context).inflate(R.layout.poet_like_item,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return likepoet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val saying = likepoet[position]
        holder.textView.text = saying
        holder.itemView.setOnLongClickListener { v ->
            mOnItemOnClickListener.onItemLongOnClick(v,holder.adapterPosition,saying)
            false
        }
    }

    fun removeItem(pos: Int){
        likepoet.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(0,likepoet.size - 1)
    }

}