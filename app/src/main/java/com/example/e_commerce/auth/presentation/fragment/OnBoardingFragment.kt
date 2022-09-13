package com.example.e_commerce.auth.presentation.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerce.R
import com.example.e_commerce.auth.domain.models.OnBoarding
import com.example.e_commerce.auth.presentation.adapter.AdapterOnBoarding
import com.example.e_commerce.core.extentions.deleteCurrentFragmentAfterNavigate
import com.example.e_commerce.core.extentions.navigateSafely
import com.example.e_commerce.core.utils.AppIds
import com.example.e_commerce.databinding.OnboardingFragmentBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment: Fragment() {
    private var _binding: OnboardingFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // actions
        binding.next.setOnClickListener {
            binding.viewPager2.setCurrentItem(binding.viewPager2.currentItem + 1, true);

        }

        binding.skip.setOnClickListener {

            // navigate and pop the current fragment form stack bcs we don't neen it anymore
            val options = findNavController().deleteCurrentFragmentAfterNavigate()
            findNavController().navigateSafely(
                AppIds.action_onBoardingFragment_to_loginFragment,
                navOptions = options
            )
        }

        binding.started.setOnClickListener {
            // navigate and pop the current fragment form stack bcs we don't neen it anymore
            val options = findNavController().deleteCurrentFragmentAfterNavigate()
            findNavController().navigateSafely(
                AppIds.action_onBoardingFragment_to_loginFragment,
                navOptions = options
            )
        }


        initViewPagerList()
    }


    private fun initViewPagerList() {
        val list = ArrayList<OnBoarding>()
        list.add(
            OnBoarding(
                R.drawable.onboarding1,
                "Fresh Food",
                "We saw a gap in the market for an upscale gourmet supermarket, " +
                        "and products from all of the over the world," +
                        " along came the idea of Fresh Food Market ‘a gourmet retailer."
            )
        )

        list.add(
            OnBoarding(
                R.drawable.onboarding2,
                "Faster Delivery",
                "We make food ordering fast,simple and free-no matter if you" +
                        " order online or crash. "
            )
        )
        list.add(
            OnBoarding(
                R.drawable.onboarding3,
                "Easy Payment",
                "Now use your Easypaisa app to order and pay for your food from the comfort of your home."
            )
        )
        binding.viewPager2.adapter = AdapterOnBoarding(list)


        initIndicators()
        subscribeToViewPager()
    }





    private fun subscribeToViewPager() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.indictor.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                binding.indictor.onPageSelected(position)
                if (position == 2) {
                    binding.started.visibility = View.VISIBLE
                    binding.skip.visibility = View.GONE
                    binding.next.visibility = View.GONE
                    binding.motion.transitionToEnd()
                } else {
                    binding.started.visibility = View.INVISIBLE
                    binding.skip.visibility = View.VISIBLE
                    binding.next.visibility = View.VISIBLE
                    binding.motion.transitionToStart()

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                /*empty*/
            }
        })
    }

    private fun initIndicators() {
        binding.indictor.apply {
            setSliderColor(
                R.color.colorPrimaryLight,
                R.color.colorPrimaryDark
            )
            setSliderWidth(80F)
            setSliderHeight(15f)
            setCheckedColor(R.color.colorPrimaryDark)
            setNormalColor(R.color.colorPrimaryLight)
            setSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(binding.viewPager2.adapter!!.itemCount)
            notifyDataChanged()
        }

    }













    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OnboardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}