package com.jay.kotlin.customerview.binding

import android.graphics.drawable.Drawable

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Binding adapters.
 *
 *
 */
internal object BindingAdapterHelper {

    /**
     * image view need to bind imageUrl and placeholder simultaneous if use data binding.
     * this method invoke automatically.
     *
     * @param imgView
     * @param url
     * @param placeholder
     */
    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder", "needGone")
    fun loadImage(
        imgView: ImageView,
        url: String?,
        placeholder: Drawable,
        needGone: Boolean = false
    ) {
        if (TextUtils.isEmpty(url)) {
            if (needGone) {
                imgView.visibility = View.GONE
            } else {
                imgView.visibility = View.VISIBLE
                imgView.setImageDrawable(placeholder)
            }
            return
        }
        imgView.visibility = View.VISIBLE
        val requestOptions = RequestOptions().placeholder(placeholder).error(placeholder)

        Glide.with(imgView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(imgView)
    }

    @JvmStatic
    @BindingAdapter("rawUrl", "placeholder")
    fun loadImageFromRaw(imgView: ImageView, url: Int, placeholder: Drawable) {
        val requestOptions = RequestOptions().placeholder(placeholder).error(placeholder)
        Glide.with(imgView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(imgView)
    }

    @JvmStatic
    @BindingAdapter("imageFilePath")
    fun loadImageFromLocal(imgView: ImageView, filePath: String) {

        if (TextUtils.isEmpty(filePath)) {
            imgView.visibility = View.GONE
            return
        }
        imgView.visibility = View.VISIBLE
        Glide.with(imgView.context)
            .load(filePath)
            //no ram cache
            .apply(RequestOptions.skipMemoryCacheOf(true))
            //no disk cache
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .apply(RequestOptions.centerCropTransform())
            .into(imgView)
    }

    @JvmStatic
    @BindingAdapter("videoUrl", "placeholder", "needGone")
    fun loadVideoThumbnail(
        imgView: ImageView,
        url: String,
        placeholder: Drawable,
        needGone: Boolean = false
    ) {
        if (TextUtils.isEmpty(url)) {
            if (needGone) {
                imgView.visibility = View.GONE
            } else {
                imgView.visibility = View.VISIBLE
                imgView.setImageDrawable(placeholder)
            }
            return
        }
        imgView.visibility = View.VISIBLE
        val requestOptions =
            RequestOptions().frame(1000000).centerCrop().placeholder(placeholder).error(placeholder)

        Glide.with(imgView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(imgView)
    }
}// Private Constructor to hide the implicit one
