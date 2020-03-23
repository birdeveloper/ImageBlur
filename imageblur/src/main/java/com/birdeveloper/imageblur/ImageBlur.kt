package com.birdeveloper.imageblur

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import java.lang.ref.WeakReference

/*
Created by @birdeveloper 3/24/2020 12:27 AM
 */
class ImageBlur internal constructor(private val context: Context) {
    private var image: Bitmap? = null
    private var intensity = 08f
    private val MAX_RADIUS = 25f
    private val MIN_RADIUS = 0f
    private var asyncTask = false
    private fun blur(): Bitmap? {
        if (image == null) {
            return image
        }
        val width = Math.round(image!!.width.toFloat())
        val height = Math.round(image!!.height.toFloat())
        val input = Bitmap.createScaledBitmap(image!!, width, height, false)
        val output = Bitmap.createBitmap(input)
        val rs = RenderScript.create(context)
        val intrinsicBlur =
            ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val inputallocation = Allocation.createFromBitmap(rs, input)
        val outputallocation = Allocation.createFromBitmap(rs, output)
        intrinsicBlur.setRadius(intensity)
        intrinsicBlur.setInput(inputallocation)
        intrinsicBlur.forEach(outputallocation)
        outputallocation.copyTo(output)
        return output
    }

    fun load(bitmap: Bitmap?): ImageBlur {
        image = bitmap
        return this
    }

    fun load(res: Int): ImageBlur {
        image = BitmapFactory.decodeResource(context.resources, res)
        return this
    }

    fun blur(intensity: Float): ImageBlur {
        if (intensity < MAX_RADIUS && intensity > 0) this.intensity =
            intensity else this.intensity = MAX_RADIUS
        return this
    }

    fun asyncTask(sync: Boolean): ImageBlur {
        this.asyncTask = sync
        return this
    }

    fun into(imageView: ImageView) {
        if (asyncTask) {
            asyncTaskBlurImage(imageView).execute()
        } else {
            try {
                imageView.setImageBitmap(blur())
            } catch (ex: NullPointerException) {
                ex.printStackTrace()
            }
        }
    }
    val ımageBlur: Bitmap?
        get() = blur()
    @SuppressLint("StaticFieldLeak")
    private inner class asyncTaskBlurImage(image: ImageView) :
        AsyncTask<Void?, Void?, Bitmap?>() {
        private val weakReference: WeakReference<ImageView> = WeakReference(image)

        override fun onPostExecute(bitmap: Bitmap?) {
            val imageView = weakReference.get()
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap)
            }
        }

        override fun doInBackground(vararg params: Void?): Bitmap? {
            return blur()
        }
    }

    companion object {
        private const val BITMAP_SCALE = 0.3f
        private const val BLUR_RADIUS = 7f
        fun withContext(context: Context): ImageBlur {
            return ImageBlur(context)
        }
    }

}