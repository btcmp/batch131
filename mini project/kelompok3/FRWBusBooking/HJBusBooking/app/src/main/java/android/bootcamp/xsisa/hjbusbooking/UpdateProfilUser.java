package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pchmn.androidverify.Form;
import com.pchmn.androidverify.InputValidator;

public class UpdateProfilUser extends AppCompatActivity {

    //attribut
    private EditText eNama,eEmail,eNoktp,eNoHP,eAlamat;
    private CheckBox checkBox;
    private InputValidator emailValidator;
    private Button ton1;
    private TextView session;
    private SQLiteDbHelper dbHelper;
    private Cursor cursor;

    private String session1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Data Diri");
        setContentView(R.layout.activity_update_profil_user);

    //inisialisasi
        dbHelper = new SQLiteDbHelper(this);

        session = findViewById(R.id.tSessionU);


        //ambil session
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String bSession = bundle.getString("session");
            if(bSession!=null){
                session1=bSession;
                session.setText(bSession);
                session.setVisibility(View.INVISIBLE);
            }
        }

        final Bundle bSession3 = new Bundle();
        bSession3.putString("session", session1);


        eNama = findViewById(R.id.tnama1);
        eEmail = findViewById(R.id.temail1);
        eNoktp = findViewById(R.id.tnoktp1);
        eNoHP = findViewById(R.id.tnohp1);
        eAlamat = findViewById(R.id.talamat1);
        checkBox = findViewById(R.id.cbUpdate);


        //tampilkan data user
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("Select * From user Where _id='"+session1+"'",null);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            eNama.setText(cursor.getString(1).toString());
            eEmail.setText(cursor.getString(2).toString());
            eNoktp.setText(cursor.getString(4).toString());
            eNoHP.setText(cursor.getString(5).toString());
            eAlamat.setText(cursor.getString(6).toString());
        }

        emailValidator = new InputValidator.Builder(UpdateProfilUser.this)
                .on(eEmail)
                .required(true)
                .validatorType(InputValidator.IS_EMAIL)
                .build();

        final Form form = new Form.Builder(UpdateProfilUser.this)
                .addInputValidator(emailValidator)
                .showErrors(true).build();

        ton1 = findViewById(R.id.save1);

        //jika button update ditekan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cek validasi
                if(form.isValid()){

                    if(checkBox.isChecked()){
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        db.execSQL("UPDATE "+dbHelper.TABLE_NAME+" SET "
                                +dbHelper.COL_NAME+" = '"+eNama.getText().toString()+"', "
                                +dbHelper.COL_EMAIL+" = '"+eEmail.getText().toString()+"', "
                                +dbHelper.COL_KTP+" = '"+eNoktp.getText().toString()+"', "
                                +dbHelper.COL_TELP+" = '"+eNoHP.getText().toString()+"', "
                                +dbHelper.COL_ALAMAT+"= '"+eAlamat.getText().toString()+"' where "+dbHelper.COL_ID+" = '"+session1+"'");

                        finish();
                        Toast.makeText(getApplicationContext(), "Update Data Diri Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateProfilUser.this,MainActivity.class);
                        intent.putExtras(bSession3);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Centang check box untuk memperbaharui data anda!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Isi Data Diri Gagal", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
