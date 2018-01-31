package com.example.android.cinemax.cinemax;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PemesananActivity extends AppCompatActivity {
    Spinner spinner, spinner2;
    private Cursor cursor;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    ArrayAdapter<String> adapter, adapter2;
    Button button;
    TextView textTampildb;
    EditText tanggal;
    int jampick;
    String tanggal_nonton, jamtampil;
    SessionManager session;
    String[] id;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);
        spinner = (Spinner) findViewById(R.id.spinnerJam);
        session=new SessionManager(PemesananActivity.this);
        String all=session.email();
        Toast.makeText(getApplicationContext(),all,Toast.LENGTH_LONG).show();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Cursor cursor = queryHelper.sesi(all+"");

        id = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int n= 0; n<cursor.getCount(); n++){
            cursor.moveToPosition(n);
            id[n]= cursor.getString(0);

        }
        cursor.close();
        Toast.makeText(getApplicationContext(), id[0], Toast.LENGTH_LONG).show();
// spinner2 = (Spinner) findViewById(R.id.spinnerKursi);


        tampilJam();

//        Button button = findViewById(R.id.buttonKursi);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        Intent intent=(Intent) getIntent();

        TextView judulFilm = findViewById(R.id.textViewJudul);
        TextView tempatBioskop = findViewById(R.id.textViewBioskop);
        TextView hargaBioskop = findViewById(R.id.textViewHarga);
        TextView nameDetail = findViewById(R.id.textNamaDetail);


        final String judul = intent.getExtras().getString("id_1");
        judulFilm.setText(judul);

        final String bioskop = intent.getExtras().getString("id_3");
        tempatBioskop.setText(bioskop);

        final String harga = intent.getExtras().getString("id_4");
        hargaBioskop.setText(harga);

        final int id_bio = intent.getExtras().getInt("id_5");
        final int id_jad = intent.getExtras().getInt("id_6");

        //tampilPesan();

        tanggal = findViewById(R.id.editTanggal);
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        tanggal.setInputType(InputType.TYPE_NULL);

        button = (Button) findViewById(R.id.buttonPesan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tanggal.getText().toString().matches("")){
                    Toast.makeText(PemesananActivity.this, "Tanggal Film belum diisi", Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into pemesanan(id_bioskop, id_jadwal, nama_film, tanggal, id_user)values('"+id_bio+"','"+jampick+"','"+judul+"', '"+tanggal_nonton+"', '"+id[0]+"')");

                    final AlertDialog.Builder builder = new AlertDialog.Builder(PemesananActivity.this);
                    builder.setTitle("Pemesanan Berhasil");
                    builder.setMessage("Silahkan pilih kursi");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intentKursi = new Intent(PemesananActivity.this, PopkursiActivity.class);
                            intentKursi.putExtra("judul", judul);
                            intentKursi.putExtra("id_bioskop", id_bio);
                            intentKursi.putExtra("id_jadwal", jampick);
                            intentKursi.putExtra("id_tanggal",tanggal.getText().toString());
                            intentKursi.putExtra("harga", harga);
                            intentKursi.putExtra("bioskop", bioskop);
                            intentKursi.putExtra("jamtampil", jamtampil);
                            startActivity(intentKursi);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }

    private void showDateDialog() {
        Date dt = new Date();

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(dt);
        newCalendar.add(Calendar.DATE,1);

        datePickerDialog = new DatePickerDialog(PemesananActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                tanggal.setText(simpleDateFormat.format(newDate.getTime()));
                tanggal_nonton = simpleDateFormat.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void tampilJam(){
        Intent intent=(Intent) getIntent();
        String judul = intent.getExtras().getString("id_1");
        String bioskop = intent.getExtras().getString("id_3");

        Cursor cursor = queryHelper.readJamFilm(bioskop+"", judul+"");

        final String[] jam = new String[cursor.getCount()];
        final int[] id_ty = new int[cursor.getCount()];
        String[] jam_1 = new String[cursor.getCount()];
//        String[] kursi = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n=0; n< cursor.getCount(); n++){
            cursor.moveToPosition(n);
            jam[n] = cursor.getString(0);
            id_ty[n] = cursor.getInt(2);

//            kursi[n] = cursor.getString(5);
        }
        cursor.close();

        adapter = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_item, jam);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jampick = id_ty[i];
                jamtampil = jam[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        adapter2 = new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_item, kursi);
//        spinner.setAdapter(adapter2);
    }

//    private void tampilPesan(){
//        Cursor cursor = queryHelper.readPemesanan();
//
//        String[] _id = new String[cursor.getCount()];
//        String[] id_user = new String[cursor.getCount()];
//        String[] id_bioskop = new String[cursor.getCount()];
//        String[] nama_film = new String[cursor.getCount()];
//        String[] id_jadwal = new String[cursor.getCount()];
//
//        cursor.moveToFirst();
//        for (int n=0; n< cursor.getCount(); n++){
//            cursor.moveToPosition(n);
//            _id[n] = cursor.getString(0);
//            id_user[n] = cursor.getString(1);
//            id_bioskop[n] = cursor.getString(2);
//            nama_film[n] = cursor.getString(3);
//            id_jadwal[n] = cursor.getString(4);
//        }
//        cursor.close();
//
//        String tampilText = "";
//        for(int i=0; i<_id.length; i++){
//            tampilText += id_user[i] +
//                    " - " + id_bioskop[i] +
//                    " - " + nama_film[i] +
//                    " - " + id_jadwal[i] +
//                    "\n";
//        }
//        textTampildb.setText(tampilText);
//    }
}
