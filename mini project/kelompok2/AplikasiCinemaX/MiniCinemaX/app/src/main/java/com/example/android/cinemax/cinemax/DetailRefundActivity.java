package com.example.android.cinemax.cinemax;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailRefundActivity extends AppCompatActivity {
    TextView textJudul, textKode, textNama, textBioskop, textJadwal,tes;
    Button btn_batal,btn_liatkursi;
    String status;
    ProgressDialog progressDialog;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    private Context mContext;
    String []bioskop1, kursi1,tgl1,judul1,id;
    SessionManager session;


    //    private String EVENT_DATE_TIME = "2018-01-20 17:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_refund);
        final Intent intent= getIntent();
        final String kodepmb= intent.getExtras().getString("id_11");
        final String nama = intent.getExtras().getString("id_13");
        final String judul = intent.getExtras().getString("id_12");
        final String bioskop = intent.getExtras().getString("id_14");
        final String jadwal = intent.getExtras().getString("id_15");
        final String tgl = intent.getExtras().getString("id_16");
        final String rek = intent.getExtras().getString("id_17");
        final String stat = intent.getExtras().getString("id_18");
        final String nama_rek = intent.getExtras().getString("id_19");
        final  String tgl_tayang = intent.getExtras().getString("id_20");
        mContext = this;
        session=new SessionManager(DetailRefundActivity.this);
        String all=session.email();

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        Cursor cursor2= queryHelper.sesi(all+"");
        id=new String [cursor2.getCount()];
        cursor2.moveToFirst();
        for(int n=0;n<cursor2.getCount();n++){
            id[n]=cursor2.getString(0);
        }
        cursor2.close();

        Cursor cursor = queryHelper.liatkursi(id[0],tgl_tayang,kodepmb);
/*        String tampilText = " ";
        String [] opsi = new String [cursor.getCount()];*/

        judul1 = new String[cursor.getCount()];
        bioskop1 = new String[cursor.getCount()];
        tgl1 = new String[cursor.getCount()];
        kursi1= new String [cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++){
            cursor.moveToPosition(n);
            bioskop1[n]=cursor.getString(1);
            judul1[n]= cursor.getString(0);
            tgl1[n]=cursor.getString(3);
            kursi1[n]=cursor.getString(2);
        }
        cursor.close();



        textJudul = findViewById(R.id.judul);
        textKode = findViewById(R.id.kode_booking);
        textBioskop= findViewById(R.id.nama_bioskop);
        textJadwal= findViewById(R.id.jadwal);
        textNama= findViewById(R.id.nama);
        btn_batal= findViewById(R.id.btn_batalkan);
        btn_liatkursi = findViewById(R.id.btn_listkursi);
        tes= findViewById(R.id.test);
        btn_liatkursi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1= new Dialog(DetailRefundActivity.this);
                dialog1.setContentView(R.layout.list_kursi);
                ListView list_kursi = (ListView)dialog1.findViewById(R.id.list_kursi);
                CustomAdapter customAdapter=new CustomAdapter();
                list_kursi.setAdapter(customAdapter);
                Button close=(Button)dialog1.findViewById(R.id.btn_back);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        //menyimpan waktu sekarang
   /*     Date now = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String nows = dateFormat.format(now);*/
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date current_date = new Date();
            Date event_date = dateFormat.parse(String.valueOf(tgl));
            if(!event_date.after(current_date)) {
                final long diff = current_date.getTime() - event_date.getTime();
                btn_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(diff<=900000 ){
                            final Dialog dialog = new Dialog(DetailRefundActivity.this);
                            dialog.setContentView(R.layout.activity_refund);
                            TextView rekening = (TextView)dialog.findViewById(R.id.rek);
                            TextView namaa=(TextView)dialog.findViewById(R.id.nama);
                            Button ok=(Button)dialog.findViewById(R.id.btn_ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    progressDialog = new ProgressDialog(DetailRefundActivity.this);
                                    progressDialog.setTitle("Mengirim Refund");
                                    progressDialog.setMessage("Mohon Tunggu Hingga Proses Selesai");
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();

                                    final Runnable progressRunnable = new Runnable() {

                                        @Override
                                        public void run() {

                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            ContentValues values=new ContentValues();
                                            values.put("status","refund");

                                            db.update("pemesanan",values,"_id='"+kodepmb+"'",null);
                                            db.execSQL("DELETE FROM detail_pemesanan WHERE id_pemesanan IN ( SELECT id_pemesanan FROM detail_pemesanan a JOIN pemesanan b  ON a.id_pemesanan = b._id WHERE a.id_pemesanan = '"+kodepmb+"' and b.tanggal ='"+tgl_tayang+"')");

                                            progressDialog.setContentView(R.layout.success_dialog);
                                            TextView namaa=(TextView)progressDialog.findViewById(R.id.namaaa);
                                            TextView rekk = (TextView) progressDialog.findViewById(R.id.rekeningg);
                                            Button btn_selesai = (Button) progressDialog.findViewById(R.id.btn_selesai);
                                            dialog.dismiss();

                                            namaa.setText(nama_rek);
                                            rekk.setText(rek);
                                            btn_selesai.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent1=new Intent(getApplicationContext(),ListTransaksiActivity.class);
                                                    startActivity(intent1);
                                                }
                                            });

                                        }
                                    };

                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 3000);
                                  /*final Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            progressDialog.setMessage("Success Refund to Your Account");
                                            progressDialog.dismiss();
                                            timer.cancel();
                                        }
                                    },5000);*/


                                   /* Intent intent1=new Intent(getApplicationContext(),ListTransaksiActivity.class);
                                    startActivity(intent1);*/
                                }
                            });
                            Button tidak=(Button)dialog.findViewById(R.id.btn_tidak);
                            tidak.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            rekening.setText(rek);
                            namaa.setText(nama_rek);
                            dialog.show();
                            /*Intent intent1 = new Intent(DetailRefundActivity.this,RefundActivity.class);
                            intent1.putExtra("id_11",kodepmb);
                            intent1.putExtra("id_12",judul);
                            intent1.putExtra("id_13",nama);
                            intent1.putExtra("id_14",bioskop);
                            intent1.putExtra("id_15",jadwal);
                            intent1.putExtra("id_16",tgl);
                            intent1.putExtra("id_17",rek);
                            startActivity(intent1);*/
                            Toast.makeText(getApplicationContext(),"Masih bisa refund",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Batas Refund Telah Habis", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(this,"Waktu Pemesanan Melebihi Waktu Sekarang",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        tes.setText(tgl);
        textKode.setText("KBX0P"+kodepmb);
        textNama.setText(nama);
        textJudul.setText(judul);
        textBioskop.setText(bioskop);
        textJadwal.setText(jadwal);
    }

    @Override
    public void onBackPressed() {
        Intent balik = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(balik);
    }
    /*  class JedaWaktu extends TimerTask {
            private int counter = 0;

            @Override
            public void run() {
                Log.d("debugTimer", "excuted>" + counter);
                counter++;

                if (progressDialog != null && counter>=7) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setMessage("Refund Success"+counter);
                            progressDialog.setContentView(R.layout.activity_main);
                            progressDialog.dismiss();

                        }
                    });
                    this.cancel();
                }
            }


        }*/
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return kursi1.length ;
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
            convertView = getLayoutInflater().inflate(R.layout.list_kursi_custom, null);
            TextView textJudul = (TextView) convertView.findViewById(R.id.judul);
            TextView textBioskop = (TextView) convertView.findViewById(R.id.bioskop);
            TextView textKursi =(TextView) convertView.findViewById(R.id.kursi);
            TextView textTanggal = (TextView) convertView.findViewById(R.id.tanggal);

            textJudul.setText(judul1[n]);
            textBioskop.setText(bioskop1[n]);
            textKursi.setText(kursi1[n]);
            textTanggal.setText(tgl1[n]);
            return convertView;
        }
    }
}
