package com.example.redditloader.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.redditloader.R
import com.example.redditloader.data.model.Children
import com.example.redditloader.utils.load
import kotlinx.android.synthetic.main.item_recycler.view.*

class RedditListAdapter(val clickListener: (imageUrl: String) -> Unit = {}) :
    ListAdapter<Children, RedditListAdapter.RedditViewHolder>(ChildrenDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RedditViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
    )

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class RedditViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            clicked.data.url.let {
                clickListener.invoke(it)
            }
        }

        fun bind(item: Children) = with(itemView) {
            iv_image.load(item.data.url)
        }
    }

    private class ChildrenDC : DiffUtil.ItemCallback<Children>() {
        override fun areItemsTheSame(
            oldItem: Children,
            newItem: Children
        ): Boolean {
            return oldItem.data.url == newItem.data.url
        }

        override fun areContentsTheSame(
            oldItem: Children,
            newItem: Children
        ): Boolean {
            return oldItem == newItem
        }
    }
}