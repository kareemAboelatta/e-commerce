package com.example.e_commerce.main.presentation.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ProductDetailsFragmentBinding
import com.example.e_commerce.main.presentation.ProductViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentProductDetails : Fragment() {
    private var _binding: ProductDetailsFragmentBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<ProductViewModel>()


    val args:FragmentProductDetailsArgs by navArgs()


    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val product=args.product



/*

        requireActivity().window.decorView
            .systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        requireActivity().window.statusBarColor = Color.TRANSPARENT

*/

        glide.load(product.imageCover).into(binding.productImage)

        binding.productName.text=product.title
        binding.productPrice.text= product.price.toString()+" $"
        binding.productDescription.text=product.description
        binding.productBrand.text="Adidas"
        binding.productCategory.text=product.category
        for (color in product.subCategory){
            setChips(color.toString())
        }


    }


    private fun setChips(color: String) {
        val chip = Chip(requireActivity())
        chip.text = color
        chip.textSize = 20f
        chip.setPadding(40,60,40,60)
        chip.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        chip.setChipBackgroundColorResource(R.color.colorPrimary)
        chip.chipStrokeWidth=5f
        chip.chipStrokeColor=ColorStateList.valueOf(R.color.colorPrimaryDark)

        chip.setTextColor(resources.getColor(R.color.white))
        //if you not create chipGroup in you xml yet then create it
        binding.productChipGroupSubCategories.addView(chip)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductDetailsFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        hideStatusBar()

    }
    override fun onPause() {
        super.onPause()
        showStatusBar()

    }


    private fun hideStatusBar() {
        requireActivity().window.decorView
            .systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        requireActivity().window.statusBarColor = Color.TRANSPARENT



    }

    private fun showStatusBar() {

        requireActivity().window.decorView
            .systemUiVisibility= SYSTEM_UI_FLAG_VISIBLE
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.colorPrimaryDark)

    }


}