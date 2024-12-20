package com.example.myapplication.base.log;

import android.app.Application;
import android.util.Log;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.example.myapplication.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : 包名
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : Herb(lhb)
 * @CreateDate : 2023/08/17 17:03
 * @UpdateUser : 更新者
 * @UpdateDate : 2023/08/17 17:03
 * @UpdateRemark : 更新说明
 */
public class SpeedyLog {

    public static final String TAG = Constants.BASE_TAG + SpeedyLog.class.getSimpleName();


    private final SpeedyLogConfig speedyLogConfig;

    private final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    /**
     * xlog 非全局 Logger 构建器
     */
    private final Logger.Builder xLogBuilder;

    private static volatile SpeedyLog INSTANCE = null;


    public static void init(Application context) {
        init(SpeedyLogConfig.Build(context).build());
    }

    public static void init(final SpeedyLogConfig speedyLogConfig) {
        if (INSTANCE == null) {
            synchronized (SpeedyLog.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpeedyLog(speedyLogConfig);
                }
            }
        }
        ExecutorManager.execute(() -> Util.checkLog(speedyLogConfig));
    }

    private SpeedyLog(SpeedyLogConfig speedyLogConfig) {
        this.speedyLogConfig = speedyLogConfig;

        //崩溃监听,清空缓存
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            try {
                if (speedyLogConfig != null) {
                    e("Crash", Util.dumpPhoneInfo(speedyLogConfig.application()), e);
                    flush();
                }
            } catch (Throwable a) {
                a.printStackTrace();
            } finally {//崩溃事件继续流动,系统或其他程序
                defaultUncaughtExceptionHandler.uncaughtException(t, e);
            }
        });


        XLogInitUtil.INSTANCE.init(xLogInitConfig -> Unit.INSTANCE);
        FilePrinter filePrinter = XLogUtil.getFilePrinter(speedyLogConfig.path());
        xLogBuilder = XLog.disableStackTrace()  // SpeedyLog 有自己的堆栈管理，这里就直接关闭
                .printers(new AndroidPrinter(), filePrinter);
    }

    public static void i(String log) {
        i(TAG, log);
    }

    public static void i(String tag, String log) {
        INSTANCE.log(Level.INFO, tag, log);
    }

    public static void i(String tag, String format, Object... args) {
        //INSTANCE.log(Level.INFO, tag, log);
        String log = String.format(format, args);
        i(tag, log);
    }

    public static void i(int methodCount, String log) {
        i(methodCount, TAG, log);
    }

    public static void i(int methodCount, String tag, String log) {
        INSTANCE.log(methodCount, Level.INFO, tag, log);
    }

    public static void i(int methodCount, String tag, String format, Object... args) {
        //INSTANCE.log(Level.INFO, tag, log);
        String log = String.format(format, args);
        i(methodCount, tag, log);
    }

    public static void e(String log) {
        e(TAG, log);
    }

    public static void e(String tag, String log) {
        INSTANCE.log(Level.ERROR, tag, log);
    }

    public static void e(Throwable tr) {
        e("", tr);
    }

    public static void e(String log, Throwable tr) {
        e(TAG, log, tr);
    }

    public static void e(String tag, String log, Throwable tr) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(bos);
        tr.printStackTrace(p);
        INSTANCE.log(Level.ERROR, tag, log + "\n" + bos);
    }

    public static void e(int methodCount, String log) {
        e(methodCount, TAG, log);
    }

    public static void e(int methodCount, String tag, String log) {
        INSTANCE.log(methodCount, Level.ERROR, tag, log);
    }

    public static void e(int methodCount, Throwable tr) {
        e(methodCount, "", tr);
    }

    public static void e(int methodCount, String log, Throwable tr) {
        e(methodCount, TAG, log, tr);
    }

    public static void e(int methodCount, String tag, String log, Throwable tr) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(bos);
        tr.printStackTrace(p);
        INSTANCE.log(methodCount, Level.ERROR, tag, log + "\n" + bos);
    }

    public static void w(String log) {
        w(TAG, log);
    }

    public static void w(String tag, String log) {
        INSTANCE.log(Level.WARING, tag, log);
    }

    public static void w(int methodCount, String log) {
        w(methodCount, TAG, log);
    }

    public static void w(int methodCount, String tag, String log) {
        INSTANCE.log(methodCount, Level.WARING, tag, log);
    }

    public static void d(String log) {
        d(TAG, log);
    }

    public static void d(String tag, String log) {
        INSTANCE.log(Level.DEBUG, tag, log);
    }

    public static void d(int methodCount, String log) {
        d(methodCount, TAG, log);
    }

    public static void d(int methodCount, String tag, String log) {
        INSTANCE.log(methodCount, Level.DEBUG, tag, log);
    }

    public static void v(String log) {
        v(TAG, log);
    }

    public static void v(String tag, String log) {
        INSTANCE.log(Level.VERBOSE, tag, log);
    }

    public static void v(int methodCount, String log) {
        v(methodCount, TAG, log);
    }

    public static void v(int methodCount, String tag, String log) {
        INSTANCE.log(methodCount, Level.VERBOSE, tag, log);
    }

    //立即持久化日志,阻塞
    public static void flush() {
        INSTANCE.flushAll();
    }

    //获取日记路径
    public static String getPath() {
        return INSTANCE.speedyLogConfig == null ? "" : INSTANCE.speedyLogConfig.path();
    }

    private final Map<String, LogInfo> map = new ConcurrentHashMap<>();

    private void flushAll() {
        for (Map.Entry<String, LogInfo> e : map.entrySet()) e.getValue().flush();
    }

    private String log(Level info, String tag, String log) {
        return log(speedyLogConfig.methodCount(), info, tag, log);
    }

    private String log(int methodCount, Level level, String tag, String log) {
        if (speedyLogConfig == null) {
            Log.e(TAG, "请先初始化SpeedyLog.init()");
            return "";
        }

        /*String timeSSS = Util.formatTime();
        String date = timeSSS.substring(0, 10);*/
        String thread = Thread.currentThread().getName();
        String stack = "";

        if (methodCount > 0) {
            stack = Util.getStack(methodCount);
        }

        Logger xlogger = xLogBuilder.tag(tag).build();

        String msg = log + stack;

        if (speedyLogConfig.debug()) {
            switch (level) {
                case ERROR:
                    // Log.e(tag, msg);
                    xlogger.e(msg);
                    break;
                case INFO:
                    // Log.i(tag, msg);
                    xlogger.i(msg);
                    break;
                case DEBUG:
                    // Log.d(tag, msg);
                    xlogger.d(msg);
                    break;
                case WARING:
                    // Log.w(tag, msg);
                    xlogger.w(msg);
                    break;
                case VERBOSE:
                    // Log.v(tag, log + stack);
                    xlogger.v(msg);
                    break;
            }
        }
        return stack;
    }

    private static class LogInfo {

        private final SpeedyLogConfig speedyLogConfig;
        private final String folder;
        private final String fileName;

        private ByteArrayOutputStream buff = new ByteArrayOutputStream();//日记写入缓存
        private volatile long lastWriteTime = System.currentTimeMillis();//最后一次写入时间

        private volatile ScheduledFuture scheduledFuture;//可见性
        private final ReentrantLock reentrantLock = new ReentrantLock();

        LogInfo(SpeedyLogConfig speedyLogConfig, String fileName) {
            this.speedyLogConfig = speedyLogConfig;
            this.folder = speedyLogConfig.path();
            this.fileName = fileName;
        }

        //此方法不阻塞
        void apply(final String log) {
            //优化锁机制,解决以下2个问题
            // 1.直接无脑开线程费性能
            // 2.直接写入buff有可能正在flush操作而需要加锁等待阻塞
            if (reentrantLock.tryLock()) {
                //拿到锁表示没有flush操作
                try {
                    write(log);
                } finally {
                    reentrantLock.unlock();
                }
            } else {
                //正在flush/write操作,开线程写入buff
                ExecutorManager.execute(new Runnable() {
                    @Override
                    public void run() {
                        write(log);
                    }
                });
            }
        }

        //日记写入缓存,线程安全,防止多线程日记乱了
        void write(String log) {
            try {
                reentrantLock.lock();
                try {
                    buff.write(log.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cancel();
                long space = System.currentTimeMillis() - lastWriteTime;
                if (space > speedyLogConfig.delay() || buff.size() > speedyLogConfig.buffSize()) {
//                    ExecutorManager.execute(flushRun);
                    flushRun.run();
                } else {
                    scheduledFuture = ExecutorManager.schedule(flushRun, speedyLogConfig.delay() - space);
                }
            } finally {
                reentrantLock.unlock();
            }
        }

        //取消上一个任务
        private void cancel() {
            ScheduledFuture temp = scheduledFuture;
            if (temp != null && !temp.isCancelled() && !temp.isDone())
                temp.cancel(false);
            scheduledFuture = null;
        }

        private final Runnable flushRun = new Runnable() {
            @Override
            public void run() {
                flush();
                lastWriteTime = System.currentTimeMillis();
            }
        };

        //日记持久化,加锁,阻塞,防止线程安全问题重复写入
        void flush() {
            try {
                reentrantLock.lock();
                long temp = System.currentTimeMillis();
                if (buff.size() > 0 && Util.writeData(speedyLogConfig.writeData(), folder, fileName, buff.toByteArray())) {
//                    if (speedyLogConfig.debug()) {
                    long use = System.currentTimeMillis() - temp;
                    Log.d(TAG, "flush->logName:" + fileName + " ,len:" + buff.size() + " ,useTime:" + use);
//                    }
                    if (buff.size() > speedyLogConfig.buffSize())//如果缓存过大重置,比如写入一个大log(MB),之后buff就一直很大了
                        buff = new ByteArrayOutputStream();
                    else
                        buff.reset();
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * 日志拦截器数据过滤
     * [手机号码] 前三位，后四位，其他隐藏<例子:138****1234>
     * [ICCID] 前四位，后四位，其他隐藏<例子:8986***********0217>
     */
    public static String sensitiveLoggingInterceptor(String logging) {
        try {
            if (logging == null || "".equals(logging)) {
                return logging;
            }
            logging = logging.replaceAll("(1\\d{2})\\d{4}(\\d{4}\\b|\\D])", "$1****$2");//手机号脱敏
            logging = logging.replaceAll("(\\d{4})\\d{12}(\\d{3})", "$1************$2");//ICCID脱敏
            return logging;
        } catch (Exception e) {
            e(TAG, "日志打印脱敏异常：" + e.getMessage());
            return logging;
        }
    }
}
