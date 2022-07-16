package com.srdroid.memedb.presentation.memesearch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
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

    fun itemClickListener(l: (MemeItemUIState) -> Unit) {
        listener = l
    }

    private val set = ConstraintSet()

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

    /**
     * ViewHolder
     */
    class MyViewHolder(val viewHolder: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)
}