package com.jay.kotlin.customerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jay.kotlin.customerview.databinding.ItemViewBinding

class ItemAdapter constructor(dataList: List<ItemEntity>?, clickListener: ClickListener) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    var data = dataList
    var listener = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.create(LayoutInflater.from(parent.context), parent, listener)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(data?.get(position))
    }

    class MyViewHolder private constructor(
        private val binding: ItemViewBinding,
        callback: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(
                inflater: LayoutInflater,
                parent: ViewGroup,
                callback: ClickListener
            ): MyViewHolder {
                val itemBinding = ItemViewBinding.inflate(inflater, parent, false)
                return MyViewHolder(itemBinding, callback)
            }
        }

        init {
            binding.root.setOnClickListener { v ->
                callback.onClick(binding.item)
            }
        }

        fun onBind(item: ItemEntity?) {
            binding.item = item
        }
    }

    interface ClickListener {
        fun onClick(item: ItemEntity?)
    }
}