package android.bootcamp.xsisa.hjbusbooking;

import android.app.ActivityOptions;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SessionManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    //attribut
    private SessionManager session;
    private TimerTask timerTask ;
    private long jedaWaktu = 5000;
    private ProgressBar progressBar;
    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    private ImageView imageView;
    private Context mContext;
    private Cursor cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        dbHelper = new SQLiteDbHelper(SplashScreen.this);
        queryHelper = new QueryHelper(dbHelper);
        session = new SessionManager(SplashScreen.this);
        importDbFromSQLFile();
        //Cek Status Login
        cursor2 = queryHelper.cekEmail(session.email());
        final int[] fid = new int[cursor2.getCount()];

        cursor2.moveToFirst();
        for (int n = 0; n < cursor2.getCount(); n++) {
            cursor2.moveToPosition(n);
            fid[n] = cursor2.getInt(0);
        }
        cursor2.close();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                finish();

                Bundle bundle = ActivityOptions.makeCustomAnimation(getBaseContext(),
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle();
                if (session.isLoggedIn() == true){
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("session",String.valueOf(fid[0]));
                    Log.d("session",String.valueOf(fid[0]));
                    Intent lTrue = new Intent(SplashScreen.this, MainActivity.class);
                    lTrue.putExtras(bundle1);
                    startActivity(lTrue, bundle);

                }else if(session.isLoggedIn()== false){
                    Intent lFalse = new Intent(SplashScreen.this, SignUp.class);
                    startActivity(lFalse, bundle);
                }

            }
        };

        //pasang progressbar
        progressBar = findViewById(R.id.progressBar);
        ThreeBounce tb = new ThreeBounce();
        tb.setColor(Color.parseColor("#F24D00"));
        progressBar.setIndeterminateDrawable(tb);

        //setWaktu
        Timer timer = new Timer();
        timer.schedule(timerTask,jedaWaktu);


        //import database

    }

    public void importDbFromSQLFile(){
        try {
            dbHelper.createDatabaseFromImportedSQL();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "importDbFromSQLFile IOException : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
