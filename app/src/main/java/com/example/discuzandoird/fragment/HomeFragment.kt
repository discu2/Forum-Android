package com.example.discuzandoird.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.discuzandoird.R
import com.example.discuzandoird.databinding.FragmentHomeBinding
import com.example.discuzandoird.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_left))
        return binding.root
    }

}