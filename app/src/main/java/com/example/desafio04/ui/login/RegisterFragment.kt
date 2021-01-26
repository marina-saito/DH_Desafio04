package com.example.desafio04.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafio04.ui.main.MainActivity
import com.example.desafio04.R
import com.example.desafio04.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this.requireActivity(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

}