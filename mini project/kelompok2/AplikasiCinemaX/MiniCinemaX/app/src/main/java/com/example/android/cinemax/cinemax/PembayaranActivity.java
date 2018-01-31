package com.example.android.cinemax.cinemax;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class PembayaranActivity extends AppCompatActivity {
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Cursor cursor;

    TextView TextBook;

    String judul;
    String bioskop;
    String harga;
    String isinya;
    String b;
    String tanggalfilm;
    String tampiltext;
    int a, jumlahkursi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);


        TextBook = (TextView) findViewById(R.id.kodebooking);
        TextView judulFilm = findViewById(R.id.hargaTiket1);
        TextView namabioskop = findViewById(R.id.jumlahTiket1);
        TextView hargatiket = findViewById(R.id.total1);
        TextView jamtayang = findViewById(R.id.jamtayang);
        TextView tiketkursi = findViewById(R.id.tiketkursi);
        TextView totalbiaya = findViewById(R.id.totalbiaya);
        final TextView tanggal = findViewById(R.id.tanggal_film);


        readPemesanan();


        Button buttonBayar = (Button) findViewById(R.id.buttonPay);
        buttonBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PembayaranActivity.this);
                builder.setMessage("Tiket berhasil dipesan");
                builder.setPositiveButton("bayar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PembayaranActivity.this,TransferActivity.class);
                        intent.putExtra("totalbiaya",a);
                        intent.putExtra("judul",judul);
                        intent.putExtra("bioskop",bioskop);
                        intent.putExtra("harga",harga);
                        intent.putExtra("jamtampil",isinya);
                        intent.putExtra("jumlahkursi",b);
                        intent.putExtra("kodebooking",tampiltext);
                        intent.putExtra("id_tanggal",tanggalfilm);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Intent intent = (Intent) getIntent();
        judul = intent.getExtras().getString("judul");
        judulFilm.setText(judul);
        bioskop = intent.getExtras().getString("bioskop");
        namabioskop.setText(bioskop);
        harga = intent.getExtras().getString("harga");
        hargatiket.setText(harga);
        tanggalfilm = intent.getExtras().getString("id_tanggal");
        tanggal.setText(tanggalfilm);

        isinya = intent.getExtras().getString("jamtampil");

        //=======
//        Cursor cursor = queryHelper.jamPembayaran(isinya);
//
//        String[] jam = new String[cursor.getCount()];
//
//        cursor.moveToFirst();
//        for (int n=0; n< cursor.getCount(); n++){
//            cursor.moveToPosition(n);
//            jam[n] = cursor.getString(1);
//        }
//        cursor.close();
//        //======
//        tampilText = "";
//        for(int i=0; i<jam.length; i++){
//            tampilText += jam[i];
//        }
        jamtayang.setText(isinya);

        jumlahkursi = intent.getExtras().getInt("jumlahkursi");
        b = String.valueOf(jumlahkursi);
        tiketkursi.setText(b);

        //ini untuk mengubah int to string
        a = Integer.parseInt(harga) * Integer.parseInt(tiketkursi.getText().toString()) ;
        totalbiaya.setText("Rp "+a);
    }

    private void readPemesanan() {
        cursor = queryHelper.readPemesanan();
        String[] kode = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            kode[i] = cursor.getString(0);
        }
        cursor.close();
        tampiltext ="";
        for (int i=0; i<kode.length; i++){
            tampiltext = kode[i];
        }
        TextBook.setText("KBX0P"+tampiltext);
    }
}
