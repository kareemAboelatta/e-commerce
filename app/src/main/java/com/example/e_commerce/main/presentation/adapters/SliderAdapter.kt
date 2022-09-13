package com.example.e_commerce.main.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerce.databinding.ItemBannerSliderBinding
import com.example.e_commerce.main.domain.models.SliderItem

class SliderAdapter(
    var images : ArrayList<SliderItem>,
    var viewPager: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(private val itemBinding : ItemBannerSliderBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(image: Int) {
            itemBinding.imageSlider.setImageResource(image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val itemBinding = ItemBannerSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ImageSliderViewHolder(
            itemBinding
        )
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val image = images[position]
        holder.apply {
            bindData(image.image)
        }

        if (position == images.size-2){
            viewPager.post(runnable)
        }
    }

    override fun getItemCount(): Int {
       return images.size
    }



    private var runnable = Runnable {
        images.addAll(images)
        notifyDataSetChanged()
    }
}