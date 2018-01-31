package android.bootcamp.xsisa.hjbusbooking.konfirmasi;

import android.bootcamp.xsisa.hjbusbooking.R;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    private Button submitBtn;
    private TextView codeBooking;
    private EditText nameField, totalTransfer, norekField;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    private int _idPemesanan;
    private int _codeBooking;
    private String _idPemesan;
    private int[] data_code, data_price, data_transfer;

    Spinner bankField;
    ArrayAdapter<String> adapter;

    String[] listBank = {"Mandiri", "BNI", "BCA", "BRI"};
    String bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title3);
        setContentView(R.layout.activity_confirm);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        codeBooking = (TextView) findViewById(R.id.codeField);
        nameField = (EditText) findViewById(R.id.nameField);
        bankField = (Spinner) findViewById(R.id.bankField);
        totalTransfer = (EditText) findViewById(R.id.totalField);
        norekField = (EditText) findViewById(R.id.norekField);

        adapter = new ArrayAdapter<String>(ConfirmActivity.this, android.R.layout.simple_spinner_item, listBank);
        bankField.setAdapter(adapter);

        bankField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank = listBank[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Intent intentCon = (Intent) getIntent();
        _codeBooking = intentCon.getExtras().getInt("codeBooking");
        _idPemesanan = intentCon.getExtras().getInt("idPemesanan");
        _idPemesan = intentCon.getExtras().getString("session");

        codeBooking.setText(String.valueOf(_codeBooking));

        submitBtn = (Button) findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String an_pembayar = nameField.getText().toString();
                final String transfer = totalTransfer.getText().toString();
                final String norek = norekField.getText().toString();

                if (TextUtils.isEmpty(an_pembayar)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Nama Pembayar!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(bank)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Nama Bank!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(norek)) {
                    Toast.makeText(getApplicationContext(), "Masukkan No. Rekening!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(transfer)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Jumlah Transfer!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //QueryHelper (.detailHistoryBooking) berdasarkan id pemesan
                Cursor cursor = queryHelper.detailPayment(_idPemesanan);
                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase update = dbHelper.getWritableDatabase();
                    update.execSQL("UPDATE pemesanan SET " +
                            "atas_nama_pembayaran = '" + an_pembayar + " ', " +
                            "tipe_pembayaran = ' " + bank + " ', " +
                            "total_transfer = ' " + transfer + " ', " +
                            "nomor_rekening = ' " + norek + " ', " +
                            "status_konfirmasi = '" + "Confirmed" + "' " +
                            "where pemesanan.kode_pesan = '" + _codeBooking + "' ");

                    Cursor cursor1 = queryHelper.detailPayment(_idPemesanan);
                    data_code = new int[cursor1.getCount()];
                    data_price = new int[cursor1.getCount()];
                    data_transfer = new int[cursor1.getCount()];
                    cursor1.moveToFirst();
                    for (int cc = 0; cc < cursor1.getCount(); cc++) {
                        cursor1.moveToPosition(cc);
                        data_code[cc] = cursor1.getInt(1);
                        data_price[cc] = cursor1.getInt(8);
                        data_transfer[cc] = cursor1.getInt(9);
                    }
                    cursor1.close();

                    if (data_price[0] == data_transfer[0]) {
                        Toast.makeText(getApplicationContext(), "Jumlah Transfer Sesuai!", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), DetailBooking.class);
                        in.putExtra("codeBooking", _codeBooking);
                        in.putExtra("idPemesanan", _idPemesanan);
                        in.putExtra("session", _idPemesan);
                        startActivity(in);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Lihat Harga Pada Detail Pemesanan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}

