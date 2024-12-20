package com.example.myapplication.base.log

import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.BackupStrategy
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy2
import com.elvishew.xlog.printer.file.clean.CleanStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.ChangelessFileNameGenerator
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.elvishew.xlog.printer.file.naming.FileNameGenerator
import com.example.myapplication.DataManager
import com.example.myapplication.common.Constants
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.log
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2024/1/10 10:11
 * @UpdateUser : Yulu
 * @UpdateDate : 2024/1/10 10:11
 * @UpdateRemark : 更新说明
 */

/**
 * @author  张磊  on  2022/08/19 at 14:00
 * Email: 913305160@qq.com
 */
object XLogUtil {

    /**
     * 默认 XLog 日志保存文件夹的路径： sdcard/Android/data/包名/files/log
     */
    val defaultLogFolderPath: String by lazy {

        val externalFilesDir = DataManager.context.getExternalFilesDir(null)
        File(externalFilesDir, Constants.LogFilesDir).absolutePath
    }

    /**
     * 默认 XCrash日志保存文件夹的路径： sdcard/Android/data/包名/files/crash
     */
    val defaultCrashFolderPath: String by lazy {

        val externalFilesDir = DataManager.context.getExternalFilesDir(null)
        File(externalFilesDir, Constants.CrashFilesDir).absolutePath
    }

    /**
     * 日志文件最大的大小，默认 10M , 超过即触发备份
     */
    private const val logFileMaxSize = 10L * 1024 * 1024

    /**
     * 日志文件最大备份个数
     */
    private const val logFileMaxBackCount = 5

    /**
     * 日志文件最大保留时间期限， 默认一周（7天）
     */
    private const val logFileMaxSaveTime = 7L * 1000 * 60 * 60 * 24

    /**
     * 根据时间戳生成文件名的文件名称生成器
     */
    private val defaultFileNameGenerator = MyDateFileNameGenerator()

    /**
     *  默认的备份策略
     */
    private val defaultBackupStrategy = FileSizeBackupStrategy2(logFileMaxSize, logFileMaxBackCount)


    /**
     *  默认的清理
     */
    private val defaultCleanStrategy = FileLastModifiedCleanStrategy(logFileMaxSaveTime)

    /**
     * 当前日志文件名称生成器
     */
    private var currentFileNameGenerator: FileNameGenerator? = null


    /**
     * 日志文件保存目录
     */
    private var logFolderFile = File(defaultLogFolderPath)

    /**
     * 日志保存文件
     */
    val logFile: File
        get() {
            val logfileName =
                currentFileNameGenerator?.generateFileName(LogLevel.ALL, System.currentTimeMillis())
                    ?: ""
            return File(logFolderFile, logfileName)
        }

    /**
     * 获取日志文件输出器
     *
     * @param  logFolderPath      日志输出的目录
     * @param  fileNameGenerator  日志文件名称生成器
     * @param  backupStrategy     日志文件备份策略
     * @param  cleanStrategy      日志文件清理策略
     *
     */
    fun getFilePrinter(
        logFolderPath: String = defaultLogFolderPath,
        fileNameGenerator: FileNameGenerator = defaultFileNameGenerator,
        backupStrategy: BackupStrategy = defaultBackupStrategy,
        cleanStrategy: CleanStrategy = defaultCleanStrategy
    ): FilePrinter {
        logFolderFile = File(logFolderPath)
        currentFileNameGenerator = fileNameGenerator

        return FilePrinter.Builder(logFolderPath) // 指定保存日志文件的路径
            .fileNameGenerator(fileNameGenerator) // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
            .backupStrategy(backupStrategy) // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
            .cleanStrategy(cleanStrategy) // 指定日志文件清除策略，默认为 NeverCleanStrategy()
            .flattener(ClassicFlattener()) // 指定日志平铺器，默认为 DefaultFlattener
            .build()
    }

    @JvmStatic
    fun getFilePrinter(logFolderPath: String): FilePrinter {
        val fileNameGenerator: FileNameGenerator = defaultFileNameGenerator
        val backupStrategy: BackupStrategy = defaultBackupStrategy
        val cleanStrategy: CleanStrategy = defaultCleanStrategy
        logFolderFile = File(logFolderPath)
        currentFileNameGenerator = fileNameGenerator

        return FilePrinter.Builder(logFolderPath) // 指定保存日志文件的路径
            .fileNameGenerator(fileNameGenerator) // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
            .backupStrategy(backupStrategy) // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
            .cleanStrategy(cleanStrategy) // 指定日志文件清除策略，默认为 NeverCleanStrategy()
            .flattener(ClassicFlattener()) // 指定日志平铺器，默认为 DefaultFlattener
            .build()

    }

    /**
     *  获取固定日志文件名称的日志文件输出器
     */
    fun getFilePrinterByName(
        logFolderPath: String = defaultLogFolderPath,
        fileName: String = "log.txt",
        backupStrategy: BackupStrategy = defaultBackupStrategy,
        cleanStrategy: CleanStrategy = defaultCleanStrategy
    ): FilePrinter {
        return getFilePrinter(
            logFolderPath,
            ChangelessFileNameGenerator(fileName),
            backupStrategy,
            cleanStrategy
        )
    }

    private class MyDateFileNameGenerator : DateFileNameGenerator() {

        var mLocalDateFormat = object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            }
        }

        override fun generateFileName(logLevel: Int, timestamp: Long): String {
            val sdf = mLocalDateFormat.get()!!
            return "${sdf.format(Date(timestamp))}.txt"
        }
    }
}