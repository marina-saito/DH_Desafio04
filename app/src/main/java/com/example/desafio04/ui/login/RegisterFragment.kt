package com.example.desafio04.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafio04.MainActivity
import com.example.desafio04.R
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        view.btnRegister.setOnClickListener {
            val intent = Intent(this.requireActivity(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }

}