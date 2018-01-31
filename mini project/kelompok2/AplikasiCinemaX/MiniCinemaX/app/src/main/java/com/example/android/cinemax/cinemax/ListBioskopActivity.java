package com.example.android.cinemax.cinemax;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.util.List;

public class ListBioskopActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    private Context mContext;
    private ListView listView;
    private Cursor cursor;
    String[]  nama_bioskop, alamat, img_src;
    int resId;
//    int[] img_src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bioskop);

        listView = (ListView) findViewById(R.id.listViewBioskop);

        mContext = this;

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);

//        tampilListBioskop();

        Cursor cursor = queryHelper.readListBioskop();
        String tampiltext = "";
        String[] opsi = new String[cursor.getCount()];

        nama_bioskop = new String[cursor.getCount()];
        alamat = new String[cursor.getCount()];
        img_src = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++){
            cursor.moveToPosition(n);
            img_src[n] = cursor.getString(1);
            nama_bioskop[n] = cursor.getString(2);
            alamat[n]= cursor.getString(7);
        }
        cursor.close();

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(onListClick);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return nama_bioskop.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int n, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.costum_listview, null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_image);
            TextView textNama = (TextView) convertView.findViewById(R.id.textNama);
            TextView textAlamat = (TextView) convertView.findViewById(R.id.textAlamat);

            String pathnya = "com.example.android.cinemax.cinemax:drawable/bip.jpg";
//            int imageid = getResources().getIdentifier(img_src[n], "com.example.android.cinemax.cinemax:drawable/", getApplicationContext().getPackageName());
//            imageView.setImageResource(img_src[n]);

            resId = getResources().getIdentifier(img_src[n],"drawable",getPackageName());

            imageView.setImageResource(resId);
            textNama.setText(nama_bioskop[n]);
            textAlamat.setText(alamat[n]);

            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(ListBioskopActivity.this, DetailBioskopActivity.class);

            intent.putExtra("id_1", resId);
            intent.putExtra("id_2", nama_bioskop[i]);
            intent.putExtra("id_3", alamat[i]);

            startActivity(intent);
        }
    };
}
