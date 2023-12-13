package com.example.myapplication.ui.page.detail

import android.app.VoiceInteractor.Prompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailOneBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.OnceListAdapter
import com.example.myapplication.useCase.PromptUseCase
import com.example.myapplication.vm.DetailOneViewModel

class DetailOneFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailOneBinding
    private var onceListAdapter = OnceListAdapter()
    private val viewModel by viewModels<DetailOneViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailOneBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.onceList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(onceListAdapter)
        }
        binding.fab.setOnClickListener {
            initAddOnceView()
        }
        onceListAdapter.deleteListener = { position, data ->
            PromptUseCase().deletePrompt(data) {
                onceListAdapter.list.removeAt(position)
                notifyData()
            }
        }
    }

    /**
     * 初始化添加ONCE界面
     */
    private fun initAddOnceView() {
        val customView = layoutInflater.inflate(R.layout.fragment_create_once, null, false)
        val context = ActivityUtils.getTopActivity()
        val alertDialog = AlertDialog.Builder(context)
            .setView(customView)
            .setCancelable(false)
            .create()
        alertDialog.show()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window?.decorView?.setPadding(80, 0, 80, 0)

        val onceName = customView.findViewById<EditText>(R.id.once_name)
        val onceDesc = customView.findViewById<EditText>(R.id.once_desc)
        val onceCancel = customView.findViewById<TextView>(R.id.once_cancel)
        val descCount = customView.findViewById<TextView>(R.id.desc_count)
        val onceOk = customView.findViewById<TextView>(R.id.once_ok)
        onceOk.setOnClickListener {
            val name = onceName.text.toString().trim()
            val desc = onceDesc.text.toString().trim()
            onceListAdapter.list.add(name)
            notifyData()
            val flat = viewModel.insertOnce(name, desc)
            if (flat)
                alertDialog.dismiss()
        }
        onceCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        onceDesc.doOnTextChanged { text, _, _, _ ->
            descCount.text = "${text!!.length}/100"
        }
    }


    /**
     * 刷新适配器数据
     */
    private fun notifyData() {
        onceListAdapter.notifyDataSetChanged()
    }
}