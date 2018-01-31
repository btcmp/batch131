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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Pemesanan extends AppCompatActivity {

    int id_trayek;

    private Context mContext;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    TextView abc;
    Button tomPesan;
    CheckBox satua, satub, satuc, satud,
            duaa, duab, duac, duad,
            tigaa, tigab, tigac, tigad,
            empata, empatb, empatc, empatd,
            limaa, limab, limac, limad,
            enama, enamb, enamc, enamd,
            tujuha, tujuhb, tujuhc, tujuhd,
            delapana, delapanb, delapanc, delapand,
            sembilana, sembilanb, sembilanc, sembiland,
            sepuluha, sepuluhb, sepuluhc, sepuluhd;

    String tanggal;
    int pesanint=0;
    int jumlahPesan;
    String[] nomor_seat, nomor_kursi_terpilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.pemesanan);
        setContentView(R.layout.activity_pemesanan);

        mContext = this;
        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        abc = findViewById(R.id.textViewCoba);
        Intent intent= getIntent();
        id_trayek = intent.getExtras().getInt("idTrayek");
        tanggal = intent.getExtras().getString("tanggal");
        final String idUser = intent.getExtras().getString("idUser");
        nomor_kursi_terpilih = new String[40];

        importDbFromSQLFile();
        Cursor cursor = queryHelper.readDetailTrayek(id_trayek+"");

        String tampilText = "";
        final int[] id_trayek = new int[cursor.getCount()];
        int[] nomor_bus = new int[cursor.getCount()];
        String[] nama_kota = new String[cursor.getCount()];
        String[] nama_kelas = new String[cursor.getCount()];
        int[] jumlah_kursi = new int[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            id_trayek[n] = cursor.getInt(0);
            nomor_bus[n] = cursor.getInt(9);
            nama_kota[n] = cursor.getString(7);
            nama_kelas[n] = cursor.getString(14);
            jumlah_kursi[n] = cursor.getInt(15);
        }
        cursor.close();

            tampilText += "Nomor bus : " + nomor_bus[0] + "\n" +
                    nama_kelas[0] + "\n" +
                    "Jumlah Seat : " + jumlah_kursi[0] + "\n" ;
            for(int i=0; i<nama_kota.length; i++){
                tampilText += nama_kota[i] +
                        " ";
            }

        abc.setText(tampilText);

        Cursor cursor1 = queryHelper.readNomorKursi(id_trayek[0], tanggal);

        nomor_seat = new String[cursor1.getCount()];
        jumlahPesan = cursor1.getCount();

        cursor1.moveToFirst();
        for (int m = 0; m < cursor1.getCount(); m++) {
            cursor1.moveToPosition(m);
            nomor_seat[m] = cursor1.getString(2);
        }
        cursor1.close();

        tomPesan = findViewById(R.id.tombol_yakinpesan);
        tomPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pesanint == 0){
                    Toast.makeText(Pemesanan.this, "Pilih kursi", Toast.LENGTH_LONG).show();
                }
                else{
                    if(pesanint>0){
//
                        Intent in = new Intent(Pemesanan.this, CaraPembayaran.class);
                        in.putExtra("idTrayek", id_trayek[0]);
                        in.putExtra("jumlahKursi", pesanint);
                        in.putExtra("tanggal", tanggal);
                        in.putExtra("kursi", nomor_kursi_terpilih);
                        in.putExtra("idUser", idUser);

                        startActivity(in);
                    }
                    else {
                        Toast.makeText(Pemesanan.this, "Inputkan angka yang valid", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        satua = findViewById(R.id.satua);
        cekNomorSeat(satua);
        satua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = satua.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        satub = findViewById(R.id.satub);
        cekNomorSeat(satub);
        satub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = satub.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        satuc = findViewById(R.id.satuc);
        cekNomorSeat(satuc);
        satuc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = satuc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        satud = findViewById(R.id.satud);
        cekNomorSeat(satud);
        satud.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = satud.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        duaa = findViewById(R.id.duaa);
        cekNomorSeat(duaa);
        duaa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = duaa.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        duab = findViewById(R.id.duab);
        cekNomorSeat(duab);
        duab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = duab.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        duac = findViewById(R.id.duac);
        cekNomorSeat(duac);
        duac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = duac.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+"  kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        duad = findViewById(R.id.duad);
        cekNomorSeat(duad);
        duad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = duad.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tigaa = findViewById(R.id.tigaa);
        cekNomorSeat(tigaa);
        tigaa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tigaa.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tigab = findViewById(R.id.tigab);
        cekNomorSeat(tigab);
        tigab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tigab.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tigac = findViewById(R.id.tigac);
        cekNomorSeat(tigac);
        tigac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tigac.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tigad = findViewById(R.id.tigad);
        cekNomorSeat(tigad);
        tigad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tigad.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        empata = findViewById(R.id.empata);
        cekNomorSeat(empata);
        empata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = empata.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        empatb = findViewById(R.id.empatb);
        cekNomorSeat(empatb);
        empatb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = empatb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        empatc = findViewById(R.id.empatc);
        cekNomorSeat(empatc);
        empatc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = empatc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        empatd = findViewById(R.id.empatd);
        cekNomorSeat(empatd);
        empatd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = empatd.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        limaa = findViewById(R.id.limaa);
        cekNomorSeat(limaa);
        limaa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = limaa.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        limab = findViewById(R.id.limab);
        cekNomorSeat(limab);
        limab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = limab.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        limac = findViewById(R.id.limac);
        cekNomorSeat(limac);
        limac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = limac.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        limad = findViewById(R.id.limad);
        cekNomorSeat(limad);
        limad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = limad.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enama = findViewById(R.id.enama);
        cekNomorSeat(enama);
        enama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = enama.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enamb = findViewById(R.id.enamb);
        cekNomorSeat(enamb);
        enamb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = enamb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enamc = findViewById(R.id.enamc);
        cekNomorSeat(enamc);
        enamc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = enamc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enamd = findViewById(R.id.enamd);
        cekNomorSeat(enamd);
        enamd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = enamd.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tujuha = findViewById(R.id.tujuha);
        cekNomorSeat(tujuha);
        tujuha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tujuha.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tujuhb = findViewById(R.id.tujuhb);
        cekNomorSeat(tujuhb);
        tujuhb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tujuhb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tujuhc = findViewById(R.id.tujuhc);
        cekNomorSeat(tujuhc);
        tujuhc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tujuhc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tujuhd = findViewById(R.id.tujuhd);
        cekNomorSeat(tujuhd);
        tujuhd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = tujuhd.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delapana = findViewById(R.id.delapana);
        cekNomorSeat(delapana);
        delapana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = delapana.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delapanb = findViewById(R.id.delapanb);
        cekNomorSeat(delapanb);
        delapanb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = delapanb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delapanc = findViewById(R.id.delapanc);
        cekNomorSeat(delapanc);
        delapanc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = delapanc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delapand = findViewById(R.id.delapand);
        cekNomorSeat(delapand);
        delapand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = delapand.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sembilana = findViewById(R.id.sembilana);
        cekNomorSeat(sembilana);
        sembilana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sembilana.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sembilanb = findViewById(R.id.sembilanb);
        cekNomorSeat(sembilanb);
        sembilanb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sembilanb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sembilanc = findViewById(R.id.sembilanc);
        cekNomorSeat(sembilanc);
        sembilanc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sembilanc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sembiland = findViewById(R.id.sembiland);
        cekNomorSeat(sembiland);
        sembiland.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sembiland.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sepuluha = findViewById(R.id.sepuluha);
        cekNomorSeat(sepuluha);
        sepuluha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sepuluha.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sepuluhb = findViewById(R.id.sepuluhb);
        cekNomorSeat(sepuluhb);
        sepuluhb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sepuluhb.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sepuluhc = findViewById(R.id.sepuluhc);
        cekNomorSeat(sepuluhc);
        sepuluhc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sepuluhc.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sepuluhd = findViewById(R.id.sepuluhd);
        cekNomorSeat(sepuluhd);
        sepuluhd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    nomor_kursi_terpilih[pesanint] = sepuluhd.getText().toString();
                    pesanint = pesanint + 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
                else{
                    pesanint = pesanint - 1;
                    Toast.makeText(Pemesanan.this, pesanint+" kursi terpilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cekJumlahSeat(jumlah_kursi[0]);
    }

    private void cekJumlahSeat(int jumlahSeat){
        if(jumlahSeat==38){
            sepuluha.setVisibility(View.INVISIBLE);
            sepuluhb.setVisibility(View.INVISIBLE);
        }
        else if(jumlahSeat==34){
            sepuluha.setVisibility(View.INVISIBLE);
            sepuluhb.setVisibility(View.INVISIBLE);
            sepuluhc.setVisibility(View.INVISIBLE);
            sepuluhd.setVisibility(View.INVISIBLE);
            sembilana.setVisibility(View.INVISIBLE);
            sembilanb.setVisibility(View.INVISIBLE);
        }
        else if(jumlahSeat==28){
            sepuluha.setVisibility(View.INVISIBLE);
            sepuluhb.setVisibility(View.INVISIBLE);
            sepuluhc.setVisibility(View.INVISIBLE);
            sepuluhd.setVisibility(View.INVISIBLE);
            sembilana.setVisibility(View.INVISIBLE);
            sembilanb.setVisibility(View.INVISIBLE);
            sembilanc.setVisibility(View.INVISIBLE);
            sembiland.setVisibility(View.INVISIBLE);
            delapana.setVisibility(View.INVISIBLE);
            delapanb.setVisibility(View.INVISIBLE);
            delapanc.setVisibility(View.INVISIBLE);
            delapand.setVisibility(View.INVISIBLE);
        }

    }

    private void cekNomorSeat(CheckBox cb){
        for(int inc = 0; inc<jumlahPesan; inc++){
            if(cb.getText().toString().matches(nomor_seat[inc])){
                cb.setVisibility(View.INVISIBLE);
                break;
            }
            else{
                cb.setVisibility(View.VISIBLE);
            }
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
