package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class DetailBus extends AppCompatActivity {

    private int id_trayek;
    String tanggal;

    TextView abc, nomor, kelas, jenis, jumlah;
    Button kepesan;

    private Context mContext;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.detail);
        setContentView(R.layout.activity_detail_bus);

        mContext = this;
        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        abc = findViewById(R.id.textViewdetbus);
        nomor = findViewById(R.id.nomorbus);
        kelas = findViewById(R.id.namakelas);
        jenis = findViewById(R.id.jenisbus);
        jumlah = findViewById(R.id.jumlahkursi);

        Intent intent= getIntent();
        id_trayek = intent.getExtras().getInt("idTrayek");
        tanggal = intent.getExtras().getString("tanggal");
        final String idUser = intent.getExtras().getString("idUser");

        importDbFromSQLFile();
        Cursor cursor = queryHelper.readDetailBus(id_trayek+"");

        String tampilText = "";

        int[] nomor_bus = new int[cursor.getCount()];
        String[] nama_kelas = new String[cursor.getCount()];
        String[] jenis_bus = new String[cursor.getCount()];
        int[] jumlah_kursi = new int[cursor.getCount()];
        String[] nama_fasilitas = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            nomor_bus[n] = cursor.getInt(1);
            nama_kelas[n] = cursor.getString(6);
            jenis_bus[n] = cursor.getString(3);
            jumlah_kursi[n] = cursor.getInt(7);
            nama_fasilitas[n] = cursor.getString(9);
        }
        cursor.close();

        for(int i=0; i<nama_fasilitas.length; i++) {
            tampilText += nama_fasilitas[i] +
                    "\n";
        }

        nomor.append(nomor_bus[0]+"");
        kelas.setText(nama_kelas[0] + " Class");
        jenis.setText(jenis_bus[0]);
        jumlah.append(jumlah_kursi[0]+"");
        abc.setText(tampilText);

        kepesan = findViewById(R.id.tombol_pesan);
        kepesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DetailBus.this, Pemesanan.class);
                in.putExtra("idTrayek", id_trayek);
                in.putExtra("tanggal", tanggal);
                in.putExtra("idUser", idUser);
                startActivity(in);
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
