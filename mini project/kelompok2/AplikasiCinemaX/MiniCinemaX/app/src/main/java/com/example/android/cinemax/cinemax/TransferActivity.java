package com.example.android.cinemax.cinemax;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;


public class TransferActivity extends AppCompatActivity {
    Button buttonPay,buttondetail;
    TextView text0;
    int totalbiaya;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Cursor cursor, cursor2;
    String[] id;
    String kodebook,judulFilm,namabioskop,harga,jumlahtiket,jamtayang,tanggalfilm;
    EditText norek,namarek;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    SessionManager session;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        session = new SessionManager(TransferActivity.this);
        String all = session.email();

        cursor2 = queryHelper.sesi(all+"");

        id = new String[cursor2.getCount()];
        cursor2.moveToFirst();
        for (int n=0; n<cursor2.getCount();n++){
            id[n] = cursor2.getString(0);
        }
        cursor2.close();
        Toast.makeText(getApplicationContext(), id[0], Toast.LENGTH_LONG).show();



        norek = (EditText)findViewById(R.id.norek);
        //nomorrekening = norek.getText().toString();
        namarek = (EditText)findViewById(R.id.namarek);
        //namarekening = namarek.getText().toString();

        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String nows = dateFormat.format(now);


        buttonPay= (Button) findViewById(R.id.btnPembayaran);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((norek.length()<10)) {
                    norek.setError("masukkan minimal 16 digit");
                    namarek.setError("masukkan nama rekening Anda");
                    Toast.makeText(getApplicationContext(),"Masukkan nomor rekening dengan benar",Toast.LENGTH_LONG).show();
                }
                else if ((namarek.length()<=2)) {
                    norek.setError("masukkan minimal 16 digit");
                    namarek.setError("masukkan nama rekening Anda");
                    Toast.makeText(getApplicationContext(),"Masukkan nama rekening dengan benar",Toast.LENGTH_LONG).show();
                }
                else if((norek.length()>=10) && (namarek.length()>2) ) {


                    progressDialog = new ProgressDialog(TransferActivity.this);
                    progressDialog.setTitle("Proses transfer");
                    progressDialog.setMessage("Mohon tunggu hingga selesai");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    final Runnable progressRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TransferActivity.this, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(TransferActivity.this, "Berhasil", Toast.LENGTH_LONG).show();
                            SQLiteDatabase update = dbHelper.getWritableDatabase();
                            update.execSQL("UPDATE pemesanan SET no_rek = '"+norek.getText().toString()+"', nama_rek = '"+namarek.getText().toString()+"', tanggal_pesan = '"+nows+ "',status='TERBAYAR' where id_user ='"+id[0]+"'");
                            Intent intent = new Intent(TransferActivity.this, ListTransaksiActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    };
                    Handler canceller = new Handler();
                    canceller.postDelayed(progressRunnable, 5000);
                }
            }
        });

        Intent intent = (Intent) getIntent();

        judulFilm = intent.getExtras().getString("judul");
        namabioskop = intent.getExtras().getString("bioskop");
        harga = intent.getExtras().getString("harga");
        jamtayang = intent.getExtras().getString("jamtampil");
        jumlahtiket = intent.getExtras().getString("jumlahkursi");
        totalbiaya = intent.getExtras().getInt("totalbiaya");
        kodebook = intent.getExtras().getString("kodebooking");
        tanggalfilm = intent.getExtras().getString("id_tanggal");
        // tanggalfilm = intent.getExtras().getString("id_tanggal");
        //  Log.d("hasil",tanggalfilm);


        final String b = String.valueOf(totalbiaya);
        text0 = (TextView) findViewById(R.id.text0);
        text0.setText("Rp " + b);


        buttondetail = (Button) findViewById(R.id.btnDetail);
        buttondetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TransferActivity.this);
                dialog.setContentView(R.layout.pop_detailtagihan);
                TextView kodebooking = (TextView)dialog.findViewById(R.id.kodebooking);
                TextView judul = (TextView)dialog.findViewById(R.id.namafilm);
                TextView bioskop = (TextView)dialog.findViewById(R.id.namabioskop);
                TextView tiket = (TextView)dialog.findViewById(R.id.hargatiketfilm);
                TextView jam = (TextView)dialog.findViewById(R.id.jamtayang);
                TextView kursi = (TextView)dialog.findViewById(R.id.tanggalnonton);
                TextView total = (TextView)dialog.findViewById(R.id.tiketkursi);
                TextView tanggal = (TextView) dialog.findViewById(R.id.texttanggal);

                judul.setText(judulFilm);
                bioskop.setText(namabioskop);
                tiket.setText(harga);
                jam.setText(jamtayang);
                kursi.setText(jumlahtiket);
                tanggal.setText(tanggalfilm);
                //  tanggal.setText(tanggalfilm);
                total.setText("Rp"+ b);
                kodebooking.setText("KBX0P"+kodebook);

                dialog.show();

                Button buttonX=(Button)dialog.findViewById(R.id.buttonX);
                buttonX.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }


        });

        // initiate a radio button
        RadioButton simpleRadioButton = (RadioButton) findViewById(R.id.radioButton);
        // set the current state of a radio button
        simpleRadioButton.setChecked(true);

    }

    }




