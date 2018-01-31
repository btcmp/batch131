package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class HasilCariKeberangkatan extends AppCompatActivity {

    private Context mContext;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    ListView listView;


    String[] id_bus, jam_keberangkatan, nama_kelas;
    int[] harga, id_trayek, kursitersedia, jumlah_kursi, jumlah_kursi2;
    boolean[] tampilkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.pemesanan);
        setContentView(R.layout.activity_hasil_cari_keberangkatan);

        mContext = this;

        listView = findViewById(R.id.list_view);
        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);

        final Intent intent= getIntent();
        int kotasatu = intent.getExtras().getInt("kotaAwal");
        int kotadua = intent.getExtras().getInt("kotaTujuan");
        final String tanggal = intent.getExtras().getString("tanggal");
        final String idUser = intent.getExtras().getString("idUser");


        importDbFromSQLFile();
        Cursor cursor = queryHelper.readTrayekPilihan(kotasatu+"", kotadua+"");
        String tampilText = "";
        String[] opsi = new  String[cursor.getCount()];

        id_trayek = new int[cursor.getCount()];
        id_bus = new String[cursor.getCount()];
        jam_keberangkatan = new String[cursor.getCount()];
        harga = new int[cursor.getCount()];
        jumlah_kursi = new int[cursor.getCount()];
        kursitersedia = new int[cursor.getCount()];
        tampilkan = new boolean[cursor.getCount()];
        nama_kelas = new  String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            id_trayek[n] = cursor.getInt(0);
            id_bus[n] = cursor.getString(1);
            jam_keberangkatan[n] = cursor.getString(2);
            harga[n] = cursor.getInt(3);
            jumlah_kursi[n] = cursor.getInt(4);
            nama_kelas[n] = cursor.getString(5);
        }
        cursor.close();

        if(cursor.getCount()==0){

            Toast.makeText(HasilCariKeberangkatan.this, "Tidak terdapat bus yang tersedia", Toast.LENGTH_LONG).show();
            Intent intent1=new Intent(HasilCariKeberangkatan.this, CariKeberangkatan.class)  ;
            intent1.putExtra("session",idUser);
            startActivity(intent1);
        }
        else{
            Cursor cursor1;
            for(int i=0; i<id_trayek.length; i++){
                cursor1 = queryHelper.readKursiDipesan(id_trayek[i], tanggal);
                jumlah_kursi2 = new int[cursor1.getCount()];
                cursor1.moveToFirst();
                for (int m = 0; m < cursor1.getCount(); m++) {
                    cursor1.moveToPosition(m);
                    jumlah_kursi2[m] = cursor1.getInt(0);
                }
                cursor1.close();
                kursitersedia[i] = jumlah_kursi[i] - jumlah_kursi2[0];
                tampilkan[i] = kursitersedia[i] > 0;
            }

            int a=0;
            for(int j=0; j<id_trayek.length; j++){
                if(tampilkan[j]==true){
                    id_trayek[a] = id_trayek[j];
                    id_bus[a] = id_bus[j];
                    jam_keberangkatan[a] = jam_keberangkatan[j];
                    harga[a] = harga[j];
                    kursitersedia[a] = kursitersedia[j];
                    nama_kelas[a] = nama_kelas[j];
                    a++;
                }
            }

        }

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String selection = id_bus[position];
                final CharSequence[] dialogitem = {"Lihat Detail", "Pesan Sekarang"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilCariKeberangkatan.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent i = new Intent(HasilCariKeberangkatan.this, DetailBus.class);
                                i.putExtra("idTrayek", id_trayek[position]);
                                i.putExtra("tanggal", tanggal);
                                i.putExtra("idUser", idUser);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(HasilCariKeberangkatan.this, Pemesanan.class);
                                in.putExtra("idTrayek", id_trayek[position]);
                                in.putExtra("tanggal", tanggal);
                                in.putExtra("idUser", idUser);
                                startActivity(in);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return id_bus.length;
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_view,null);
            TextView textView_idbus = view.findViewById(R.id.tIdBus);
            TextView textView_jamkeb = view.findViewById(R.id.tJamKeb);
            TextView textView_harga = view.findViewById(R.id.tHarga);
            TextView textView_kursi = view.findViewById(R.id.tKursi);
            TextView textView_namakelas = view.findViewById(R.id.kelasBus);

            textView_namakelas.setText(nama_kelas[i] + " Class");
            textView_idbus.setText(id_bus[i]);
            textView_jamkeb.setText(jam_keberangkatan[i]);
            textView_harga.setText(Converter.convertIntegerToCurrency(harga[i], "Rp"));
            textView_kursi.setText(kursitersedia[i]+"");

            return view;
        }
    }

    private void importDbFromSQLFile() {
        try {
            dbHelper.createDatabaseFromImportedSQL();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "importDbFromSQLFile IOException : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
