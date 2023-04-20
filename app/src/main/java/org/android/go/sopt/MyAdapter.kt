package org.android.go.sopt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.Repo
import org.android.go.sopt.databinding.ItemGithubRepoBinding

class MyAdapter(context: Context) : ListAdapter<Repo, MyAdapter.MyViewHolder>(diffUtil) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemGithubRepoBinding = ItemGithubRepoBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class MyViewHolder(private val binding: ItemGithubRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Repo) {
            binding.tvGithubRepo.text = item.name
            binding.tvGithubId.text = item.author
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}