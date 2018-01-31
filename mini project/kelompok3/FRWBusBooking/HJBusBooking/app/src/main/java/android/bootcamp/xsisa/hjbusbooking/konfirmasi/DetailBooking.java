package android.bootcamp.xsisa.hjbusbooking.konfirmasi;

import android.bootcamp.xsisa.hjbusbooking.R;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailBooking extends AppCompatActivity {

    private TextView codeBooking, nameBuyer, departure,
            destination, dateBooking, dateDeparture,
            priceTotal, statusConfirm, maxTime;
    private Button confirmBtn, printBtn;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    public int _codeBooking, _idPemesanan;
    private String _idPemesan;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    //External Permission
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title2);
        setContentView(R.layout.activity_detail_booking);

        //External Storage Confirm
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        if (ActivityCompat.checkSelfPermission(DetailBooking.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DetailBooking.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailBooking.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(DetailBooking.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailBooking.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(DetailBooking.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();

        }

        //template pdf
        class MyFooter extends PdfPageEventHelper implements PdfPageEvent {
            Font hfont = new Font(Font.FontFamily.UNDEFINED, 14, Font.BOLD);
            Font ffont = new Font(Font.FontFamily.UNDEFINED, 14, Font.NORMAL);

            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                Phrase header = new Phrase("Detail Tiket", hfont);
                Phrase footer = new Phrase("Harapan Jaya Bus", ffont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        header,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.top() + 10, 0);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);
            }
        }


        dbHelper = new SQLiteDbHelper(getApplicationContext());
        queryHelper = new QueryHelper(dbHelper);

        codeBooking = (TextView) findViewById(R.id.codeField);
        nameBuyer = (TextView) findViewById(R.id.nameField);
        departure = (TextView) findViewById(R.id.departureField);
        destination = (TextView) findViewById(R.id.destinationField);
        dateBooking = (TextView) findViewById(R.id.dateBooking);
        dateDeparture = (TextView) findViewById(R.id.dateGo);
        priceTotal = (TextView) findViewById(R.id.totalField);
        statusConfirm = (TextView) findViewById(R.id.statusConfirm);
//        maxTime = (TextView) findViewById(R.id.timeField);

        printBtn = (Button) findViewById(R.id.printBtn);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        String newTime = simpleDateFormat.format(calendar.getTime());


        Intent intent1 = (Intent) getIntent();
        _codeBooking = intent1.getExtras().getInt("codeBooking");
        _idPemesanan = intent1.getExtras().getInt("idPemesanan");
        _idPemesan = intent1.getExtras().getString("session");

        //Read Table: Pemesanan | QueryHelper (.detailCodeBooking)
        Cursor cursor = queryHelper.detailCodeBooking(_idPemesanan);

        final int[] id_pemesanan = new int[cursor.getCount()];
        final int[] kode_pesan = new int[cursor.getCount()];
        String[] nama = new String[cursor.getCount()];
        String[] kota_awal = new String[cursor.getCount()];
        String[] kota_tujuan = new String[cursor.getCount()];
        String[] tanggal_pesan = new String[cursor.getCount()];
        String[] jam_max = new String[cursor.getCount()];
        String[] tanggal_keberangkatan = new String[cursor.getCount()];
        int[] price_total = new int[cursor.getCount()];
        int[] transfer_total = new int[cursor.getCount()];
        String[] status_konfirmasi = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);

            id_pemesanan[n] = cursor.getInt(0);
            kode_pesan[n] = cursor.getInt(1);
            nama[n] = cursor.getString(2);
            kota_awal[n] = cursor.getString(3);
            kota_tujuan[n] = cursor.getString(4);
            tanggal_pesan[n] = cursor.getString(5);
            jam_max[n] = cursor.getString(6);
            tanggal_keberangkatan[n] = cursor.getString(7);
            price_total[n] = cursor.getInt(8);
            transfer_total[n] = cursor.getInt(9);
            status_konfirmasi[n] = cursor.getString(10);
        }
        cursor.close();

        codeBooking.setText(kode_pesan[0] + "");
        //maxTime.setText(jam_max[0]);
        nameBuyer.setText(nama[0]);
        departure.setText(kota_awal[0]);
        destination.setText(kota_tujuan[0]);
        dateBooking.setText(tanggal_pesan[0]);
        dateDeparture.setText(tanggal_keberangkatan[0]);
        priceTotal.setText(price_total[0] + "");

        //Konfirmasi
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_btn = new Intent(getApplicationContext(), ConfirmActivity.class);
                intent_btn.putExtra("idPemesanan", _idPemesanan);
                intent_btn.putExtra("session", _idPemesan);
                intent_btn.putExtra("codeBooking", kode_pesan[0]);
                startActivity(intent_btn);

            }
        });

        /*String time = String.valueOf(calendar);
        String time1 = maxTime.getText().toString();*/

        if (String.valueOf(status_konfirmasi[0]).equals("Confirmed")) {
            statusConfirm.setTextColor(Color.GREEN);
            statusConfirm.setText("Konfirmasi Berhasil");
            printBtn.setVisibility(View.VISIBLE);
            confirmBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Silahkan Cetak", Toast.LENGTH_SHORT).show();

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document document = new Document();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String yourFilePath = Environment.getExternalStorageDirectory() + "/hj_bus" + "/" + "tiket_" + currentDateTimeString + ".pdf";
                String lokasi = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hj_bus";
                File dir = new File(lokasi);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {

                    int code = _codeBooking;
                    int idPemesan = Integer.parseInt(String.valueOf(_idPemesanan));
                    String buyer = nameBuyer.getText().toString();
                    String depart = departure.getText().toString();
                    String destiny = destination.getText().toString();
                    String dBook = dateBooking.getText().toString();
                    String dDepart = dateDeparture.getText().toString();
                    String sumPrice = priceTotal.getText().toString();

                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(yourFilePath));
                    MyFooter event = new MyFooter();
                    writer.setPageEvent(event);
                    document.open();

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("ID Pemesanan:"));
                    document.add(new Paragraph(String.valueOf(idPemesan)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Kode Booking:"));
                    document.add(new Paragraph(String.valueOf(code)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Nama Pemesan:"));
                    document.add(new Paragraph(String.valueOf(buyer)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Keberangkatan:"));
                    document.add(new Paragraph(String.valueOf(depart)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Tujuan:"));
                    document.add(new Paragraph(String.valueOf(destiny)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Tanggal Pemesanan:"));
                    document.add(new Paragraph(String.valueOf(dBook)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Tanggal Keberangkatan:"));
                    document.add(new Paragraph(String.valueOf(dDepart)));

                    document.add(new Paragraph("\n\n"));
                    document.add(new Paragraph("Total Biaya:"));
                    document.add(new Paragraph(String.valueOf(sumPrice)));

                    document.add(new Paragraph("\n\n\n"));
                    document.add(new Paragraph("Tiket ini merupakan bukti bahwa anda telah menyelesaikan proses pembelian tiket. Terima kasih atas kepercayaannya Anda melakukan perjalanan dengan Harapan Jaya Bus"));

                    document.close();

                    //hak akses berbagi file & temporary grant permission
                    File yourFile = new File(yourFilePath);
                    Uri path = Uri.fromFile(yourFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(path);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*} else if (time.compareTo(newTime) <= time1.compareTo(jam_max[0])) {
            confirmBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Maaf Waktu Konfirmasi Habis. Silahkan Hubungi CS.", Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public void onBackPressed() {
        Intent iBack = new Intent(this, HistoryBooking.class);
        iBack.putExtra("session", _idPemesan );
        startActivity(iBack);
        finish();
    }

}
