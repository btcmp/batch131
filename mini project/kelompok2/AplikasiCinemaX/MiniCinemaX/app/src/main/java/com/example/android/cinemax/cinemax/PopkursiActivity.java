package com.example.android.cinemax.cinemax;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import static java.lang.Byte.valueOf;

public class PopkursiActivity extends AppCompatActivity {

    CheckBox a1, a2, a3, a4,
            b1, b2, b3, b4,
            c1, c2, c3, c4;
    String[] nomor_seat, nomor_kursi_terpilih;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    int id_bioskop, id_film, pesanint=0, jumlahPesan, id_jadwal, hargaset, hasilkali;
    String id_tanggal, jampick, judul, harga, bioskop, jamtampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popkursi);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);



        final Intent intent = (Intent) getIntent();
        id_bioskop = intent.getExtras().getInt("id_bioskop");
        judul = intent.getExtras().getString("judul");
        id_jadwal = intent.getExtras().getInt("id_jadwal");
        id_tanggal = intent.getExtras().getString("id_tanggal");
        harga = intent.getExtras().getString("harga");
        bioskop = intent.getExtras().getString("bioskop");
        jamtampil = intent.getExtras().getString("jamtampil");


        hargaset = Integer.parseInt(harga);
//
//        TextView textViewHarga = findViewById(R.id.textViewHarga);
//        textViewHarga.setText(hargaset*pesanint);

        nomor_kursi_terpilih = new String[12];

//        Cursor cursor = queryHelper.readPemesanan();

//          b2 = findViewById(R.id.b2);
//          b3 = findViewById(R.id.b3);
//          b4 = findViewById(R.id.b4);

        Cursor cursor1 = queryHelper.readNomorKursi(judul, id_bioskop, id_jadwal, id_tanggal);

        nomor_seat = new String[cursor1.getCount()];
        jumlahPesan = cursor1.getCount();
        Toast.makeText(PopkursiActivity.this, id_jadwal+" nya", Toast.LENGTH_SHORT).show();

        cursor1.moveToFirst();
        for (int m = 0; m < cursor1.getCount(); m++) {
            cursor1.moveToPosition(m);
            nomor_seat[m] = cursor1.getString(2);
        }
        cursor1.close();

        a1 = (CheckBox) findViewById(R.id.a1);
        cekNomorSeat(a1);
        a1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = a1.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        a2 = (CheckBox) findViewById(R.id.a2);
        cekNomorSeat(a2);
        a2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = a2.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        a3 = (CheckBox) findViewById(R.id.a3);
        cekNomorSeat(a3);
        a3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = a3.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        a4 = (CheckBox) findViewById(R.id.a4);
        cekNomorSeat(a4);
        a4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = a4.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b1 = (CheckBox) findViewById(R.id.b1);
        cekNomorSeat(b1);
        b1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = b1.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2 = (CheckBox) findViewById(R.id.b2);
        cekNomorSeat(b2);
        b2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = b2.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b3 = (CheckBox) findViewById(R.id.b3);
        cekNomorSeat(b3);
        b3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = b3.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4 = (CheckBox) findViewById(R.id.b4);
        cekNomorSeat(b4);
        b4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = b4.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        c1 = (CheckBox) findViewById(R.id.c1);
        cekNomorSeat(c1);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = c1.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        c2 = (CheckBox) findViewById(R.id.c2);
        cekNomorSeat(c2);
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = c2.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        c3 = (CheckBox) findViewById(R.id.c3);
        cekNomorSeat(c3);
        c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = c3.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        c4 = (CheckBox) findViewById(R.id.c4);
        cekNomorSeat(c4);
        c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    nomor_kursi_terpilih[pesanint] = c4.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    pesanint = pesanint - 1;
                    Toast.makeText(PopkursiActivity.this, pesanint+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //========

//        qty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String convertEditText = qty.getText().toString();
//
//                if (convertEditText.length() == 0 ){
//                        Toast.makeText(ProductDetail.this, "tidak Kosong"  , Toast.LENGTH_SHORT).show();
//
//                } else if (convertEditText.length() > 0){
//                    int convertEditIntText = Integer.parseInt(convertEditText);
//                    if(convertEditIntText != 0) {
//                        int hasil = price * convertEditIntText;
//                        String hasil2 = String.valueOf(hasil);
//                        total.setText(hasil2);
//                    }
//                    else{
//                        qty.setError("Fill the Quantity min 1 item ");
//                        GoToChart.setEnabled(false);
//                        // Toast.makeText(ProductDetail.this, "Fill the Quantity min 1 item "  , Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

        //========

//        b2.setVisibility(View.INVISIBLE);
//        b3.setVisibility(View.INVISIBLE);
//        b4.setVisibility(View.INVISIBLE);

        Cursor cursor2 = queryHelper.ambilIdSaja();

        final int[] idku = new int[cursor2.getCount()];

        cursor2.moveToFirst();
        for (int m = 0; m < cursor2.getCount(); m++) {
            cursor2.moveToPosition(m);
            idku[m] = cursor2.getInt(0);
        }
        cursor2.close();

//        Toast.makeText(PopkursiActivity.this, idku[0]+"lala", Toast.LENGTH_SHORT).show();


        Button btn = (Button) findViewById(R.id.buttonKursiPesan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(pesanint == 0){
                    Toast.makeText(PopkursiActivity.this, "Pilih kursi", Toast.LENGTH_LONG).show();
                }
                else if (pesanint>0){

                    for(int boun = 0; boun<12; boun ++){
//                    Toast.makeText(PopkursiActivity.this, nomor_kursi_terpilih[boun]+"lala", Toast.LENGTH_SHORT).show();

                        if(nomor_kursi_terpilih[boun]!=null){
//                        Toast.makeText(PopkursiActivity.this, nomor_kursi_terpilih[boun]+"lala", Toast.LENGTH_SHORT).show();
                            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                            String queku1 = "insert into detail_pemesanan(id_pemesanan, " +
                                    "kursi) values (" +
                                    "'"+idku[0]+"'," +
                                    "'"+nomor_kursi_terpilih[boun]+"');";
                            db1.execSQL(queku1);
                        }
                    }

                    Toast.makeText(PopkursiActivity.this, "Pemesanan Kursi Berhasil " + pesanint, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(PopkursiActivity.this, PembayaranActivity.class);
                    intent.putExtra("judul", judul);
                    intent.putExtra("bioskop", bioskop);
                    intent.putExtra("harga", harga);
                    intent.putExtra("isinya", jampick);
                    intent.putExtra("jumlahkursi", pesanint);
                    intent.putExtra("jamtampil", jamtampil);
                    intent.putExtra("id_tanggal",id_tanggal);

                    startActivity(intent);

                }
            }
        });

    }

    private void cekNomorSeat(CheckBox cb){
        for (int inc = 0; inc < jumlahPesan; inc++){
            if (cb.getText().toString().matches(nomor_seat[inc])){
                cb.setVisibility(View.INVISIBLE);
                break;
            }
            else {
                cb.setVisibility(View.VISIBLE);
            }
        }
    }
}
