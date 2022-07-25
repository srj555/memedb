package com.srdroid.memedb.presentation.memesearch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srdroid.memedb.databinding.ViewHolderSearchListBinding
import com.srdroid.memedb.presentation.model.MemeItemUIModel

class MemeSearchAdapter : RecyclerView.Adapter<MemeSearchAdapter.MyViewHolder>() {

    private var listener: ((MemeItemUIModel) -> Unit)? = null
    var list = mutableListOf<MemeItemUIModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContentList(list: MutableList<MemeItemUIModel>) {
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

    fun itemClickListener(l: (MemeItemUIModel) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewHolder.meme = this.list[position]
        holder.viewHolder.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    class MyViewHolder(val viewHolder: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)
}