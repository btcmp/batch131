package android.bootcamp.xsisa.hjbusbooking;

import android.app.DatePickerDialog;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CariKeberangkatan extends AppCompatActivity {

    private Context mContext;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    Spinner spname, spname1;
    ArrayAdapter<String> adapter;

    private Button buttonSearch;
    int kota_awal, kota_tujuan;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    EditText tanggalnya;
    String tanggal_keberangkatan;


    String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cari);
        setContentView(R.layout.activity_cari_keberangkatan);
        final Intent a= getIntent();
        session = a.getExtras().getString("session");


        mContext = this;

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        spname = findViewById(R.id.kotaa);
        spname1 = findViewById(R.id.kotab);
        importDbFromSQLFile();
        tampilkanKotaa();
        tampilkanKotab();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        tanggalnya = findViewById(R.id.tanggal_keb);
        tanggalnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        buttonSearch = findViewById(R.id.tombol_cari);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tanggalnya.getText().toString().matches("")){
                    Toast.makeText(CariKeberangkatan.this,  "Isikan tanggal keberangkatan", Toast.LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(mContext, session + "isinya", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(CariKeberangkatan.this, HasilCariKeberangkatan.class)  ;
                    intent.putExtra("kotaAwal", kota_awal);
                    intent.putExtra("kotaTujuan", kota_tujuan);
                    intent.putExtra("tanggal", tanggal_keberangkatan);
                    intent.putExtra("idUser", session);
                    startActivity(intent);
                }
            }
        });
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        Date dt = new Date();
//
        newCalendar.setTime(dt);
        newCalendar.add(Calendar.DATE, 1);
        dt = newCalendar.getTime();

        datePickerDialog = new DatePickerDialog(CariKeberangkatan.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(newDate.getTimeInMillis());
                tanggalnya.setText(dateFormatter.format(newDate.getTime()));
                tanggal_keberangkatan = dateFormatter.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void tampilkanKotaa() {
        Cursor cursor = queryHelper.readKota();

        int[] _id = new int[cursor.getCount()];
        String[] kota = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            _id[n] = cursor.getInt(0);
            kota[n] = cursor.getString(1);
        }
        cursor.close();


        //coba tampilkan hasilnya di text view
        String[] kota_a = new String[_id.length];
        for(int i=0; i<_id.length; i++){
            kota_a[i] = kota[i];
        }

        adapter = new ArrayAdapter<String>(CariKeberangkatan.this, android.R.layout.simple_spinner_item, kota_a);
        spname.setAdapter(adapter);

        spname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CariKeberangkatan.this, position +1 + "selected", Toast.LENGTH_SHORT).show();
                kota_awal = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void tampilkanKotab() {
        Cursor cursor = queryHelper.readKota();

        int[] _id = new int[cursor.getCount()];
        String[] kota = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            _id[n] = cursor.getInt(0);
            kota[n] = cursor.getString(1);
        }
        cursor.close();


        //coba tampilkan hasilnya di text view
        String[] kota_b = new String[_id.length];
        for(int i=0; i<_id.length; i++){
            kota_b[i] = kota[i];
        }

        adapter = new ArrayAdapter<String>(CariKeberangkatan.this, android.R.layout.simple_spinner_item, kota_b);
        spname1.setAdapter(adapter);

        spname1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kota_tujuan = position + 1;
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
