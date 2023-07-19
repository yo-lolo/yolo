package com.example.myapplication.util

import com.google.gson.Gson
import com.google.gson.JsonParseException
import org.jetbrains.annotations.Nullable

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.util
 * @ClassName : JsonUtil
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/19 9:10
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/19 9:10
 * @UpdateRemark : 更新说明
 */
object JsonUtil {

    /**
     * 把 JSON 字符串 转换为 单个指定类型的对象
     *
     * @param json
     *            包含了单个对象数据的JSON字符串
     * @param classOfT
     *            指定类型对象的Class
     * @return 指定类型对象
     */
    @Nullable
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        try {
            return Gson().fromJson(json, classOfT)
        } catch (e: JsonParseException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 把 单个指定类型的对象 转换为 JSON 字符串
     * @param src
     * @return
     */
    fun toJson(src: Any?): String {
        return Gson().toJson(src)
    }
}