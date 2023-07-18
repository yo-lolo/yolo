package com.example.myapplication.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.myapplication.R
import com.example.myapplication.databinding.AdapterEmptyViewBinding
import com.example.myapplication.util.GlideImageLoader


class EmptyViewAdapter<T : RecyclerView.Adapter<V>, V : RecyclerView.ViewHolder>(private val adapter: T) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        // 为了支持adapter.notifyDataSetChanged方法，需注册业务adapter监听
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
            }
        })
    }

    // 空布局的ViewType
    private val EMPTY_VIEW = 0xff


    override fun getItemCount(): Int {
        // 判断数据是否空，若是没有数据，而且须要显示空布局，就返回1
        return if (adapter.itemCount != 0) {
            adapter.itemCount
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (adapter.itemCount != 0) {
            adapter.getItemViewType(position)
        } else {
            EMPTY_VIEW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == EMPTY_VIEW) {
            // do nothing
        } else {
            adapter.onBindViewHolder(holder as V, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EMPTY_VIEW) {
            // 建立空布局item
            ViewHolder(parent)
        } else {
            adapter.onCreateViewHolder(parent, viewType)
        }
    }

    class ViewHolder(
        parent: ViewGroup,
        private val viewBinding: AdapterEmptyViewBinding = AdapterEmptyViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        init {
            // GlideImageLoader().displayImage(viewBinding.root.context,R.drawable.loading,viewBinding.emptyImage)
        }
    }
}