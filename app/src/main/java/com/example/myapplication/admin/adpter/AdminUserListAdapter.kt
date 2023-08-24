package com.example.myapplication.admin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutManageUsersItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
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
class AdminUserListAdapter : RecyclerView.Adapter<AdminUserListAdapter.UserListViewHolder>() {

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
        val binding: LayoutManageUsersItemBinding = LayoutManageUsersItemBinding.inflate(parent.context.layoutInflater())
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int, user: User) {
            binding.userNumber.text = "账户：${user.number.toString()}"
            binding.userPass.text = "密码：${user.pass}"
            binding.userNeck.text = "昵称：${user.neck}"
            binding.userSign.text = "个性签名：${user.sign}"
            binding.userAddress.text = "地址：${user.address}"
            binding.userRegiTime.text = "注册时间：${TimeUtil.millis2String(user.time,TimeUtil.dateFormatYMD_CN)}"
            GlideImageLoader().displayLocalFile(user.image, binding.iconImage)
        }
    }

}