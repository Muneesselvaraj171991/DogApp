package com.dog.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dog.app.databinding.ItemBinding
import com.dog.app.model.Dog


class DogListAdapter(
    private val mItemClickListener: ItemClick
) : RecyclerView.Adapter<DogListAdapter.ItemViewHolder>() {
    private val mDiffer: AsyncListDiffer<Dog> = AsyncListDiffer<Dog>(this, DIFF_CALLBACK)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.dog = mDiffer.currentList[position]
        holder.binding.listener = mItemClickListener

    }

    fun submitList(list: List<Dog?>?) {
        mDiffer.submitList(list)
    }
    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    interface ItemClick {
        fun onClick(dog: Dog)
    }

    inner class ItemViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(oldItem: Dog, newItem: Dog) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Dog, newItem: Dog) =
                oldItem == newItem
        }
    }


}