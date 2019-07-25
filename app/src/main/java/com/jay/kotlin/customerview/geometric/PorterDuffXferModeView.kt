package com.jay.kotlin.customerview.geometric

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class PorterDuffXferModeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var bgPaint: Paint
    private var objPaint: Paint
    private var textPaint: Paint
    private val srcColor = Color.parseColor("#5FA7FF")
    private val dstColor = Color.parseColor("#FFD02C")
    private var mBG: Shader? = null

    init {

        bgPaint = Paint()
        bgPaint.color = Color.GRAY
        bgPaint.style = Paint.Style.FILL
        bgPaint.strokeWidth = 2f
        bgPaint.isAntiAlias = true

        objPaint = Paint()
        objPaint.color = srcColor
        objPaint.style = Paint.Style.FILL
        objPaint.isAntiAlias = true

        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 48f
        textPaint.isAntiAlias = true

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // make a ckeckerboard pattern
        val bm = Bitmap.createBitmap(
            intArrayOf(-0x1, -0x333334, -0x333334, -0x1), 2, 2,
            Bitmap.Config.RGB_565
        )
        mBG = BitmapShader(
            bm,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        val m = Matrix()
        m.setScale(6f, 6f)
        mBG?.setLocalMatrix(m)

        bgPaint.setShader(mBG)

    }

    internal fun makeDst(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = dstColor
        c.drawOval(
            RectF(0f, 0f, 0.7f * w, 0.7f * w), p
        )

        return bm
    }

    // create a bitmap with a rect, used for the "src" image
    internal fun makeSrc(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = srcColor
        c.drawRect(0.3f * w, 0.3f * w, 0.9f * w, 0.9f * w, p)
        return bm
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.saveLayerAlpha(RectF(0f, 0f, width.toFloat(), height.toFloat()), 255)
        //draw text
        drawText(canvas, "PorterDuffXferMode", textPaint, 0f, 0f, width)

        //layout
        val boarder = 5
        val w = (width - boarder) / 4
        val h = w
        var anchorX = 0f
        var anchorY = 100f
        val divider = 100f
        val srcSize = 0.6f * w
        val dstSize = srcSize * 0.6f
        val srcX = w / 3
        val srcY = srcX
        val dstX = dstSize
        val dstY = dstX
        val srcBitmap = makeSrc(w, h)
        val dstBitmap = makeDst(w, h)

        //1.1===========================================================================================================
        //draw clear
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Clear", textPaint, anchorX, anchorY + h, w)

        //1.2===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Src", textPaint, anchorX, anchorY + h, w)
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)

        //1.3===========================================================================================================
        //draw destination
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Dst", textPaint, anchorX, anchorY + h, w)
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)


        //1.4===========================================================================================================
        //draw source over
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "SrcOver", textPaint, anchorX, anchorY + h, w)

        var sc: Int = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null) as Int
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OVER))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)


        //2.1===========================================================================================================
        // level 2
        //draw DstOver
        anchorY += h
        anchorY += divider
        anchorX = 0f
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "DstOver", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OVER))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //2.2===========================================================================================================
        //draw source in
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "SrcIn", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //2.3===========================================================================================================
        //draw dstIn
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "DstIn", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_IN))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //2.4===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "SrcOut", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)


        //level3
        //3.1===========================================================================================================
        //draw source
        anchorY += h
        anchorY += divider
        anchorX = 0f
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "DstOut", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //3.2===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "SrcATop", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //3.3===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "DstATop", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_ATOP))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)
        //3.4===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Xor", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.XOR))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //level 4
        //4.1===========================================================================================================
        anchorY += h
        anchorY += divider
        anchorX = 0f
        //draw source
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Darken", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DARKEN))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //4.2===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Lighten", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.LIGHTEN))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)

        //4.3===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Multiply", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.MULTIPLY))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)
        //4.4===========================================================================================================
        //draw source
        anchorX += w
        anchorX += boarder
        canvas?.drawRect(anchorX, anchorY, anchorX + w, anchorY + h, bgPaint)
        drawText(canvas, "Screen", textPaint, anchorX, anchorY + h, w)

        sc = canvas?.saveLayer(anchorX, anchorY, anchorX + w, anchorY + h, null)
        //destination
        canvas?.drawBitmap(dstBitmap, anchorX, anchorY, objPaint)

        objPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SCREEN))
        //source
        canvas?.drawBitmap(srcBitmap, anchorX, anchorY, objPaint)
        objPaint.setXfermode(null)
        canvas?.restoreToCount(sc)


    }

    private fun drawText(canvas: Canvas?, text: String, paint: Paint?, x: Float, y: Float, w: Int) {
        val bounds = Rect()
        paint?.getTextBounds(text, 0, text.length, bounds)
        canvas?.drawText(text, x + (w - bounds.width()) / 2, 20f + bounds.height() + y, paint)
    }


}