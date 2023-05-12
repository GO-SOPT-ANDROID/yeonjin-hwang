package org.android.go.sopt.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemPagerBinding

class GalleryAdapter(_itemList: List<Int> = listOf()) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private var itemList: List<Int> = _itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    class GalleryViewHolder(
        private val binding: ItemPagerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(src: Int) {
            binding.imgGallery.setImageResource(src)
        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: List<Int>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}