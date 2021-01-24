package com.example.desafio04.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.desafio04.MainActivity
import com.example.desafio04.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.btnLogin.setOnClickListener {
            val intent = Intent(this.requireActivity(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        view.tvCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }

}