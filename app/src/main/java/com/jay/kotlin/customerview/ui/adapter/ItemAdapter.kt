package com.jay.kotlin.customerview.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.kotlin.customerview.R

class ItemAdapter constructor(dataList: List<ItemEntity>?, clickListener: ClickListener?) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    var data = dataList
    var listener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MyViewHolder(root, listener)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(data?.get(position))
    }

    class MyViewHolder(itemView: View, listener: ClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener?.onClick(item)
            }
        }

        fun bindData(item: ItemEntity?) {
            this.item = item
            image.setImageResource(item?.image ?: 0)
            title.text = item?.title
            description.text = item?.description
        }

        var item: ItemEntity? = null
        val image: ImageView = itemView.findViewById(R.id.logo)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    interface ClickListener {
        fun onClick(item: ItemEntity?)
    }
}