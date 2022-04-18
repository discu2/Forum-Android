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
import com.android.volley.toolbox.Volley
import com.example.discuzandoird.R
import com.example.discuzandoird.bean.AccountBean
import com.example.discuzandoird.databinding.FragmentAccountBinding
import com.example.discuzandoird.viewmodel.AccountViewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        accountViewModel.accountBean.observe(
            viewLifecycleOwner
        ) {

            if (it.auth.isLoggedIn) {
                binding.textView.text = it.username
                binding.buttonLoginLogout.text = "Logout"
            } else {
                binding.textView.text = "Please login"
                binding.buttonLoginLogout.text = "Login"
            }

        }

        binding.buttonLoginLogout.setOnClickListener {

            if (accountViewModel.accountBean.value?.auth?.isLoggedIn == true) {
                accountViewModel.accountBean.postValue(AccountBean())
            } else {
                val controller: NavController = Navigation.findNavController(this.requireView())
                controller.navigate(R.id.action_fragmentAccount_to_loginFragment)
            }


        }
    }

    override fun onStop() {
        super.onStop()

        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.out_right))

    }

}