package com.example.discuzandoird.fragment

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.discuzandoird.R
import com.example.discuzandoird.databinding.AccountFragmentBinding
import com.example.discuzandoird.viewmodel.AccountViewModel

class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: AccountFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        binding.buttonLogin.setOnClickListener {
            val controller:NavController = Navigation.findNavController(binding.root)
            controller.navigate(R.id.action_fragmentAccount_to_loginFragment)
        }
        // TODO: Use the ViewModel
    }

}