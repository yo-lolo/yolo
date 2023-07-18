package com.example.myapplication.ui.page.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentDetailOneBinding
import com.example.myapplication.databinding.FragmentDetailTwoBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.TwiceListAdapter
import com.example.myapplication.vm.DetailTwoViewModel

class DetailTwoFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailTwoBinding
    private val viewModel by viewModels<DetailTwoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTwoBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.detailTwiceList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(TwiceListAdapter())
        }
        binding.refreshLayout.apply {
            setEnableRefresh(true)
            setOnRefreshListener {
                viewModel.onRefresh()
            }
            setOnLoadMoreListener {
                viewModel.onLoadMore()
            }
        }
        viewModel.loadingTaskCount.observe(viewLifecycleOwner) { loading ->
            if (loading > 0) {
                binding.refreshLayout.finishRefresh()
                binding.refreshLayout.finishLoadMore()
            }
        }
    }
}