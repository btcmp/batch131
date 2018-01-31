package com.example.android.cinemax.cinemax;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        SessionManager sessionManager;
        final SQLiteDbHelper dbHelper;
        final QueryHelper queryHelper;
        final Context context;
        Cursor cursor;
        int IdUser;
        String nama, phones, passwords;

        final EditText p_nama, p_pass2, p_pass1;
        TextView p_IdUser;
        Button but_Update;
        final int ambil;
        final String ambil2,ambil3;

            context = this;
            sessionManager = new SessionManager(getApplicationContext());
            dbHelper = new SQLiteDbHelper(context);
            queryHelper = new QueryHelper(dbHelper);
            Intent getId = (Intent) getIntent();
            ambil = getId.getExtras().getInt("id_user");
            ambil2=getId.getExtras().getString("name");
            ambil3=getId.getExtras().getString("email");


            p_IdUser = (TextView) findViewById(R.id.p_IdUser);
            p_nama = (EditText) findViewById(R.id.p_Name);
            p_pass1 = (EditText) findViewById(R.id.Password1);
            p_pass2 = (EditText) findViewById(R.id.Password2);

//            cursor = queryHelper.loginId(ambil);
//            if (cursor.getCount()>0){
//                cursor.moveToFirst();
//                p_IdUser.setText(cursor.getString(0));
//                p_IdUser.setVisibility(View.GONE);
                p_nama.setText(ambil2);
                p_pass1.setText(ambil3);
                p_pass2.setText(ambil3);
//            }


            but_Update = (Button) findViewById(R.id.b_UpdateAccount);
            but_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confim");
                    builder.setMessage("Are you Sure to update Account ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            cursor = queryHelper.loginId(ambil);
                            if (p_pass1.getText().toString().matches(p_pass2.getText().toString())){
                                SQLiteDatabase update = dbHelper.getWritableDatabase();
                                update.execSQL("UPDATE user SET name='" +
                                        p_nama.getText().toString() + "', password='" +
                                        p_pass1.getText().toString() + "' WHERE _id = '" + ambil + "'");
                                Toast.makeText(context, "Your Account have Updated \n Please Login Again ", Toast.LENGTH_SHORT).show();
                                Intent pLogin = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(pLogin);
                            }else {

                                Toast.makeText(context, "Password Tidak Sama", Toast.LENGTH_SHORT).show();                          }

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }

    }

