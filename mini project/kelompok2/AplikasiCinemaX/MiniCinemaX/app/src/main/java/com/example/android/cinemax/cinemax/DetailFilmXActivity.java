package com.example.android.cinemax.cinemax;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class DetailFilmXActivity extends AppCompatActivity {
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Cursor cursor;
    String[] judul_film, deskripsi_film, durasi, cast, genre;
    TextView textViewJudul, textViewCast, textViewGenre, textViewDurasi, textViewDeskripsi;
    ImageView imageViewFilm;
    Button buttonYoutube, buttonPlaying;
    String source;
    String tampilText, tampilText3, tampilText4, tampilText5;
    int source2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film_x);

        textViewJudul = findViewById(R.id.textViewJudul);
        textViewCast = findViewById(R.id.textViewCast);
        textViewGenre = findViewById(R.id.textViewGenre);
        textViewDurasi = findViewById(R.id.textViewDurasi);
        textViewDeskripsi = findViewById(R.id.textViewDeskripsi);
        imageViewFilm = findViewById(R.id.imageViewFilm);

        buttonPlaying = findViewById(R.id.buttonPlaying);
        buttonPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailFilmXActivity.this, PlayingActivity.class);
                intent.putExtra("id_namafilm", tampilText);
                intent.putExtra("cast", tampilText4);
                intent.putExtra("genre", tampilText5);
                intent.putExtra("durasi", tampilText3);
                intent.putExtra("sourceimage", source2);
                startActivity(intent);
            }
        });

        buttonYoutube = findViewById(R.id.buttonYoutube);
        buttonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://"));
                startActivity(intent);
            }
        });

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        Intent intent=(Intent) getIntent();

        source = intent.getExtras().getString("id_1");
        source2 = intent.getExtras().getInt("id_2");

        cursor = queryHelper.readListFilmDetail(source+"");

        judul_film = new String[cursor.getCount()];
        deskripsi_film = new String[cursor.getCount()];
        durasi = new String[cursor.getCount()];
        cast = new String[cursor.getCount()];
        genre = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            judul_film[n] = cursor.getString(1);
            deskripsi_film[n] = cursor.getString(2);
            durasi[n] = cursor.getString(3);
            cast[n] = cursor.getString(5);
            genre[n] = cursor.getString(6);
        }
        cursor.close();

        imageViewFilm.setImageResource(source2);

        tampilText = "";
        for(int i=0; i<judul_film.length; i++){
            tampilText += judul_film[i];
        }
        textViewJudul.setText(tampilText);

        String tampilText2 = "";
        for(int i=0; i<deskripsi_film.length; i++){
            tampilText2 += deskripsi_film[i];
        }
        textViewDeskripsi.setText(tampilText2);

        tampilText3 = "";
        for(int i=0; i<durasi.length; i++){
            tampilText3 += durasi[i];
        }
        textViewDurasi.setText(tampilText3);

        tampilText4 = "";
        for(int i=0; i<cast.length; i++){
            tampilText4 += cast[i];
        }
        textViewCast.setText(tampilText4);

        tampilText5 = "";
        for(int i=0; i<genre.length; i++){
            tampilText5 += genre[i];
        }
        textViewGenre.setText(tampilText5);
    }
}

