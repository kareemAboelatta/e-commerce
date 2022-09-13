package com.example.e_commerce.main.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.e_commerce.R
import com.example.e_commerce.databinding.PopularProductListItemBinding
import com.example.e_commerce.main.domain.models.SummaryProduct
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MostPopularAdapter @Inject constructor (
    @ApplicationContext var context: Context,
    private val glide: RequestManager
) : RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>() {

    inner class PopularViewHolder(val itemBinding : PopularProductListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(product: SummaryProduct) {
            glide.load(product.imageCover).into(itemBinding.imageViewPopular)
            itemBinding.productPrice.text=product.price.toString()+" $"
            itemBinding.productName.text=product.title.toString()
            itemBinding.root.setOnClickListener {
                onItemClickListener?.let { it(product) }

            }
        }

    }
    private var onItemClickListener: ((SummaryProduct) -> Unit)? = null
    fun setOnItemClickListener(listener: (SummaryProduct) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SummaryProduct>() {
            override fun areItemsTheSame(oldItem: SummaryProduct, newItem: SummaryProduct) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: SummaryProduct, newItem: SummaryProduct) =
                oldItem._id == newItem._id
        }
    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

 


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val itemBinding = PopularProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  PopularViewHolder(
            itemBinding
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val product = differ.currentList[position]
        val animation= AnimationUtils.loadAnimation(context, R.anim.product_popular)

        holder.itemView.apply {

            startAnimation(animation)
        }

        holder.apply {
            bindData(product)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}