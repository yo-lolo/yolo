package com.example.yolo_sdk

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.yolo_sdk
 * @Description : leetcode
 * @Author : Yulu
 * @CreateDate : 2024/4/7 17:28
 * @UpdateUser : Yulu
 * @UpdateDate : 2024/4/7 17:28
 * @UpdateRemark : 更新说明
 */
class Solution {


    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        if (n != 0) {
            for (i in m until m + n) {
                nums1[i] = nums2[i - m]
            }
            return nums1.sort()
        }
        return nums1.sort()
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {
        return if (nums.contains(`val`)) {
            val nums2 = nums.toMutableList()
            nums2.removeIf { it == `val` }
            println(nums2.toString())
            nums2.size
        } else
            nums.size
    }

    fun removeDuplicates(nums: IntArray): Int {
        val first = 0
        val end = nums.size - 1
        return nums.toSet().size
    }
}

fun main(args: Array<String>) {
    val mSolution = Solution()
    val ss = mSolution.merge(intArrayOf(1, 2, 3, 0, 0, 0), 3, intArrayOf(2, 5, 6), 3)
    val solution2 = mSolution.removeElement(intArrayOf(3, 2, 2, 3), 3)
    mSolution.removeDuplicates(intArrayOf(1,2,2,3,4,4,5,0,0,0))
    println(solution2)
}