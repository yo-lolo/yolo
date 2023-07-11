package com.example.myapplication.ui.page.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentFourBinding


class FourFragment : BaseFragment() {

    private lateinit var binding : FragmentFourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFourBinding.inflate(layoutInflater)
        return binding.root
    }

}