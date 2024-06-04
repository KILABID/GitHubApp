package com.bangkit.submissionAwal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionAwal.data.remote.response.FollowerResponse
import com.bangkit.submissionAwal.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class DetailsAdapter :
    ListAdapter<FollowerResponse, DetailsAdapter.UserViewHolding>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolding {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolding(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolding, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolding(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: FollowerResponse) {
            binding.tvItem.text = users.login
            Glide.with(binding.root.context)
                .load(users.avatarUrl)
                .into(binding.profileImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowerResponse>() {
            override fun areItemsTheSame(
                oldItem: FollowerResponse,
                newItem: FollowerResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowerResponse,
                newItem: FollowerResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}