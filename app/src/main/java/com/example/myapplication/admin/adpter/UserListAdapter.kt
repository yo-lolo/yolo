package com.example.myapplication.admin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutOnceListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.adimin.adpter
 * @ClassName : UserListAdater
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/25 16:19
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/25 16:19
 * @UpdateRemark : 更新说明
 */
class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    var list: List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.setData(position, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class UserListViewHolder(
        val parent: ViewGroup,
        val binding: LayoutOnceListItemBinding = LayoutOnceListItemBinding.inflate(parent.context.layoutInflater())
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int, user: User) {
            binding.onceName.text = user.number.toString()
            binding.descText.text = user.pass
        }
    }

}