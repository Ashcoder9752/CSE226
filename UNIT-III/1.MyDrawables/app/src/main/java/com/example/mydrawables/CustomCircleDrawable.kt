package com.example.mydrawables

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable

// Custom Drawable class that draws a circle with a radial gradient
class CustomCircleDrawable : Drawable() {

    // Create a Paint object with anti-aliasing enabled to smooth the edges of the circle
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Create a RadialGradient shader for the paint. It will create a smooth color transition
    // starting with red (0xFFFF0000), transitioning to green (0xFF00FF00), and ending with blue (0xFF0000FF).
    // The gradient is applied to a circle with radius 100 at the center (100f, 100f).
    private val gradient = RadialGradient(
        100f, 100f, 100f,
        intArrayOf(0xFFFF0000.toInt(), 0xFF00FF00.toInt(), 0xFF0000FF.toInt()),
        floatArrayOf(0f, 0.5f, 1f),
        Shader.TileMode.CLAMP
    )

    // Initialize the paint object to use the RadialGradient as its shader
    init {
        paint.shader = gradient
    }

    // The core method that defines how the drawable is rendered. In this case, we draw a circle
    // using the paint object, which has the RadialGradient shader applied.
    override fun draw(canvas: Canvas) {
        canvas.drawCircle(100f, 100f, 100f, paint)
    }

    // Sets the alpha (transparency) of the drawable. This controls how transparent or opaque the circle is.
    // The alpha value ranges from 0 (completely transparent) to 255 (completely opaque).
    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    // Applies a color filter to the drawable. This allows you to modify the colors of the drawable
    // at runtime by applying different filters (e.g., make it grayscale, add a tint, etc.).
    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    // Deprecated method to define the drawable's opacity level.
    // This returns PixelFormat.TRANSLUCENT, meaning the drawable may have varying transparency levels.
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT // This drawable is translucent, allowing partial transparency

    // Defines the intrinsic (default) width of the drawable.
    // This is used to set the default width when the drawable is applied to a view.
    override fun getIntrinsicWidth(): Int = 200  // Default width for the drawable is 200 pixels

    // Defines the intrinsic (default) height of the drawable.
    // This is used to set the default height when the drawable is applied to a view.
    override fun getIntrinsicHeight(): Int = 200  // Default height for the drawable is 200 pixels
}