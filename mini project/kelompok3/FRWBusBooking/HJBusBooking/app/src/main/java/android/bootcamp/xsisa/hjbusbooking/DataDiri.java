package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SessionManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pchmn.androidverify.Form;

public class DataDiri extends AppCompatActivity {

    //Atribut

    private SessionManager session;
    private SQLiteDbHelper dbHelper;
    private Button ton1;
    private EditText text1, text2, text3;
    private Cursor cursor2;
    private QueryHelper queryHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lengkapi Data Diri");
        setContentView(R.layout.activity_data_diri);

        //inisialisasi

        //inisialisasi

        session = new SessionManager(DataDiri.this);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);
        text1 = findViewById(R.id.tnoktp);
        text2 = findViewById(R.id.tnohp);
        text3 = findViewById(R.id.talamat);
        ton1 = findViewById(R.id.save);


        //Send Session
        cursor2 = queryHelper.cekEmail(session.email());
        final int[] fid = new int[cursor2.getCount()];

        cursor2.moveToFirst();
        for (int n = 0; n < cursor2.getCount(); n++) {
            cursor2.moveToPosition(n);
            fid[n] = cursor2.getInt(0);
        }
        cursor2.close();


        final Form form = new Form.Builder(DataDiri.this)
                .showErrors(true).build();

        //Tombol Masuk ditekan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cek validasi
                if(form.isValid()){

                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    db.execSQL("UPDATE "+dbHelper.TABLE_NAME+" SET "
                            +dbHelper.COL_KTP+" = '"+text1.getText().toString()+"', "
                            +dbHelper.COL_TELP+" = '"+text2.getText().toString()+"', "
                            +dbHelper.COL_ALAMAT+"= '"+text3.getText().toString()+"' where "+dbHelper.COL_ID+" = '"+fid[0]+"'");

                    finish();

                    Bundle bSession3 = new Bundle();
                    bSession3.putString("session", String.valueOf(fid[0]));
                    Log.d("sessiontest",String.valueOf(fid[0]));
                    Intent intent = new Intent(DataDiri.this,MainActivity.class);
                    intent.putExtras(bSession3);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Isi Data Diri Gagal", Toast.LENGTH_LONG).show();

                }


            }
        });

    }
}
