package com.example.android.cinemax.cinemax;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.io.IOException;


public class SplashScreenView extends AppCompatActivity {
    private SessionManager session;
    private SQLiteDbHelper dbHelper;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreenview);
//        getSupportActionBar().hide();

        dbHelper = new SQLiteDbHelper(this);
        session = new SessionManager(getApplicationContext());

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = ActivityOptions.makeCustomAnimation(getBaseContext(),
                        R.anim.fade_in, R.anim.fade_out).toBundle();
                if (session.isLoggedIn() == true) {
                    Intent lTrue = new Intent(SplashScreenView.this, MainActivity.class);
                    startActivity(lTrue, bundle);

                } else if (session.isLoggedIn() == false) {
                    Intent lFalse = new Intent(SplashScreenView.this, LoginActivity.class);
                    startActivity(lFalse, bundle);
                }
            }
        });
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {

            int[] layouts = {R.layout.sp1, R.layout.sp2, R.layout.sp3};

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LayoutInflater inflater = LayoutInflater.from(SplashScreenView.this);
                ViewGroup layout = (ViewGroup) inflater.inflate(layouts[position], container, false);
                container.addView(layout);
                return layout;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return layouts.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        importDatabase();
    }

    private void importDatabase() {
        try {
            dbHelper.createDatabaseFromImportedSQL();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "importDbFromSQLFile IOException : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
