package com.example.discuzandoird.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.volley.Request
import com.example.discuzandoird.R
import com.example.discuzandoird.api.AccountService
import com.example.discuzandoird.databinding.FragmentLoginBinding
import com.example.discuzandoird.viewmodel.AccountViewModel
import com.google.gson.Gson
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val controller: NavController = Navigation.findNavController(this.requireView())

        accountViewModel.accountBean.observe(
            viewLifecycleOwner
        ) {
            if (it.auth.isLoggedIn) {
                Toast.makeText(requireContext(), "logged in", Toast.LENGTH_SHORT).show()
                controller.popBackStack()
            }
        }

        binding.buttonLoginLogout.setOnClickListener {
            val username = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            accountViewModel.fetchApi(
                Request.Method.POST,
                AccountService().login(),
                JSONObject(
                    Gson().toJson(AccountService.LoginRequest(username, password))
                ),
                {
                    val response =
                        Gson().fromJson(it.toString(), AccountService.LoginResponse::class.java)
                    accountViewModel.accountBean.value?.auth?.accessToken = response.accessToken
                    accountViewModel.accountBean.value?.auth?.refreshToken = response.refreshToken
                    accountViewModel.accountBean.value?.username = username
                    accountViewModel.accountBean.value?.auth?.isLoggedIn = true
                    accountViewModel.updateAccountBean()
                },
                {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            )
        }

        binding.buttonRegister.setOnClickListener {

            controller.navigate(R.id.action_loginFragment_to_registerFragment)

        }
    }

    override fun onStop() {
        super.onStop()

        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.out_right))

    }

}