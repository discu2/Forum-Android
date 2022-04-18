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
import com.example.discuzandoird.databinding.FragmentAccountBinding
import com.example.discuzandoird.databinding.FragmentLoginBinding
import com.example.discuzandoird.databinding.FragmentRegisterBinding
import com.example.discuzandoird.viewmodel.AccountViewModel
import com.google.gson.Gson
import org.json.JSONObject

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.from_right))
        return binding.root

    }

    override fun onStart() {

        super.onStart()

        val controller: NavController = Navigation.findNavController(this.requireView())

        binding.buttonRegister.setOnClickListener {

            val mail = binding.editTextTextEmailRegister.text.toString()
            val username = binding.editTextTextPersonNameRegister.text.toString()
            val password = binding.editTextTextPasswordRegister.text.toString()

            accountViewModel.accountService.fetchApi(
                Request.Method.POST,
                accountViewModel.accountService.register(),
                JSONObject(Gson().toJson(AccountService.RegisterRequest(mail, username, password))),
                {

                    Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT).show()
                    controller.popBackStack()

                },
                {

                    Toast.makeText(
                        requireContext(),
                        it?.networkResponse?.statusCode.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            )
        }

    }

    override fun onStop() {
        super.onStop()

        binding.root.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.out_right))

    }

}