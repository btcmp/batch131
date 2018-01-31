//package com.example.android.cinemax.cinemax;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//
///**
// * Created by Rhandy on 1/12/2018.
// */
//
//
//public class ImageAdapter extends BaseAdapter {
//    private Context mContext;
//
//    public ImageAdapter(Context c) {
//        mContext = c;
//    }
//
//    public int getCount() {
//        return mThumbIds.length;
//    }
//
//    public Object getItem(int position) {
//        return null;
//    }
//
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(350, 500));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
//    }
//
//    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.deadpool1, R.drawable.doraemon2,
//            R.drawable.insidious2, R.drawable.mazerunner2,
//            R.drawable.theraid2, R.drawable.aac2,
//    };
//}