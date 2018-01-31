package android.bootcamp.xsisa.hjbusbooking;

import android.app.Dialog;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marcoscg.dialogsheet.DialogSheet;

public class MenuUtama extends AppCompatActivity {

    //attribut
    private Button btLogout,btProfil;
    private TextView session;
    private SQLiteDbHelper dbHelper;
    private SQLiteDatabase db;
    private QueryHelper queryHelper;

    Cursor cursor;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        session = findViewById(R.id.textSession);

        //Ambil Session
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String bSession = bundle.getString("bSession");
            if(bSession != null){
                session.setText(bSession);
                session.setVisibility(View.INVISIBLE);
            }
            String bSession2 = bundle.getString("bSession2");
            if(bSession2 != null){
                session.setText(bSession2);
                session.setVisibility(View.INVISIBLE);
            }
            String bSession3 = bundle.getString("bSession3");
            if(bSession3 != null){
                session.setText(bSession3);
                session.setVisibility(View.INVISIBLE);
            }
        }

        final Bundle bSession = new Bundle();
        bSession.putString("bSession4",String.valueOf(session.getText()));


        //Inisialisasi


        mContext = MenuUtama.this;
        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);


        btLogout = findViewById(R.id.logout);
        //Jika button logout ditekan
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getWritableDatabase();
                db.execSQL("update user set status_login='false'");

                Intent intent = new Intent(MenuUtama.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        //Jika button profil ditekan
        btProfil = findViewById(R.id.updateProfil);
        btProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] dialogitem = {"Ubah Data Diri", "Ubah Password", "Hapus Akun Ini"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuUtama.this);
                builder.setTitle("Update Profil");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(MenuUtama.this,UpdateProfilUser.class);
                                intent.putExtras(bSession);
                                startActivity(intent);
                                break;
                            case 1 :
                                final Dialog dialog = new Dialog(MenuUtama.this);
                                dialog.setContentView(R.layout.ubah_password);
                                dialog.setTitle("Ubah Password");
                                //inisialisasi
                                TextView tubEmail = dialog.findViewById(R.id.upemail);
                                final EditText eubPass = dialog.findViewById(R.id.tuppass);
                                final EditText eubRpass = dialog.findViewById(R.id.tupretype);
                                final CheckBox cbub = dialog.findViewById(R.id.cbUbahPass);
                                Button bub = dialog.findViewById(R.id.bubah);


                                //cek status login
                                cursor = queryHelper.readStatus();
                                String[] femail = new String[cursor.getCount()];

                                cursor.moveToFirst();
                                for (int n = 0; n < cursor.getCount(); n++) {
                                    cursor.moveToPosition(n);
                                    femail[n] = cursor.getString(2);
                                }
                                cursor.close();
                                if(cursor.getCount()>0){
                                    tubEmail.setText(String.valueOf(femail[0]));
                                }


                                bub.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(cbub.isChecked() && String.valueOf(eubPass.getText().toString()).matches(eubRpass.getText().toString())){

                                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                                            db.execSQL("UPDATE "+dbHelper.TABLE_NAME+" SET "
                                                    +dbHelper.COL_PASS+"= '"+eubPass.getText().toString()+"' where "+dbHelper.COL_ID+" = '"+session.getText()+"'");

                                            Toast.makeText(getApplicationContext(), "Ubah Password Berhasil", Toast.LENGTH_LONG).show();

                                            dialog.hide();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Anda Belum Menyetujui Persyaratan atau Password dan Retype Password Tidak Sama", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                dialog.show();
                                break;
                            case 2 :
                                final DialogSheet dialogSheet = new DialogSheet(MenuUtama.this)
                                        .setMessage("Anda akan menghapus akun anda dan perlu membuat kemabali agar dapat masuk kembali")
                                        .setCancelable(false)
                                        .setBackgroundColor(Color.RED)
                                        .setButtonsColorRes(R.color.colorPrimary)
                                        .setPositiveButton("Yes", new DialogSheet.OnPositiveClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                                db.execSQL("Delete from "+dbHelper.TABLE_NAME+" Where "
                                                        +dbHelper.COL_ID+" = '"+session.getText()+"'");
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Akun Anda Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                                Intent intent1 = new Intent(MenuUtama.this,SignUp.class);
                                                startActivity(intent1);
                                            }
                                        })
                                        .setNegativeButton("Cancel",null)
                                        .setTitle("Delete Account");

                                dialogSheet.show();

                                break;
                        }
                    }
                });
                builder.create().show();
//
            }
        });

    }
}
