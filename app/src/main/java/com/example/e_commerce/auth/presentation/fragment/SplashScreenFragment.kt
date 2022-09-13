package com.example.e_commerce.auth.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.auth.presentation.AuthViewModel
import com.example.e_commerce.core.extentions.deleteCurrentFragmentAfterNavigate
import com.example.e_commerce.core.extentions.navigateSafely
import com.example.e_commerce.core.utils.AppIds
import com.example.e_commerce.databinding.SplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment(){
    private var _binding: SplashScreenBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<AuthViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get current screen width and height to apply animation
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


        binding.myLottie.animate()
            .rotation(-60f)
            .translationX(-400f)
            .setDuration(2000).startDelay = 2500

        binding.lottieContainer.animate()
            .translationX(width.toFloat()+50)
            .setDuration(2000).startDelay = 5000

        binding.logo.animate().translationY(-(height - 800).toFloat())
            .setDuration(1000).startDelay = 5600

        Handler().postDelayed({
            val options = findNavController().deleteCurrentFragmentAfterNavigate()
            findNavController().navigateSafely(
                AppIds.action_splashScreenFragment_to_onBoardingFragment,
                navOptions = options
            )
        },7200)



    }






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}