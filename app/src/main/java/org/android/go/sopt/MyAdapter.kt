package org.android.go.sopt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGithubRepoBinding

class MyAdapter(context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private val itemList: List<Repo> = listOf(
        Repo("repo1", "author1"),
        Repo("repo2", "author2"),
        Repo("repo3", "author3"),
        Repo("repo4", "author4"),
        Repo("repo5", "author5"),
        Repo("repo6", "author6"),
        Repo("repo7", "author7"),
        Repo("repo8", "author8"),
        Repo("repo9", "author9"),
        Repo("repo10", "author10"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemGithubRepoBinding = ItemGithubRepoBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}

class MyViewHolder(private val binding: ItemGithubRepoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Repo) {
        binding.tvGithubRepo.text = item.name
        binding.tvGithubId.text = item.author
    }
}

data class Repo(
    val name: String,
    val author: String
)