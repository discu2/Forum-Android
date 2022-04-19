package com.example.discuzandoird.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.discuzandoird.R
import com.example.discuzandoird.databinding.FragmentHomeBinding
import com.example.discuzandoird.viewmodel.AccountViewModel
import com.example.discuzandoird.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_left))
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val accessToken = accountViewModel.accountBean.value?.accessToken
        viewModel.getBoardList(
            accessToken
        ) {
            Toast.makeText(
                requireContext(),
                it?.networkResponse?.statusCode.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.boardBeanList.observe(viewLifecycleOwner) {

            binding.textView2.text = viewModel.boardBeanList.value.toString()

        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.out_left))
    }

}