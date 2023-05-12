package org.android.go.sopt.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.android.go.sopt.data.remote.model.ResponseFollowerDto
import org.android.go.sopt.databinding.ItemReqresFollowerBinding

class MyAdapter(context: Context) : ListAdapter<ResponseFollowerDto.Data, MyAdapter.MyViewHolder>(
    diffUtil
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemReqresFollowerBinding = ItemReqresFollowerBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class MyViewHolder(private val binding: ItemReqresFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ResponseFollowerDto.Data) {
            binding.tvName.text = item.first_name
            binding.tvEmail.text = item.email
            Glide.with(binding.root)
                .load(item.avatar)
                .circleCrop()
                .into(binding.ivImage)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResponseFollowerDto.Data>() {
            override fun areItemsTheSame(oldItem: ResponseFollowerDto.Data, newItem: ResponseFollowerDto.Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponseFollowerDto.Data, newItem: ResponseFollowerDto.Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}