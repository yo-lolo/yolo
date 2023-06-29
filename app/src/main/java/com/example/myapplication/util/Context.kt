package com.example.myapplication.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.util
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/2/9 10:01
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/2/9 10:01
 * @UpdateRemark : 更新说明
 */
public fun Context.toast(str: String) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

public fun Context.toast(strId: Int) = Toast.makeText(this, strId, Toast.LENGTH_SHORT).show()

public fun Context.layoutInflater(): LayoutInflater = LayoutInflater.from(this)