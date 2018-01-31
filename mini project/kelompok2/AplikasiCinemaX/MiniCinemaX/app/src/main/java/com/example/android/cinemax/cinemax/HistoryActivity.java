package com.example.android.cinemax.cinemax;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class HistoryActivity extends AppCompatActivity {

    Cursor cursor;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    TextView Bayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);
        Bayar = (TextView)findViewById(R.id.textPem);
        readPemesanan();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void readPemesanan() {
        cursor = queryHelper.readPemesanan();
        String[] no_rek = new String[cursor.getCount()];
        String[] nama_rek = new String[cursor.getCount()];
        String[] status = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            status[i] = cursor.getString(6);
            no_rek[i] = cursor.getString(8);
            nama_rek[i] = cursor.getString(9);
        }
        cursor.close();
        String tampiltext ="";
        for (int i=0; i<no_rek.length; i++){
              tampiltext = no_rek[i]+
                      "\n"+nama_rek[i]+
                      "\n"+status[i]+
                      "\n";
        }
      Bayar.setText(tampiltext);

    }
}
