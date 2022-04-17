package com.example.discuzandoird.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.toolbox.Volley
import com.example.discuzandoird.R
import com.example.discuzandoird.databinding.AccountFragmentBinding
import com.example.discuzandoird.viewmodel.AccountViewModel

class AccountFragment : Fragment() {

    private lateinit var binding: AccountFragmentBinding
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        accountViewModel.setQueue(Volley.newRequestQueue(requireActivity()))
        accountViewModel.accountRepository.observe(
            viewLifecycleOwner
        ) {
            if (it.auth.isLoggedIn) {
                binding.textView.text = it.username
                binding.buttonLogin.isVisible = false
            } else {
                binding.textView.text = "Please login"
            }
        }

        binding.buttonLogin.setOnClickListener {

            val controller: NavController = Navigation.findNavController(this.requireView())
            controller.navigate(R.id.action_fragmentAccount_to_loginFragment)

        }
    }
}