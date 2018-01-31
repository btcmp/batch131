package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pchmn.androidverify.Form;
import com.pchmn.androidverify.InputValidator;

public class FormRegister extends AppCompatActivity {
    //Attribut
    SQLiteDbHelper dbHelper;
    Button ton1;
    EditText text1, text2, text3, text4;
    SessionManager session;
    QueryHelper queryHelper;

    Context mContext;
    Cursor cursor,cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_registrasi);

        //Inisialisasi
        dbHelper = new SQLiteDbHelper(this);
        text1 = findViewById(R.id.tnama);
        text2 = findViewById(R.id.temail);
        text3 = findViewById(R.id.tpass);
        text4 = findViewById(R.id.tretype);
        ton1 = findViewById(R.id.save);

        //ubah nama title
        getSupportActionBar().setTitle("Form Sign Up");

        //Inisialisasi
        dbHelper = new SQLiteDbHelper(this);
        text1 = findViewById(R.id.tnama);
        text2 = findViewById(R.id.temail);
        text3 = findViewById(R.id.tpass);
        text4 = findViewById(R.id.tretype);
        ton1 = findViewById(R.id.save);

        mContext = this;

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        session = new SessionManager(FormRegister.this);


        InputValidator emailValidator = new InputValidator.Builder(FormRegister.this)
                .on(text2)
                .required(true)
                .validatorType(InputValidator.IS_EMAIL)
                .build();



        final Form form = new Form.Builder(FormRegister.this)
                .addInputValidator(emailValidator)
                .showErrors(true).build();

        //jika button beralih ke data diri ditekan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cek Validasi
                if(form.isValid() && (String.valueOf(text3.getText().toString()).matches(text4.getText().toString()))){

                    cursor2 = queryHelper.cekEmail(text2.getText().toString());
                    session.createLoginSession(text2.getText().toString() , text3.getText().toString());
                    if(cursor2.getCount()>0){
                        Toast.makeText(FormRegister.this,"Email Anda Sudah Terpakai",Toast.LENGTH_LONG).show();
                    } else {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();


                        db.execSQL("insert into "+dbHelper.TABLE_NAME+"("
                                +dbHelper.COL_NAME+","
                                +dbHelper.COL_EMAIL+","
                                +dbHelper.COL_PASS+","
                                +dbHelper.COL_KTP+","
                                +dbHelper.COL_TELP+","
                                +dbHelper.COL_ALAMAT+","
                                +dbHelper.COL_STATUS+") values ('"

                                +text1.getText().toString()+"','"
                                +text2.getText().toString()+"','"
                                +text3.getText().toString()+"',' ',' ',' ','true');");



                        Toast.makeText(getApplicationContext(), "Register Berhasil Silahkan Ubah Data Diri Anda", Toast.LENGTH_LONG).show();
                        finish();

                        cursor = queryHelper.readStatus();

                        int[] fid = new int[cursor.getCount()];

                        cursor.moveToFirst();
                        for (int n = 0; n < cursor.getCount(); n++) {
                            cursor.moveToPosition(n);
                            fid[n] = cursor.getInt(0);
                        }
                        cursor.close();

                        if(cursor.getCount()>0){

                            Bundle bundle = new Bundle();
                            bundle.putString("session",String.valueOf(fid[0]));
//
                            Intent intent = new Intent(FormRegister.this, DataDiri.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }



                }
                else {
                    Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_LONG).show();
                }



            }
        });
    }
}
