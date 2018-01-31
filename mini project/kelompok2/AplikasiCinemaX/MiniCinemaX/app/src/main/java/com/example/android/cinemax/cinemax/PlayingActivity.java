package com.example.android.cinemax.cinemax;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class PlayingActivity extends AppCompatActivity {
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    String[] bioskop_nama, jam_tayang, jamHimpun, harga, namafilm;
    int a;
    int b;
    int[] id_bio, id_jad;
    ListView listView;
    TextView textViewJudul1, textViewCast1, textViewGenre1, textViewDurasi;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        Intent intent = (Intent) getIntent();
        String nama_fil = intent.getExtras().getString("id_namafilm");
        String cast = intent.getExtras().getString("cast");
        String genre = intent.getExtras().getString("genre");
        String durasi = intent.getExtras().getString("durasi");
        int sourceimage = intent.getExtras().getInt("sourceimage");

        textViewJudul1 = findViewById(R.id.textViewJudul1);
        textViewJudul1.setText(nama_fil);
        textViewCast1 = findViewById(R.id.textViewCast1);
        textViewCast1.setText(cast);
        textViewDurasi = findViewById(R.id.textViewDurasi1);
        textViewDurasi.setText(durasi);
        textViewGenre1 = findViewById(R.id.textViewGenre1);
        textViewGenre1.setText(genre);
        imageView = findViewById(R.id.imageViewFilm);
        imageView.setImageResource(sourceimage);



        Cursor cursor = queryHelper.readListFilmTayangBioskop(nama_fil + "");
        listView = findViewById(R.id.listViewFilmBioskop);

        bioskop_nama = new String[cursor.getCount()];
        jam_tayang = new String[cursor.getCount()];
        jamHimpun = new String[cursor.getCount()];
        id_bio = new int[cursor.getCount()];
        harga = new String[cursor.getCount()];
        namafilm = new String[cursor.getCount()];
        id_jad = new int[cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++){
            cursor.moveToPosition(n);
            bioskop_nama[n] = cursor.getString(0);
            jam_tayang[n] = cursor.getString(1);
            id_bio[n] = cursor.getInt(2);
            harga[n] = cursor.getString(3);
            namafilm[n] = cursor.getString(4);
            id_jad[n] = cursor.getInt(5);
        }
        a=0;
        b=0;
        jamHimpun[b]="";
        for (int i=0; i<bioskop_nama.length; i++) {
            if(i==0){
                bioskop_nama[a] = bioskop_nama[i];
                jamHimpun[b] += jam_tayang[i]+", ";
                harga[a] = harga[i];
                namafilm[a] = namafilm[i];
                id_bio[a] = id_bio[i];
                a++;
            }
            else{
                if(id_bio[i] == id_bio[i-1]){
                    jamHimpun[b] += jam_tayang[i]+", ";
                }
                else{
                    b++;
                    jamHimpun[b]="";
                    bioskop_nama[a] = bioskop_nama[i];
                    jamHimpun[b] += jam_tayang[i]+", ";
                    harga[a] = harga[i];
                    id_bio[a] = id_bio[i];
                    namafilm[a] = namafilm[i];
                    a++;
                }
            }
        }

        cursor.close();

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(onListClick);

    }

    class CustomAdapter extends BaseAdapter{

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
            convertView = getLayoutInflater().inflate(R.layout.custom_list_tayangfilm, null);

            TextView textNama = (TextView) convertView.findViewById(R.id.textViewBioskopTayangFilmx);
            TextView textAlamat = (TextView) convertView.findViewById(R.id.textViewJamTayangFilmx);

            textNama.setText(bioskop_nama[n]);
            textAlamat.setText(jamHimpun[n]);

            return convertView;
        }
}

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(PlayingActivity.this, PemesananActivity.class);
            intent.putExtra("id_1", namafilm[i]);
            intent.putExtra("id_3", bioskop_nama[i]);
            intent.putExtra("id_2", jam_tayang[i]);
            intent.putExtra("id_4", harga[i]);
            intent.putExtra("id_5", id_bio[i]);
            intent.putExtra("id_6", id_jad[i]);

            startActivity(intent);
        }
    };
}
