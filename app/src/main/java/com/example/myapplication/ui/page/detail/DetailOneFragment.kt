package com.example.myapplication.ui.page.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailOneBinding

class DetailOneFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailOneBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

    }
}