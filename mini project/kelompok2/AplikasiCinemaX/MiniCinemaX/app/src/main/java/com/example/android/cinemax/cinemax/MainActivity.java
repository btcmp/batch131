package com.example.android.cinemax.cinemax;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    String[] img_src, nama_film,name,email;
    int[]id_user;
    int resId;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        String all= session.email();
        Toast.makeText(getApplicationContext(),all,Toast.LENGTH_LONG).show();
        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);
        Cursor cursor2=queryHelper.sesi(all+"");
         id_user=new int[cursor2.getCount()];
         name= new String[cursor2.getCount()];
         email= new String[cursor2.getCount()];
        cursor2.moveToFirst();
        for(int n=0;n<cursor2.getCount();n++){
            id_user[n]=cursor2.getInt(0);
            name[n]=cursor2.getString(1);
            email[n]=cursor2.getString(2);
        }
        cursor2.close();

        Cursor cursor = queryHelper.readListGambarAll();

        img_src = new String[cursor.getCount()];
        nama_film = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            img_src[n] = cursor.getString(0);
            nama_film[n] = cursor.getString(1);
        }
        cursor.close();

        Button button = (Button) findViewById(R.id.buttonBioskop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListBioskopActivity.class);
                startActivity(intent);
            }
        });

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailFilmXActivity.class);

                resId = getResources().getIdentifier(img_src[position],"drawable",getPackageName());
                intent.putExtra("id_1", nama_film[position]);
                intent.putExtra("id_2", resId);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView names = header.findViewById(R.id.textNama);
        TextView emails = header.findViewById(R.id.textEmail);
        names.setText(name[0]);
        emails.setText(email[0]);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
           moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            session.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, ListTransaksiActivity.class);
            startActivity(intent);
            // Handle the camera action
        }
        if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("id_user", id_user[0]);
            intent.putExtra("email",email[0]);
            intent.putExtra("name",name[0]);
            startActivity(intent);
            // Handle the camera action
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return img_src.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {

                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(250, 350));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            resId = getResources().getIdentifier(img_src[position],"drawable",getPackageName());

            imageView.setImageResource(resId);
            return imageView;
        }

}
}
