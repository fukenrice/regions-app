package com.example.regions_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.regions_app.R
import com.squareup.picasso.Picasso

// Взято с https://stackoverflow.com/a/29991829
class ImagesAdapter(private val mContext: Context, private val images: List<String>) :
    PagerAdapter() {
    private val mLayoutInflater: LayoutInflater =
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as CardView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.image_layout, container, false)
        val imageView = itemView.findViewById<View>(R.id.imageView) as ImageView
        Picasso.get().load(images[position]).into(imageView)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as CardView)
    }
}