package com.example.android.cinemax.cinemax;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.util.ArrayList;
import java.util.List;

public class DetailBioskopActivity extends AppCompatActivity {
    ListView listView;
    private Cursor cursor;
    TextView textView;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    String[] nama_film, jam, harga, nama_bioskop, himpunanJam, img_src;
    int[] id_bioskop, id_jadwal, id_film;

    int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bioskop);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        Intent intent=(Intent) getIntent();

        String anama_bioskop = intent.getExtras().getString("id_2");

        Cursor cursor = queryHelper.readListTayangFilm(anama_bioskop+"");
        Cursor cursor2 = queryHelper.readListGambar(anama_bioskop+"");

        img_src = new String[cursor2.getCount()];
        cursor2.moveToFirst();
        for (int n=0; n<cursor2.getCount(); n++){
            cursor2.moveToPosition(n);
            img_src[n] = cursor2.getString(0);
        }

        String tampiltext = "";
        String[] opsi = new String[cursor.getCount()];

        nama_film = new String[cursor.getCount()];
        harga = new String[cursor.getCount()];
        jam = new String[cursor.getCount()];
        nama_bioskop = new String[cursor.getCount()];
        id_bioskop = new int[cursor.getCount()];
        id_jadwal = new  int[cursor.getCount()];
        id_film = new  int[cursor.getCount()];
        himpunanJam = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            nama_film[n] = cursor.getString(0);
            jam[n] = cursor.getString(1);
            harga[n] = cursor.getString(2);
            nama_bioskop[n] = cursor.getString(3);
            id_bioskop[n] = cursor.getInt(4);
            id_jadwal[n] = cursor.getInt(5);
            id_film[n] = cursor.getInt(6);
        }

        a=0;
        b=0;
        himpunanJam[b]="";
        for (int i=0; i<nama_film.length; i++) {
            if(i==0){
                nama_film[a] = nama_film[i];
                harga[a] = harga[i];
                nama_bioskop[a] = nama_bioskop[i];
                id_bioskop[a] = id_bioskop[i];
                id_jadwal[a] = id_jadwal[i];
                id_film[a] = id_film[i];
                himpunanJam[b] += jam[i]+", ";
                a++;
            }
            else{
                if(id_film[i] == id_film[i-1]){
                    himpunanJam[b] += jam[i]+", ";
                }
                else{
                    b++;
                    himpunanJam[b]="";
                    nama_film[a] = nama_film[i];
                    harga[a] = harga[i];
                    nama_bioskop[a] = nama_bioskop[i];
                    id_bioskop[a] = id_bioskop[i];
                    id_jadwal[a] = id_jadwal[i];
                    id_film[a] = id_film[i];
                    himpunanJam[b] += jam[i]+", ";
                    a++;
                }
            }
        }

        cursor.close();

        ImageView imageDetail = findViewById(R.id.imageDetailBioskop);
        TextView nameDetail = findViewById(R.id.textNamaDetail);
        TextView alamatDetail = findViewById(R.id.textAlamatDetail);
        listView=findViewById(R.id.listViewDetailFilmBioskop);

        int imageDet = intent.getExtras().getInt("id_1");
        imageDetail.setImageResource(imageDet);

        String name = intent.getExtras().getString("id_2");
        nameDetail.setText(name);

        String alamat = intent.getExtras().getString("id_3");
        alamatDetail.setText(alamat);

        DetailBioskopActivity.CustomAdapter customAdapter = new DetailBioskopActivity.CustomAdapter();

        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(onListClick);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return a;
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
            convertView = getLayoutInflater().inflate(R.layout.costum_listviewdetailfilmbioskop, null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_image);
            TextView textNama = (TextView) convertView.findViewById(R.id.textJudul);
            TextView textJudul = (TextView) convertView.findViewById(R.id.textJam);
            TextView textharga = (TextView) convertView.findViewById(R.id.textJamtayang);

            int resId = getResources().getIdentifier(img_src[n],"drawable",getPackageName());

                    imageView.setImageResource(resId);
                    textNama.setText(nama_film[n]);
                    textJudul.setText(himpunanJam[n]);
                    textharga.setText("Rp "+harga[n]);

            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(DetailBioskopActivity.this, PemesananActivity.class);
            intent.putExtra("id_1", nama_film[i]);
            intent.putExtra("id_2", jam[i]);
            intent.putExtra("id_3", nama_bioskop[i]);
            intent.putExtra("id_4", harga[i]);
            intent.putExtra("id_5", id_bioskop[i]);
            intent.putExtra("id_6", id_jadwal[i]);

            startActivity(intent);
        }
    };
}
