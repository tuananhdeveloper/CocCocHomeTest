package com.tuananh.simplebrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuananh.simplebrowser.databinding.ItemNormalArticleBinding
import com.tuananh.simplebrowser.databinding.ItemPodcastArticleBinding
import com.tuananh.simplebrowser.model.Article
import com.tuananh.simplebrowser.model.NormalArticle
import com.tuananh.simplebrowser.model.PodcastArticle

class MyAdapter(
    private val list: List<Article>,
    private val onItemClick: (Article) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType) {
            Article.NORMAL_TYPE -> NormalViewHolder(
                ItemNormalArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Article.PODCAST_TYPE -> PodcastViewHolder(
                ItemPodcastArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> {}
        } as RecyclerView.ViewHolder
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder.itemViewType) {
            Article.NORMAL_TYPE -> (holder as NormalViewHolder).bind(list[position] as NormalArticle)
            Article.PODCAST_TYPE -> (holder as PodcastViewHolder).bind(list[position] as PodcastArticle)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getType()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class NormalViewHolder(
        private val binding: ItemNormalArticleBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(list[adapterPosition])
            }
        }

        fun bind(normalArticle: NormalArticle) {
            binding.tvTitle.text = normalArticle.title
            binding.tvAuthor.text = normalArticle.author
            binding.tvDescription.text = normalArticle.description
            Glide.with(binding.ivArticle.context)
                .load(normalArticle.imageUrl)
                .centerCrop()
                .into(binding.ivArticle)
        }
    }

    inner class PodcastViewHolder(
        private val binding: ItemPodcastArticleBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(list[adapterPosition])
            }
        }

        fun bind(podcastArticle: PodcastArticle) {
            binding.tvTitle.text = podcastArticle.title
            binding.tvDescription.text = podcastArticle.description
            Glide.with(binding.ivArticle.context)
                .load(podcastArticle.imageUrl)
                .centerCrop()
                .into(binding.ivArticle)
        }
    }
}