package com.example.myapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.provider
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/9/19 14:42
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/9/19 14:42
 * @UpdateRemark : 更新说明
 */
class BookProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        Log.e("yolo","onCreate")
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        Log.e("yolo","query")
        return null
    }

    override fun getType(p0: Uri): String? {
        Log.e("yolo","getType")
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        Log.e("yolo","insert")
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        Log.e("yolo","delete")
        return 1
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        Log.e("yolo","update")
        return 0
    }
}