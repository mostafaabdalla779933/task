package com.example.task.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.R
import com.example.task.common.convertToTime
import com.example.task.common.getLoadingDrawable
import com.example.task.data.model.ArticlesItem
import com.example.task.databinding.ItemArticleBinding
import com.example.task.databinding.ItemShimmerBinding


class ArticlesAdapter(var list: List<ArticlesItem?> = emptyList(), val onclick: (ArticlesItem) -> Unit,val showSave:Boolean = true) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val rowView: ItemArticleBinding) :
        RecyclerView.ViewHolder(rowView.root) {
        fun onBind(item: ArticlesItem, position: Int) {
            rowView.apply {
                tvTitle.text = item.title ?: ""
                tvAuthor.text = "Author:${item.author ?: ""}"
                tvDate.text = item.publishedAt?.convertToTime() ?: ""
                Glide.with(itemView.context)
                    .load(item.urlToImage)
                    .placeholder(itemView.context.getLoadingDrawable())
                    .error(
                        AppCompatResources.getDrawable(
                            itemView.context,
                            R.drawable.ic_error_loading
                        )
                    )
                    .into(ivImage)
                btnSave.visibility = if(showSave) View.VISIBLE else View.GONE
                btnSave.setOnClickListener {
                    onclick(item)
                }
            }
        }
    }

    inner class ShimmerViewHolder(rowView: ItemShimmerBinding) :
        RecyclerView.ViewHolder(rowView.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE -> {
                ViewHolder(
                    ItemArticleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ShimmerViewHolder(
                    ItemShimmerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                list[position]?.let { holder.onBind(it, position) }
            }
            is ShimmerViewHolder -> {

            }
        }
    }

    fun setArticlesList(list: List<ArticlesItem?>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.getOrNull(position)?.isShimmer == true) {
            SHIMMER_TYPE
        } else {
            VIEW_TYPE
        }
    }

    override fun getItemCount(): Int = list.size

    companion object {
        const val SHIMMER_TYPE = 1
        const val VIEW_TYPE = 2
    }

}