package com.example.android.cinemax.cinemax;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingupActivity extends AppCompatActivity {
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Context mContext;
    Button buttonSignUpBack, buttonSignUp;
    EditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        dbHelper = new SQLiteDbHelper(this);
        mContext=this;
        queryHelper = new QueryHelper(dbHelper);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        final String textEmail = editTextEmail.getText().toString();

        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        buttonSignUp = (Button) findViewById(R.id.buttonSign);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textEmail = editTextEmail.getText().toString();

                if (!isValidEmail(textEmail)) {
                    editTextEmail.setError("Email Anda Tidak Valid");
                    editTextName.setError("Masukan Nama Anda");
                    editTextPassword.setError("Password Minimal 6 Karakter");
                }

                else if (editTextName.length()>=2 && editTextPassword.length()>=5)
                {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into user(name, email, password)values('"+editTextName.getText().toString()+"','"+editTextEmail.getText().toString()+"', '"+editTextPassword.getText().toString()+"')");

                    final AlertDialog.Builder builder = new AlertDialog.Builder(SingupActivity.this);
                    builder.setTitle("SIGN UP");
                    builder.setMessage("SIGN UP BERHASIL");
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        importDbFromSQLFile();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


     public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    SQLiteDbHelper sqLiteDbHelper = new SQLiteDbHelper(this);

    private void importDbFromSQLFile(){
        try {
            sqLiteDbHelper.createDatabaseFromImportedSQL();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "importDbFromSQLFile IOException : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
