package com.example.e_commerce.auth.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.auth.domain.models.OnBoarding
import com.example.e_commerce.databinding.ItemBannerSliderBinding
import com.example.e_commerce.databinding.ItemOnboardingViewPagerBinding
import com.example.e_commerce.main.domain.models.SliderItem
import com.example.e_commerce.main.presentation.adapters.SliderAdapter

class AdapterOnBoarding(
    var onboards : ArrayList<OnBoarding>
): RecyclerView.Adapter<AdapterOnBoarding.ImageViewHolder>() {

    inner class ImageViewHolder(private val itemBinding : ItemOnboardingViewPagerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(onboard: OnBoarding) {
            itemBinding.image.setImageResource(onboard.image)
            itemBinding.title.text=onboard.title
            itemBinding.descripition.text=onboard.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding = ItemOnboardingViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ImageViewHolder(
            itemBinding
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val board = onboards[position]
        holder.apply {
            bindData(board)
        }
    }

    override fun getItemCount(): Int {
       return onboards.size
    }

}