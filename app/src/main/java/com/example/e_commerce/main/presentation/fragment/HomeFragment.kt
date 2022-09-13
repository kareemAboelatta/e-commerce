package com.example.e_commerce.main.presentation.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerce.R
import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.databinding.HomeFragmentBinding
import com.example.e_commerce.main.data.dto.toSummaryProduct
import com.example.e_commerce.main.domain.models.SliderItem
import com.example.e_commerce.main.domain.models.SummaryProduct
import com.example.e_commerce.main.presentation.MainActivity
import com.example.e_commerce.main.presentation.ProductViewModel
import com.example.e_commerce.main.presentation.adapters.MostPopularAdapter
import com.example.e_commerce.main.presentation.adapters.SliderAdapter
import com.google.android.material.snackbar.Snackbar
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProductViewModel>()

    lateinit var viewPager: ViewPager2

    @Inject
    lateinit var mostPapularAdapter: MostPopularAdapter

    @Inject
    lateinit var recommendedAdapter: MostPopularAdapter

    val sliderHandler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val list = ArrayList<SliderItem>()

        list.add(SliderItem(R.drawable.image1))
        list.add(SliderItem(R.drawable.image2))
        list.add(SliderItem(R.drawable.image3))

        viewPager=binding.viewpagerImageSlider
        binding.viewpagerImageSlider
            .adapter=SliderAdapter(list, binding.viewpagerImageSlider)

        viewPager.clipChildren=false
        viewPager.clipToPadding=false
        viewPager.offscreenPageLimit=3
        viewPager.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER


        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(50))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager.setPageTransformer(compositePageTransformer)


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    // you are on the first page
                }
                else if (position == 1) {
                    // you are on the second page
                }
                else if (position == 2){
                    // you are on the third page  (last one)
                }
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,3000)

            }
        })

        binding.recyclerViewPopular.adapter=mostPapularAdapter
        binding.recyclerRecommended.adapter=recommendedAdapter


        viewModel.getMostPopular()
        viewModel.getRecommended()

        observePopulars()
        observeRecommended()

        mostPapularAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("product", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_fragmentProductDetails,
                bundle
            )
        }

        recommendedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("product", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_fragmentProductDetails,
                bundle
            )
        }


    }


    private fun observePopulars(){
        lifecycleScope.launch {
            viewModel.mostPopularState.collect{
                when (it) {
                    is Resource.Success -> {
                        binding.shimmer.visibility=View.GONE
                        mostPapularAdapter.differ.submitList(it.data?.documents?.map { it.toSummaryProduct() })
                        Toast.makeText(requireActivity(), "Success ${it.data?.documents?.get(0)?.title}", Toast.LENGTH_SHORT).show()
                        Log.e("HomeFragment", "Success pop ${it.data?.documents?.size}")
                        Log.e("HomeFragment", "Success pop result ${it.data?.result}")
                    }
                    is Resource.Error -> {
                        binding.shimmer.visibility=View.GONE
                        Log.e("HomeFragment", "my error: " + it.message)
                        Toast.makeText(requireActivity(), "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.shimmer.visibility=View.VISIBLE
                        Log.e("HomeFragment", "loading")
                    }
                }
            }
        }
    }
    private fun observeRecommended(){
        lifecycleScope.launch {
            viewModel.recommendedState.collect{
                when (it) {
                    is Resource.Success -> {
                        recommendedAdapter.differ.submitList(it.data)
                        Log.e("HomeFragment", "Success rec ${it.data?.size}" + it.message)
                        Log.e("HomeFragment", "Success rec ${it.data?.size}" + it.message)
                    }
                    is Resource.Error -> {
                        Log.e("HomeFragment", "error: " + it.message)
                        Toast.makeText(requireActivity(), "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Log.e("HomeFragment", "loading")
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)



        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var sliderRunnable = Runnable {
        lifecycleScope.launchWhenStarted {
            viewPager.currentItem = viewPager.currentItem+1
        }
    }





    fun getProductsFromSerever():ArrayList<SummaryProduct> {

        val mylist = ArrayList<SummaryProduct>()
        for (i in 1..10){
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Mac Book",
                    colors = listOf("red"  ,"blue"),
                    category = "laptops",
                    subCategory = listOf("assadasd555asd"),
                    description = "i asdcas x nk lk n ln kn klnlcn sldfdklf",
                    imageCover = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YXBwbGUlMjBsYXB0b3B8ZW58MHx8MHx8&w=1000&q=80",
                    images = listOf(),
                    price = 555.55,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Activ-05",
                    colors = listOf("red"  ,"blue"),
                    category = "sad5asdasd",
                    subCategory = listOf("assadasd555asd"),
                    description = "i asdcas x nk lk n ln kn klnlcn sldfdklf",
                    imageCover = "https://cdn.shopify.com/s/files/1/0522/8504/6984/products/1_d4b692b7-3a07-4fb0-b3b6-7eee5cbdab3e.jpg?v=1640010242&width=1500",
                    images = listOf(),
                    price = 90.99,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Nice Sport 07",
                    colors = listOf("red"  ,"blue"),
                    category = "sad5asdasd",
                    subCategory = listOf("assadasd555asd"),
                    description = "i asdcas x nk lk n ln kn klnlcn sldfdklf",
                    imageCover = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8c2hvZXN8ZW58MHx8MHx8&w=1000&q=80",
                    images = listOf(),
                    price = 33.0,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "T-shirt",
                    colors = listOf("red"  ,"blue"),
                    category = "sad5asdasd",
                    subCategory = listOf("assadasd555asd"),
                    description = "i asdcas x nk lk n ln kn klnlcn sldfdklf",
                    imageCover = "https://5.imimg.com/data5/CR/OL/NO/ANDROID-36904487/img-20181220-wa0001-jpg-500x500.jpg",
                    images = listOf(),
                    price = 88.55,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Cameras",
                    colors = listOf("red"  ,"blue"),
                    category = "sad5asdasd",
                    subCategory = listOf("assadasd555asd"),
                    description = "i asdcas x nk lk n ln kn klnlcn sldfdklf",
                    imageCover = "https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8bGVuc3xlbnwwfHwwfHw%3D&w=1000&q=80",
                    images = listOf(),
                    price = 577.5,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Makeup Organiser Cosmetic Storage Acrylic",
                    colors = listOf("red"  ,"blue"),
                    category = "Makeup",
                    subCategory = listOf("assadasd555asd"),
                    description = "Makeup Organiser Cosmetic Storage Acrylic: Cosmetics Organisers Makeup Holder Clear - Make up Stand Macallen Tray Organizer Bathroom Desktop Jewellery Lipstick Skincare Plastic Countertop",
                    imageCover = "https://m.media-amazon.com/images/I/81m+4dZFVFS._AC_SX425_.jpg",
                    images = listOf(),
                    price = 299.5,
                    priceAfterDiscount = 535,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Apple iPhone 13 Pro Max",
                    colors = listOf("red"  ,"blue"),
                    category = "Mobiles",
                    subCategory = listOf("assadasd555asd"),
                    description = "6.7-inch Super Retina XDR display with ProMotion for a faster, more responsive feel\n" +
                            "Cinematic mode adds shallow depth of field and shifts focus automatically in your videos\n" +
                            "Pro camera system with new 12MP Telephoto, Wide, and Ultra Wide cameras; LiDAR Scanner; 6x optical zoom range; macro photography; Photographic Styles, ProRes video, Smart HDR 4, Night mode, Apple ProRAW, 4K Dolby Vision HDR recording\n" +
                            "12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording",
                    imageCover = "https://m.media-amazon.com/images/I/614fbeOTjDL._AC_SY550_.jpg",
                    images = listOf(),
                    price = 28999.99,
                    priceAfterDiscount = 29000,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Dell Gaming Monitor",
                    colors = listOf("red"  ,"blue"),
                    category = "Pc",
                    subCategory = listOf("assadasd555asd"),
                    description = "Brand: Dell\n" +
                            "Sleek design\n" +
                            "Designed to perfection\n" +
                            "Compact construction",
                    imageCover = "https://m.media-amazon.com/images/I/31YchTv0VzS._AC_.jpg",
                    images = listOf(),
                    price = 4999.99,
                    priceAfterDiscount = 29000,
                    quantity = "33",
                    sold = 35

                )
            )
            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "pink blazer",
                    colors = listOf("pink"  ,"blue", "red"),
                    category = "Clothes",
                    subCategory = listOf("women","jackets","models"),
                    description = "Self: 55% linen, 45% viscose\n" +
                            "Lining: 97% poly, 3% elastane\n" +
                            "Made in China\n" +
                            "Dry clean only\n" +
                            "Single front button closure\n" +
                            "Padded shoulders\n" +
                            "Side flap pockets\n" +
                            "Back vent\n" +
                            "Linen fabric with satin lining\n" +
                            "Revolve Style No. BARD-WO73\n" +
                            "Manufacturer Style No. 57841JB1",
                    imageCover = "https://www.fornewz.com/wp-content/uploads/2021/07/trending-fashion-of-2021.jpg",
                    images = listOf(),
                    price = 400.00,
                    priceAfterDiscount = 29000,
                    quantity = "33",
                    sold = 35

                )
            )


            mylist.add(
                SummaryProduct(
                    _id = "asdasd",
                    title = "Glasses",
                    colors = listOf("pink"  ,"blue","orange0"),
                    category = "Accessories",
                    subCategory = listOf("women"),
                    description = "Self: 55% linen, 45% viscose\n" +
                            "Lining: 97% poly, 3% elastane\n" +
                            "Made in China\n" +
                            "Dry clean only\n" +
                            "Single front button closure\n" +
                            "Padded shoulders\n" +
                            "Side flap pockets\n" +
                            "Back vent\n" +
                            "Linen fabric with satin lining\n" +
                            "Revolve Style No. BARD-WO73\n" +
                            "Manufacturer Style No. 57841JB1",
                    imageCover = "https://image.freepik.com/fotos-kostenlos/das-junge-schelmische-maedchen-in-der-rosa-sonnenbrille-strahlt-vor-glueck-und-sendet-einen-luftkuss-nahes-portraet-der-niedlichen-braunhaarigen-frau_197531-12066.jpg",
                    images = listOf(),
                    price = 400.00,
                    priceAfterDiscount = 29000,
                    quantity = "33",
                    sold = 35

                )
            )
        }


        return mylist
    }
}