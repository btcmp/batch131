package com.example.android.cinemax.cinemax;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.io.IOException;

public class ListTransaksiActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    private ListView listView;
    private Cursor cursor;
    private TextView textView;
    private QueryHelper queryHelper;
    private SQLiteDbHelper dbHelper;
    private Context mContext;
    SessionManager session;
    int resid1;
    String [] judul, kode, nama,bioskop,jadwal, rekening,tgl_pesan,status, nama_rek,tgl_tayang,img_src;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi);

        listView = (ListView) findViewById(R.id.list_Transaksi);

        mContext = this;

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        session = new SessionManager(ListTransaksiActivity.this);
        String all=session.email();
        Cursor cursor2=queryHelper.sesi(all+"");
        String[] id= new String [cursor2.getCount()];

        cursor2.moveToFirst();
        for(int n=0;n<cursor2.getCount();n++){
            id[n]=cursor2.getString(0);
        }
        cursor2.close();

//        Cursor cursor = queryHelper.readDetailRefund();
//        Cursor cursor = queryHelper.readListFilm();
        Cursor cursor = queryHelper.readAlldataPesanan(id[0]);
/*        String tampilText = " ";
        String [] opsi = new String [cursor.getCount()];*/

        judul = new String[cursor.getCount()];
        kode = new String[cursor.getCount()];
        nama = new String[cursor.getCount()];
        jadwal = new String [cursor.getCount()];
        bioskop = new String[cursor.getCount()];
        tgl_pesan = new String[cursor.getCount()];
        rekening = new String[cursor.getCount()];
        status = new String [cursor.getCount()];
        nama_rek= new String [cursor.getCount()];
        tgl_tayang= new String[cursor.getCount()];
        img_src = new String [cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n<cursor.getCount(); n++){
            cursor.moveToPosition(n);
            kode[n]=cursor.getString(0);
            nama[n]=cursor.getString(1);
            bioskop[n]=cursor.getString(2);
            judul[n]= cursor.getString(3);
            jadwal[n]= cursor.getString(4);
            tgl_pesan[n]=cursor.getString(5);
            rekening[n]=cursor.getString(6);
            status[n]=cursor.getString(7);
            nama_rek [n] = cursor.getString(8);
            tgl_tayang[n] = cursor.getString(9);
            img_src[n]=cursor.getString(10);
            Log.d("tgl_pesan",cursor.getString(5));

        }
        cursor.close();

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(onListClick);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return kode.length ;
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
            resid1 = getResources().getIdentifier(img_src[n],"drawable",getPackageName());
            convertView = getLayoutInflater().inflate(R.layout.list_transaksi_custom, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.gmbr);
            TextView textJudul = (TextView) convertView.findViewById(R.id.judul);
            TextView textKode = (TextView) convertView.findViewById(R.id.kode_booking);
            TextView textStatus = (TextView) convertView.findViewById(R.id.status);

            image.setImageResource(resid1);
            textKode.setText("KBXOP"+kode[n]);
            textJudul.setText(judul[n]);
            textStatus.setText(status[n]);
            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onListClick = (new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(ListTransaksiActivity.this,DetailRefundActivity.class);
            intent.putExtra("id_11",kode[i]);
            intent.putExtra("id_12",judul[i]);
            intent.putExtra("id_13",nama[i]);
            intent.putExtra("id_14",bioskop[i]);
            intent.putExtra("id_15",jadwal[i]);
            intent.putExtra("id_16",tgl_pesan[i]);
            intent.putExtra("id_17",rekening[i]);
            intent.putExtra("id_18",status[i]);
            intent.putExtra("id_19",nama_rek[i]);
            intent.putExtra("id_20",tgl_tayang[i]);
            startActivity(intent);
        }
    });

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent =new Intent(ListTransaksiActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
