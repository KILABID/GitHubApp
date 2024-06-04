package com.bangkit.submissionAwal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionAwal.data.local.entity.FavoritEntity
import com.bangkit.submissionAwal.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private var onItemClicked : ((FavoritEntity?) -> Unit)? = null
) : ListAdapter<FavoritEntity, FavoriteAdapter.ViewHolding>(DIFF_CALLBACK) {

    inner class ViewHolding(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: FavoritEntity) {
            Glide.with(binding.root.context)
                .load(users.avatarUrl)
                .circleCrop() // Menggunakan CircleCrop untuk membuat gambar menjadi bulat
                .into(binding.profileImage)
            binding.tvItem.text = users.username

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolding {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolding(binding)
    }

    override fun onBindViewHolder(holder: ViewHolding, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(user)
        }
    }

    fun setOnItemClickListener(listener: (FavoritEntity?) -> Unit) {
        onItemClicked = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoritEntity>() {
            override fun areItemsTheSame(oldItem: FavoritEntity, newItem: FavoritEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoritEntity, newItem: FavoritEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}