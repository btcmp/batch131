//package com.example.android.cinemax.cinemax;
//
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutCompat;
//import android.widget.LinearLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DetailFilmActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
//
//    private LinearLayout indicator;
//    private int mDotCount;
//    private LinearLayout[] mDots;
//    private ViewPager viewPager;
//    private List<String> listItem = new ArrayList<>();
//    private FragmentAdapter fragmentAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        indicator = (LinearLayout) findViewById(R.id.indicators);
//        viewPager = (ViewPager) findViewById(R.id.viewPager_itemList);
//
//        setData();
//    }
//
//    private void setData(){
//        listItem.add("Ini adalah fragment 1");
//        listItem.add("Ini adalah fragment 2");
//        listItem.add("Ini adalah fragment 3");
//        listItem.add("Ini adalah fragment 4");
//        listItem.add("Ini adalah fragment 5");
//
//        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), listItem);
//        viewPager.setAdapter(fragmentAdapter);
//        viewPager.setCurrentItem(0);
//        viewPager.setOnPageChangeListener(this);
//        setUiPageViewController();
//
//    }
//
//    private void setUiPageViewController(){
//        mDotCount = fragmentAdapter.getCount();
//        mDots = new LinearLayout[mDotCount];
//
//        for(int i=0; i<mDotCount; i++){
//            mDots[i] = new LinearLayout(this);
//            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//
//            params.setMargins(4,0,4,0);
//            indicator.addView(mDots[i],params);
//            mDots[0].setBackgroundResource(R.drawable.selected_item);
//        }
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        for (int i=0; i<mDotCount; i++){
//            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
//        }
//        mDots[position].setBackgroundResource(R.drawable.selected_item);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//}
