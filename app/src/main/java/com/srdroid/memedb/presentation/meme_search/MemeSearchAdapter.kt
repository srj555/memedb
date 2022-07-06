package com.srdroid.memedb.presentation.meme_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.srdroid.memedb.databinding.ViewHolderSearchListBinding
import com.srdroid.memedb.domain.model.MemeModel

class MemeSearchAdapter : RecyclerView.Adapter<MemeSearchAdapter.MyViewHolder>() {


    private var listener: ((MemeModel) -> Unit)? = null

    var list = mutableListOf<MemeModel>()

    fun setContentList(list: MutableList<MemeModel>) {
        this.list = list
        notifyDataSetChanged()
    }


    class MyViewHolder(val viewHolder: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ViewHolderSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun itemClickListener(l: (MemeModel) -> Unit) {
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
}