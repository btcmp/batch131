package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.konfirmasi.TiketSementara;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaraPembayaran extends AppCompatActivity {

    int id_trayek, jumlah_kursi;

    private Context mContext;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    TextView cekhasil;

    Spinner spname, spname1, spname2;
    ArrayAdapter<String> adapter, adapter1, adapter2;

    String[] daftarBank = {"BNI", "BCA", "Mandiri", "BRI"};
    int[] harga, kota_asal, kota_akhir;

    String bankPilihan;
    String namaRek;
    int kotaasal;
    int kotatujuan;
    String tanggal;
    String[] kursipilihan;

    Button tomPesanan;

    EditText banknya;

    int jumlahbayar;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.pemesanan);
        setContentView(R.layout.activity_cara_pembayaran);

        mContext = this;
        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        spname = findViewById(R.id.spinnerBank);
        cekhasil = findViewById(R.id.untukcek);

        Intent intent= getIntent();
        id_trayek = intent.getExtras().getInt("idTrayek");
        jumlah_kursi = intent.getExtras().getInt("jumlahKursi");
        tanggal = intent.getExtras().getString("tanggal");
        kursipilihan = intent.getExtras().getStringArray("kursi");
         idUser = intent.getExtras().getString("idUser");
//        Toast.makeText(CaraPembayaran.this, idUser+"lala", Toast.LENGTH_LONG).show();

        importDbFromSQLFile();
        Cursor cursor = queryHelper.readTrayek(id_trayek+"");

        String tampilText = "";
        harga = new int[cursor.getCount()];
        kota_asal = new int[cursor.getCount()];
        kota_akhir = new int[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            harga[n] = cursor.getInt(5);
            kota_asal[n] = cursor.getInt(1);
            kota_akhir[n] = cursor.getInt(2);
        }
        cursor.close();

        jumlahbayar = jumlah_kursi*harga[0];

        spname1 = findViewById(R.id.spinnerPoola);
        spname2 = findViewById(R.id.spinnerPoolb);

        tampilkanPoola();
        tampilkanPoolb();


        adapter = new ArrayAdapter<String>(CaraPembayaran.this, android.R.layout.simple_spinner_item, daftarBank);
        spname.setAdapter(adapter);

        spname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankPilihan = daftarBank[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        banknya = findViewById(R.id.etNamaRek);
        tomPesanan = findViewById(R.id.kirimapi);
        tomPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaRek = banknya.getText().toString();
                if(namaRek.matches("")){
                    Toast.makeText(CaraPembayaran.this, "Inputkan Nama pada Rekening", Toast.LENGTH_LONG).show();
                }
                else{
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyy");
                    String formatTanggal = sd.format(c.getTime());
                    String formatTanggal_untukDisimpan = formatTanggal;
                    SimpleDateFormat sd1 = new SimpleDateFormat("H:m:ss");
                    String formatTanggal1 = sd1.format(c.getTime());
                    String formatTanggal_untukDisimpan1 = formatTanggal1;
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String queku = "insert into pemesanan(id_trayek, " +
                            "id_pemesan, " +
                            "pool_awal, " +
                            "pool_akhir, " +
                            "tanggal_pemesanan, " +
                            "jam_pesan, " +
                            "status_konfirmasi, " +
                            "tanggal_keberangkatan, " +
                            "tipe_pembayaran, " +
                            "atas_nama_pembayaran, total_harga) values ('"
                            +id_trayek+"','"
                            +idUser+"','"
                            +kotaasal+"','"
                            +kotatujuan+"','"
                            +formatTanggal+"','"
                            +formatTanggal1+"','"
                            +"BOOKED"+"','"
                            +tanggal+"','"
                            +bankPilihan+"','"
                            +namaRek+"','"
                            +jumlahbayar+"');";
                    db.execSQL(queku);

                    Cursor cursor1 = queryHelper.readIdPemesanan(Integer.parseInt(idUser), tanggal, id_trayek, formatTanggal_untukDisimpan, formatTanggal_untukDisimpan1);

                    int[] id_pemesanan = new int[cursor1.getCount()];

                    cursor1.moveToFirst();
                    for (int m = 0; m < cursor1.getCount(); m++) {
                        cursor1.moveToPosition(m);
                        id_pemesanan[m] = cursor1.getInt(0);
                    }
                    cursor1.close();

                    for(int boun = 0; boun<40; boun ++){
                        if(kursipilihan[boun]!=null){
                            SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                            String queku1 = "insert into detail_pemesanan(id_pemesanan, " +
                                    "nomor_kursi) values (" +
                                    "'"+id_pemesanan[0]+"'," +
                                    "'"+kursipilihan[boun]+"');";
                            db1.execSQL(queku1);
                        }
                    }

                    Cursor cursor = queryHelper.readDetailPemesanan();

                    String tampilText = "";
                    String[] id = new String[cursor.getCount()];

                    cursor.moveToFirst();
                    for (int n = 0; n < cursor.getCount(); n++) {
                        cursor.moveToPosition(n);
                        id[n] = cursor.getString(2);
                    }
                    cursor.close();

                    for (int i = 0; i < id.length; i++) {
                        tampilText += id[i] + " , ";
                    }

                    cekhasil.setText(tampilText);

                    Intent b = new Intent(CaraPembayaran.this, TiketSementara.class);
                    b.putExtra("idPemesanan", id_pemesanan[0]);
                    b.putExtra("idUser", Integer.parseInt(idUser));
                    b.putExtra("tanggalPesan", formatTanggal);
                    b.putExtra("jamPesan", formatTanggal1);
//                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(b);
                }
            }
        });

    }



    private void tampilkanPoolb() {
        Cursor cursorb = queryHelper.readPool(kota_akhir[0]);

        final String [] nama_poolb = new String[cursorb.getCount()];
        final int[] id_poolb = new int[cursorb.getCount()];

        cursorb.moveToFirst();
        for (int b = 0; b < cursorb.getCount(); b++) {
            cursorb.moveToPosition(b);
            id_poolb[b] = cursorb.getInt(0);
            nama_poolb[b] = cursorb.getString(1);
        }
        cursorb.close();

        adapter2 = new ArrayAdapter<String>(CaraPembayaran.this, android.R.layout.simple_spinner_item, nama_poolb);
        spname2.setAdapter(adapter2);

        spname2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kotatujuan = id_poolb[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void tampilkanPoola() {
        Cursor cursora = queryHelper.readPool(kota_asal[0]);

        final String [] nama_poola = new String[cursora.getCount()];
        final int[] id_poola = new int[cursora.getCount()];

        cursora.moveToFirst();
        for (int a = 0; a < cursora.getCount(); a++) {
            cursora.moveToPosition(a);
            id_poola[a] = cursora.getInt(0);
            nama_poola[a] = cursora.getString(1);
        }
        cursora.close();

        adapter1 = new ArrayAdapter<String>(CaraPembayaran.this, android.R.layout.simple_spinner_item, nama_poola);
        spname1.setAdapter(adapter1);

        spname1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kotaasal = id_poola[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
