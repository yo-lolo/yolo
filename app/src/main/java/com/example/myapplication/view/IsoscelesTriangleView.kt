package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R
import java.lang.Float.min
import java.lang.Math.sqrt

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.view
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/9/19 11:20
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/9/19 11:20
 * @UpdateRemark : 更新说明
 */
/**
 * 等腰直角三角尺
 */
class IsoscelesTriangleView @JvmOverloads constructor(
    private val context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    //刻度跟字
    private val drawPaint = Paint()

    //背景
    private val bgPaint = Paint()

    //外三角Path
    private val outPath = Path()

    //内三角Path
    private val inPath = Path()

    //刻度的宽度
    private var linesWidth = 0f

    //刻度的颜色
    private var linesColor = Color.BLACK

    //值的文本颜色
    private var valuesTextColor = Color.BLACK

    //值的文本大小
    private var valuesTextSize = 0f

    private var textHeight = 0

    //相邻两个值的跳跃间隔
    private var valuesInterval = 0

    //每两个值之间的间隔数,也指多少个最小单位，比如0cm到1cm有10个最小单位1mm
    private var intervalsBetweenValues = 0

    //间隔，即两条刻度间的距离
    private var interval = 0f

    //最短刻度长度为基准
    private var minLength = 0f

    private var length = 0f

    init {
        val array = context!!.obtainStyledAttributes(attrs, R.styleable.IsoscelesTriangle)
        interval = array.getDimensionPixelSize(R.styleable.IsoscelesTriangle_interval, 1).toFloat()
        intervalsBetweenValues =
            array.getInt(R.styleable.IsoscelesTriangle_intervalsBetweenValues, 10)
        valuesInterval = array.getInt(R.styleable.IsoscelesTriangle_valuesInterval, 1)
        valuesTextSize =
            array.getDimensionPixelSize(R.styleable.IsoscelesTriangle_valuesTextSize, 4).toFloat()
        valuesTextColor = array.getColor(R.styleable.IsoscelesTriangle_valuesTextColor, Color.BLACK)
        linesWidth =
            array.getDimensionPixelSize(R.styleable.IsoscelesTriangle_linesWidth, 1).toFloat()
        linesColor = array.getColor(R.styleable.IsoscelesTriangle_linesColor, Color.BLACK)
        array.recycle()

        initView()
    }

    private fun initView() {
        drawPaint.color = Color.BLACK
        bgPaint.color = Color.WHITE
        bgPaint.style = Paint.Style.FILL

        drawPaint.textSize = valuesTextSize
        //文本高度
        val fm = drawPaint.fontMetrics
        textHeight = (fm.bottom - fm.top).toInt()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        //三角形外边长
        length = min(viewWidth, viewHeight)
        //等腰外三角形
        outPath.reset()
        outPath.moveTo(0f, 0f)
        outPath.lineTo(length, 0f)
        outPath.lineTo(0f, length)
        outPath.close()

        //等腰内三角形
        inPath.reset()
        val inLength = length / 3
        //边距
        val padding = (length - inLength) / 2f
        val x = padding / 2f
        val y = x
        minLength = y / 14f
        inPath.moveTo(x, y)
        inPath.lineTo(length - padding, y)
        inPath.lineTo(x, length - padding)
        inPath.close()
        canvas?.apply {
            save()
            clipOutPath(inPath)
            drawPath(outPath, bgPaint)
            restore()
        }
        //============刻度========
        drawPaint.color = linesColor
        drawPaint.strokeWidth = linesWidth
        drawLineAndText(canvas)
    }

    private fun drawLineAndText(canvas: Canvas?) {
        //根据view的宽度去决定长度，取Int整数
        var toValue = ((length) / (interval * intervalsBetweenValues) - 1).toInt()
        var offset = (length - (toValue * interval * intervalsBetweenValues)) / 2f
        var width = offset
        var position = 0

        while (true) {
            //画剩下的刻度
            if (position > toValue / valuesInterval * intervalsBetweenValues) break
            if (width > length + drawPaint.measureText("10000")) break
            if (position % (intervalsBetweenValues / 2) == 0) {
                if (position % intervalsBetweenValues == 0) {
                    //===============长线============
                    canvas?.apply {
                        //横
                        drawLine(width, 0f, width, minLength * 2f, drawPaint)
                        //纵
                        drawLine(0f, width, minLength * 2, width, drawPaint)

                        drawPaint.color = valuesTextColor
                        //横
                        val valueString =
                            (position / intervalsBetweenValues * valuesInterval).toString()
                        drawText(
                            valueString,
                            width - drawPaint.measureText(valueString) / 2,
                            minLength * 2.5f + textHeight / 2,
                            drawPaint
                        )
                        //纵
                        // 计算旋转后的坐标
                        val centerX = minLength * 2.5f + textHeight / 2
                        val centerY = width
                        val textWidth: Float =
                            drawPaint.measureText((toValue - valueString.toInt()).toString())
                        val textHeight: Float = drawPaint.descent() - drawPaint.ascent()
                        val textX = centerX - textWidth / 2
                        val textY = centerY + textHeight / 2
                        // 绘制旋转的文本
                        save()
                        rotate(90f, centerX, centerY)
                        drawText(
                            (toValue - valueString.toInt()).toString(),
                            textX,
                            textY,
                            drawPaint
                        )
                        restore()
                        drawPaint.color = linesColor
                    }
                } else {
                    //============中线==============
                    //横
                    canvas?.drawLine(width, 0f, width, minLength * 1.5f, drawPaint)
                    //纵
                    canvas?.drawLine(0f, width, minLength * 1.5f, width, drawPaint)
                }
            } else {
                //============短线===========
                //横
                canvas?.drawLine(width, 0f, width, minLength, drawPaint)
                //纵
                canvas?.drawLine(0f, width, minLength, width, drawPaint)
            }
            width += interval
            position++
        }

        //============斜边=============

        val sqrt2 = 1.414f
        toValue = sqrt((2 * toValue * toValue.toDouble())).toInt()
        val hypotenuse = length * sqrt2
        offset = (hypotenuse - toValue * interval * intervalsBetweenValues) / 2
        width = offset
        position = 0
        var lineLength: Float
        while (true) {
            if (position > toValue / valuesInterval * intervalsBetweenValues) break
            if (width > length * sqrt2 + drawPaint.measureText("10000")) break
            canvas?.apply {
                if (position % (intervalsBetweenValues / 2) == 0) {
                    if (position % intervalsBetweenValues == 0) {
                        //===============长线===========
                        lineLength = length - minLength * 2f
                        //=======画字=====
                        drawPaint.color = valuesTextColor
                        val valueString = (toValue -
                                (position / intervalsBetweenValues * valuesInterval)).toString()

                        // 计算旋转后的坐标
                        val centerX = width
                        val centerY = length - minLength * 2.5f - textHeight / 2
                        val textWidth: Float = drawPaint.measureText(valueString)
                        val textHeight: Float = drawPaint.descent() - drawPaint.ascent()
                        val textX = centerX - textWidth / 2
                        val textY = centerY + textHeight / 2
                        // 绘制旋转的文本
                        save()
                        rotate(-45f, 0f, length)
                        drawText(
                            valueString,
                            textX,
                            textY,
                            drawPaint
                        )
                        restore()
                        drawPaint.color = linesColor

                    } else {
                        //============中线==============
                        lineLength = length - minLength * 1.5f
                    }
                } else {
                    //============短线===========
                    lineLength = length - minLength
                }

                save()
                rotate(-45f, 0f, length)
                drawLine(width, lineLength, width, length, drawPaint)
                restore()

                width += interval
                position++
            }
        }
    }
}
