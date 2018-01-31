package com.example.android.cinemax.cinemax;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewPagerAdapter extends PagerAdapter{
    private Context mContext;
    private int[] mResource;

    public ViewPagerAdapter(Context mContext, int[] mResource) {
        this.mContext = mContext;
        this.mResource = mResource;
    }

    @Override
    public int getCount() {
        return mResource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_pager_item);
        imageView.setImageResource(mResource[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}