package android.bootcamp.xsisa.hjbusbooking.konfirmasi;

import android.bootcamp.xsisa.hjbusbooking.MainActivity;
import android.bootcamp.xsisa.hjbusbooking.R;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TiketSementara extends AppCompatActivity {

    private TextView codeBooking, date, time, total;
    private Button finishBtn, cancelBtn;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    private int _idPemesanan;
    private int _idPemesan;
    String tanggalPesan;
    String jamPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title1);
        setContentView(R.layout.activity_tiket_sementara);
        dbHelper = new SQLiteDbHelper(getApplicationContext());
        queryHelper = new QueryHelper(dbHelper);

        Intent c = getIntent();
        _idPemesanan = c.getExtras().getInt("idPemesanan");
        _idPemesan = c.getExtras().getInt("idUser");
        tanggalPesan = c.getExtras().getString("tanggalPesan");
        jamPesan = c.getExtras().getString("jamPesan");

        codeBooking = (TextView) findViewById(R.id.codeField);
        date = (TextView) findViewById(R.id.dateField);
        time = (TextView) findViewById(R.id.timeField);
        total = (TextView) findViewById(R.id.totalField);

        //Code Booking
        Random random = new Random();
        int ran = random.nextInt(10000) + 1;
        String myString = String.valueOf(ran);
        codeBooking.setText(myString);

        final int codeBooked;
        codeBooked = Integer.parseInt((codeBooking.getText().toString()));

        //Calendar
        String myTime = jamPesan;
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        String me = simpleDateFormat.format(calendar.getTime());
        Date myDate = null;
        try {
            myDate = simpleDateFormat.parse(myTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(myDate);
        calendar.add(Calendar.MINUTE, 120);
        final String newTime = simpleDateFormat.format(calendar.getTime());
        time.setText(newTime);

        final String maxTimeConfirm;
        maxTimeConfirm = String.valueOf(time.getText().toString());

        date.setText(tanggalPesan);

        //update Code Booking
        queryHelper.updatePemesanan(codeBooked, _idPemesanan);

        finishBtn = (Button) findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = queryHelper.readPemesanan();

                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    queryHelper.updateTimeMax(maxTimeConfirm, _idPemesanan);
                }
                Intent intent2 = new Intent(TiketSementara.this, HistoryBooking.class);
                String pemesananId = _idPemesan + "";
                intent2.putExtra("session", pemesananId);
                startActivity(intent2);
                finish();
            }
        });

        temporaryTicket();
    }

    private void temporaryTicket() {
        //QueryHelper (.detailHistoryBooking) berdasarkan id pemesan
        Cursor cursor = queryHelper.detailPayment(_idPemesanan);

        int[] tab_booking = new int[cursor.getCount()];
        String[] date_booking = new String[cursor.getCount()];
        String[] time_booking = new String[cursor.getCount()];
        int[] price_total = new int[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);

            tab_booking[n] = cursor.getInt(0);
            date_booking[n] = cursor.getString(5);
            time_booking[n] = cursor.getString(6);
            price_total[n] = cursor.getInt(8);
        }
        cursor.close();

//        date.setText(date_booking[0]);
//        time.setText(time_booking[0]);
        total.setText(String.valueOf(price_total[0]));

    }

    @Override
    public void onBackPressed() {
        Intent f = new
                Intent(getApplicationContext(), MainActivity.class);
        f.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        f.putExtra("session", _idPemesan);
        startActivity(f);
    }
}