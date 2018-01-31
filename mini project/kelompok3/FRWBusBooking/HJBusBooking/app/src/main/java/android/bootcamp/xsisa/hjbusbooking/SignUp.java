package android.bootcamp.xsisa.hjbusbooking;

import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class SignUp extends AppCompatActivity {

    private TextFieldBoxes tb1,tb2;
    private ExtendedEditText email,password;
    private Button btLogin, btSignup;
    private TextView textView;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    private SessionManager session;


    Cursor cursor, cursor2;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //ubah nama title
        getSupportActionBar().setTitle("Login & Sign Up");

        //Inisiasi

        btLogin = findViewById(R.id.login);
        btSignup = findViewById(R.id.signup);
        email = findViewById(R.id.temail);
        password = findViewById(R.id.tpass);
        tb1 = findViewById(R.id.tbPass);
        tb2 = findViewById(R.id.tbEmail);



        mContext = SignUp.this;

        dbHelper = new SQLiteDbHelper(mContext);
        queryHelper = new QueryHelper(dbHelper);
        session = new SessionManager(SignUp.this);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() > 7){
                    tb1.setError("Its True", true);
                    tb1.setErrorColor(Color.BLACK);
                } else {
                    tb1.setHelperText("Minimal 8 Karakter");
                }
            }
        });

        tb1.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tb1.setEndIcon(R.drawable.eye);
                password.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, FormRegister.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidateEmail(String yourField){
        return !TextUtils.isEmpty(yourField)&& Patterns.EMAIL_ADDRESS.matcher(yourField).matches();
    }


    private boolean isEmptyField(String yourField){

        return !TextUtils.isEmpty(yourField);
    }

    private void loginUser(){
        cursor = queryHelper.readUserLogin(email.getText().toString(), password.getText().toString());

        int[] fid = new int[cursor.getCount()];

        cursor.moveToFirst();
        for (int n = 0; n < cursor.getCount(); n++) {
            cursor.moveToPosition(n);
            fid[n] = cursor.getInt(0);
        }
        cursor.close();



        String emails = email.getText().toString();
        String passwords = password.getText().toString();

        if(!isEmptyField(emails) || !isEmptyField(passwords)) {
            tb2.setErrorColor(Color.RED);
            tb2.setError("Email Tidak Boleh Kosong", true);

            tb1.setErrorColor(Color.RED);
            tb1.setError("Password Tidak Boleh Kosong", true);

        } else if(!isValidateEmail(emails) ) {
            tb2.setErrorColor(Color.RED);
            tb2.setError("Masukan Email dengan Benar",true);
        } else {

            if(cursor.getCount() > 0){

                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                db.execSQL("update user set status_login='false'");
                db.execSQL("update user set status_login='true' where _id='"+fid[0]+"'");
                session.createLoginSession(emails , passwords);
                Toast.makeText(SignUp.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("session",String.valueOf(fid[0]));
                Log.d("sessiontest ",String.valueOf(fid[0]));

                Intent intent = new Intent(SignUp.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else {
                Toast.makeText(SignUp.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
