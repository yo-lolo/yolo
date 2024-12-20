package com.example.myapplication.base.log;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : 包名
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : Herb(lhb)
 * @CreateDate : 2023/08/17 17:13
 * @UpdateUser : 更新者
 * @UpdateDate : 2023/08/17 17:13
 * @UpdateRemark : 更新说明
 */
public interface WriteData {
    /**
     * return: false会继续执行写入本地, true不继续执行
     */
    boolean writeData(String folder, String fileName, byte[] bytes) throws Exception;
}
