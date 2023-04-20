package org.android.go.sopt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemRepoTitleBinding

class RepoTitleAdapter: RecyclerView.Adapter<RepoTitleAdapter.RepoTitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoTitleViewHolder {
        return RepoTitleViewHolder(ItemRepoTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RepoTitleViewHolder, position: Int) { }

    class RepoTitleViewHolder(private val binding: ItemRepoTitleBinding) :
        RecyclerView.ViewHolder(binding.root) { }
}