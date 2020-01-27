package com.example.uzapp.tools

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import java.util.*
import android.graphics.Path

class PaintView(context: Context?, attrs: AttributeSet) : View(context, attrs) {

    var brushSize = 20
    var activeColor = Color.RED
    val DEFAULT_BG_COLOR = Color.WHITE
    val TOUCH_TOLERANCE = 4
    var mX: Float = 0.0F
    var mY: Float = 0.0F
    lateinit var mPath: Path
    lateinit var mPaint: Paint
    var paths: ArrayList<FingerPath> = ArrayList()
    var currentColor: Int = 0
    var backgroundColorPaint: Int = DEFAULT_BG_COLOR
    var strokeWidth: Int = 0
    var emboss: Boolean = false
    var blur: Boolean = false
    lateinit var mEmboss: MaskFilter
    lateinit var mBlur: MaskFilter
    lateinit var mBitmap: Bitmap
    lateinit var mCanvas: Canvas
    var mBitmapPaint: Paint = Paint(Paint.DITHER_FLAG)

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = activeColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff

        mEmboss = EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f)
        mBlur = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
    }

    fun init(metrics: DisplayMetrics) {
        var height = metrics.heightPixels
        var width = metrics.widthPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        currentColor = activeColor
        strokeWidth = brushSize
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun emboss() {
        emboss = true
        blur = false
    }

    fun blur() {
        emboss = false
        blur = true
    }

    fun clear() {
        backgroundColorPaint = DEFAULT_BG_COLOR
        paths.clear()
        normal()
        invalidate()
    }
    fun red() {
        currentColor = Color.RED
    }
    fun green() {
        currentColor = Color.GREEN
    }
    fun blue() {
        currentColor = Color.BLUE
    }
    fun orange() {
        currentColor = Color.YELLOW
    }
    fun black() {
        currentColor = Color.BLACK
    }
    fun size20() {
        strokeWidth = 20
    }
    fun size30() {
        strokeWidth = 30
    }
    fun size40() {
        strokeWidth = 40
    }
    fun size50() {
        strokeWidth = 50
    }
    fun size60() {
        strokeWidth = 60
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()
        mCanvas.drawColor(backgroundColorPaint)

        for (fp: FingerPath in paths) {
            mPaint.color = fp.color
            mPaint.strokeWidth = fp.strokeWidth.toFloat()
            mPaint.maskFilter = null

            if(fp.emboss)
                mPaint.maskFilter = mEmboss
            else if(fp.blur)
                mPaint.maskFilter = mBlur

            mCanvas.drawPath(fp.path!!, mPaint)
        }
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
    }

    fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = FingerPath(currentColor, emboss, blur, strokeWidth, mPath)
        paths.add(fp)

        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun touchUp() {
        mPath.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }
}