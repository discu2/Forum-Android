package com.example.discuzandoird.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.discuzandoird.R
import com.example.discuzandoird.databinding.LoginFragmentBinding
import com.example.discuzandoird.viewmodel.AccountViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val controller: NavController = Navigation.findNavController(this.requireView())

        accountViewModel.accountRepository.observe(
            viewLifecycleOwner
        ) {
            if (it.auth.isLoggedIn) {
                controller.popBackStack()
            }
        }

        binding.button.setOnClickListener {
            val username = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            accountViewModel.login(username, password)
        }
    }

}