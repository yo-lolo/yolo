package com.example.myapplication.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
public final class ExecutorManager {
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors
            .newScheduledThreadPool(DEFAULT_THREAD_POOL_SIZE);

    public static void execute(Runnable runnable) {
        SCHEDULED_EXECUTOR_SERVICE.execute(runnable);
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, long delay) {
        return SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
}
