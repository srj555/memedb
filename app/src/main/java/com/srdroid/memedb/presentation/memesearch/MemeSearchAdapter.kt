package com.srdroid.memedb.presentation.memesearch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srdroid.memedb.databinding.ViewHolderSearchListBinding
import com.srdroid.memedb.presentation.model.MemeItemUIState

class MemeSearchAdapter : RecyclerView.Adapter<MemeSearchAdapter.MyViewHolder>() {

    private var listener: ((MemeItemUIState) -> Unit)? = null

    var list = mutableListOf<MemeItemUIState>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContentList(list: MutableList<MemeItemUIState>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ViewHolderSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Item click listener
     */
    fun itemClickListener(l: (MemeItemUIState) -> Unit) {
        listener = l
    }

    /**
     * Bind view adapter
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewHolder.meme = this.list[position]
        // Set click listener for each item
        holder.viewHolder.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    /**
     * Get Item Count
     */
    override fun getItemCount(): Int {
        return this.list.size
    }

    /**
     * ViewHolder
     */
    class MyViewHolder(val viewHolder: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)
}