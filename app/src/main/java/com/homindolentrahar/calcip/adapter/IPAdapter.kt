package com.homindolentrahar.calcip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.calcip.R
import com.homindolentrahar.calcip.databinding.ListItemBinding
import com.homindolentrahar.calcip.model.IPResult

class IPAdapter(private val onClick: (item: IPResult) -> Unit) :
    ListAdapter<IPResult, IPAdapter.IPHolder>(IpComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IPHolder =
        IPHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: IPHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }

    }

    class IPHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IPResult) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object IpComparator : DiffUtil.ItemCallback<IPResult>() {
        override fun areItemsTheSame(oldItem: IPResult, newItem: IPResult): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: IPResult, newItem: IPResult): Boolean =
            oldItem == newItem
    }
}