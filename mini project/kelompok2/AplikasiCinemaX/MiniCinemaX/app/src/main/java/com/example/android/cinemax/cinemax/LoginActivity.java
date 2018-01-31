package com.example.android.cinemax.cinemax;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cinemax.cinemax.DatabaseHelper.QueryHelper;
import com.example.android.cinemax.cinemax.DatabaseHelper.SQLiteDbHelper;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Cursor cursor, cursor2;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    SessionManager session;
    TextView textView;
    EditText editTextEmail, editTextPassword;
    Button buttonSignUp, buttonLogIn;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager( getApplicationContext());

        mContext = this;

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        dbHelper = new SQLiteDbHelper(this);
        queryHelper = new QueryHelper(dbHelper);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonLogIn = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
/*        //cek login_status
        cursor2 = queryHelper.readStatus();
        int[] uid = new int[cursor2.getCount()];


        cursor2.moveToFirst();
        for (int n = 0; n < cursor2.getCount(); n++) {
            cursor2.moveToPosition(n);
            uid[n] = cursor2.getInt(0);
        }
        cursor2.close();

        if(cursor2.getCount() > 0){
            Bundle bundle = new Bundle();
            bundle.putString("bSession2",String.valueOf(uid[0]));
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }*/

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, SingupActivity.class);
                startActivity(intent);
            }
        });


        //Login Button click
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                cursor = queryHelper.readUserLogin(email, editTextPassword.getText().toString());
                if (cursor.getCount() >0){
                    cursor.moveToPosition(0);
                    session.createLoginSession(email);
                    String username = cursor.getString(1);
                    Toast.makeText(getApplicationContext(), username + "\n Status Login :"+ session.isLoggedIn(), Toast.LENGTH_SHORT).show();
                    // Staring MainActivity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "SALAH", Toast.LENGTH_SHORT).show();
                }


/*
                int[] uid = new int[cursor.getCount()];

                cursor.moveToFirst();
                for (int n = 0; n<cursor.getCount(); n++){
                    cursor.moveToPosition(n);
                    uid[n] = cursor.getInt(0);
                }
                cursor.close();

                if(cursor.getCount()>0){
                  *//*  SQLiteDatabase db= dbHelper.getWritableDatabase();
                    db.execSQL("update user set status_login='false'");
                    db.execSQL("update user set status_login='true' where _id='"+uid[0]+"'");

*//*
                    Toast.makeText(LoginActivity.this, "Login Berhasil"+uid[0]+"", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("Session", String.valueOf(uid[0]));
                    finish();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Gagal, Username atau Password Salah", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
