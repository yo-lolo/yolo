package com.example.myapplication.shareLifeUser.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseViewModel
import com.example.myapplication.common.AppConfig
import com.example.myapplication.base.cache.MMKVManager
import com.example.myapplication.bean.NewsDataInfo
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.base.database.entity.User
import com.example.myapplication.getTag
import com.example.myapplication.base.log.SpeedyLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.vm
 * @ClassName : MineViewModel
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 10:21
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 10:21
 * @UpdateRemark : 更新说明
 */
class MineViewModel : BaseViewModel() {

    private val userStoreRepository = DataManager.userStoreRepository
    private val newsStoreRepository = DataManager.newsStoreRepository
    private val likeStoreRepository = DataManager.likeStoreRepository
    var user = MutableLiveData<User?>()
    var newsMapData = MutableLiveData<List<Pair<NewsInfo, NewsDataInfo>>>()
    var resultMap = mutableMapOf<NewsInfo, NewsDataInfo>()
    var imagePath = MutableLiveData<String>("")
    val editState = MutableLiveData<Boolean>(false)
    var doModifyType = MutableLiveData(false)

    /**
     * 初始化用户数据，默认为当前用户
     */
    fun initData(number: Long = AppConfig.account) {
        viewModelScope.launch {
            user.value = userStoreRepository.queryUserByNumber(number)
            newsStoreRepository.getNews().filter { it.type == 1 && it.number == number }
                .map { newsInfo ->
                    val user = userStoreRepository.queryUserByNumber(newsInfo.number)
                    val likeCount = likeStoreRepository.getLikesByNewId(newsInfo.id).size
                    val likeState = likeStoreRepository.getLikesMine(AppConfig.account)
                        .any { it.newsId == newsInfo.id }
                    resultMap[newsInfo] = NewsDataInfo(user, likeCount, likeState)
                }
            newsMapData.value = resultMap.toList()
        }
    }

    fun refreshPath(path: String) {
        imagePath.value = path
    }

    /**
     * 更新用户信息
     */
    fun updateUserEdit(neck: String, address: String, sign: String) {
        if (user.value != null) {
            viewModelScope.launch {
                kotlin.runCatching {
                    userStoreRepository.updateUserInfo(
                        AppConfig.account, neck, address, sign, imagePath.value!!
                    )
                }.onFailure {
                    ToastUtils.showShort("保存失败")
                }.onSuccess {
                    ToastUtils.showShort("保存成功")
                    delay(500)
                    editState.value = true
                }
            }
        } else {
            ToastUtils.showShort("请先登录")
        }
    }

    /**
     * 登出
     */
    fun logout() {
        MMKVManager.isLogin = false
    }

    /**
     * 修改密码
     */
    fun doModifyPass(oldPwd: String, newPwd: String, newPwdAgain: String) {
        launchSafe {
            val user = userStoreRepository.queryUserByNumber(AppConfig.account)
            if (oldPwd.isNotEmpty() && newPwd.isNotEmpty() && newPwdAgain.isNotEmpty()) {
                if (newPwd == newPwdAgain && newPwd.length in 6..14) {
                    if (user.pass == oldPwd) {
                        if (oldPwd != newPwd) {
                            kotlin.runCatching {
                                kotlin.runCatching {
                                    userStoreRepository.updatePass(newPwd)
                                }.onFailure {
                                    doModifyType.value = false
                                    ToastUtils.showShort("密码修改失败，请重试")
                                    SpeedyLog.d(getTag(), it.toString())
                                }.onSuccess {
                                    ToastUtils.showShort("密码修改成功")
                                    delay(1000)
                                    doModifyType.value = true
                                }
                            }
                        } else {
                            ToastUtils.showShort("新密码不能与旧密码一致")
                        }
                    } else {
                        ToastUtils.showShort("原密码错误")
                    }
                } else {
                    ToastUtils.showShort("新密码不一致或格式不正确")
                }
            } else {
                ToastUtils.showShort("请完成填写")
            }
        }
    }


}