package com.bedroomcomputing.tobecomethebest.ui.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bedroomcomputing.tobecomethebest.databinding.ResultItemBinding

class RankingAdapter : ListAdapter<RankingItem, RankingAdapter.RankingViewHolder>(RankingItemComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ResultItemBinding.inflate(layoutInflater, parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class RankingViewHolder(val binding: ResultItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:RankingItem){
            binding.textResultItemName.text = "#${item.rank} ${item.name}"
            binding.textResultItemPoint.text = String.format("%,d", item.score)
        }
    }

    class RankingItemComparator : DiffUtil.ItemCallback<RankingItem>() {
        override fun areItemsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean {
            return oldItem.name == newItem.name
        }
    }

}
