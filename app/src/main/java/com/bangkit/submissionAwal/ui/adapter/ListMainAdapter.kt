package com.bangkit.submissionAwal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionAwal.data.remote.response.ItemsItem
import com.bangkit.submissionAwal.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class ListMainAdapter(
    private var onItemClick: ((ItemsItem) -> Unit)? = null
) : ListAdapter<ItemsItem, ListMainAdapter.UserViewHolding>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolding {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolding(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolding, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }


    class UserViewHolding(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem) {
            binding.tvItem.text = users.login
            Glide.with(binding.root.context)
                .load(users.avatarUrl)
                .circleCrop() // Menggunakan CircleCrop untuk membuat gambar menjadi bulat
                .into(binding.profileImage)
        }
    }

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClick = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}