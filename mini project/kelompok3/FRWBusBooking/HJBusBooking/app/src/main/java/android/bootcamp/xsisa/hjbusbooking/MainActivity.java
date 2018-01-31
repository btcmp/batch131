package android.bootcamp.xsisa.hjbusbooking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SessionManager;
import android.bootcamp.xsisa.hjbusbooking.fragment_help_about.TentangBis;
import android.bootcamp.xsisa.hjbusbooking.konfirmasi.HistoryBooking;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.marcoscg.dialogsheet.DialogSheet;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAG1 = "menu utama";
    private static final String TAG_FRAG2 = "data diri";
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_FRAG1;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private android.support.v7.widget.Toolbar toolbar;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;
    private SQLiteDatabase db;
    private SessionManager session1;

    Cursor cursor;
    String session;


    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                android.support.v4.app.Fragment fragment = getHomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private android.support.v4.app.Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                About kontak = new About();
                return kontak;
            default:
                return new About();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.win_frmain:
                        Intent i1 = new Intent(MainActivity.this, CariKeberangkatan.class);
                        i1.putExtra("session", session);
                        startActivity(i1);
                        drawer.closeDrawers();
                        return true;
                    case R.id.win_fr2:
                        dialogUpdateProfil();
                        drawer.closeDrawers();
                        return true;
                    case R.id.win_fr3:
                        Intent i2 = new Intent(MainActivity.this, HistoryBooking.class);
                        i2.putExtra("session", session);
                        startActivity(i2);
                        drawer.closeDrawers();
                        return true;
                    case R.id.win_fr4:
                        startActivity(new Intent(MainActivity.this, TentangBis.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.win_fr5:
                        startActivity(new Intent(MainActivity.this, Kontak.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.win_logout:
                        logoutUser();

                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new SQLiteDbHelper(MainActivity.this);
        importDbFromSQLFile();
        queryHelper = new QueryHelper(dbHelper);
        session1 = new SessionManager(MainActivity.this);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        activityTitles = getResources().getStringArray(R.array.activity_titles);

        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_FRAG1;
//            startActivity(new Intent(MainActivity.this, CariKeberangkatan.class));
            loadHomeFragment();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String bSession4 = bundle.getString("session");
            if (bSession4 != null) {
                session = bSession4;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_FRAG1;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }


    private void importDbFromSQLFile() {
        try {
            dbHelper.createDatabaseFromImportedSQL();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "importDbFromSQLFile IOException : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void dialogUpdateProfil() {

        final CharSequence[] dialogitem = {"Ubah Data Diri", "Ubah Password", "Hapus Akun Ini"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Update Profil");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, UpdateProfilUser.class);
                        intent.putExtra("session", session);
                        startActivity(intent);
                        break;
                    case 1:
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.ubah_password);
                        dialog.setTitle("Ubah Password");
                        //inisialisasi
                        TextView tubEmail = dialog.findViewById(R.id.upemail);
                        final EditText eubPass = dialog.findViewById(R.id.tuppass);
                        final EditText eubRpass = dialog.findViewById(R.id.tupretype);
                        final CheckBox cbub = dialog.findViewById(R.id.cbUbahPass);
                        Button bub = dialog.findViewById(R.id.bubah);

                        tubEmail.setText(session1.email());

                        bub.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (cbub.isChecked() && String.valueOf(eubPass.getText().toString()).matches(eubRpass.getText().toString())) {

                                    if (eubPass.getText().toString().length() > 7) {

                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        db.execSQL("UPDATE " + dbHelper.TABLE_NAME + " SET "
                                                + dbHelper.COL_PASS + "= '" + eubPass.getText().toString() + "' where " + dbHelper.COL_ID + " = '" + session + "'");

                                        Toast.makeText(getApplicationContext(), "Ubah Password Berhasil", Toast.LENGTH_LONG).show();

                                        dialog.hide();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Minimal 8 Karakter", Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    Toast.makeText(getApplicationContext(), "Anda Belum Menyetujui Persyaratan atau Password dan Retype Password Tidak Sama", Toast.LENGTH_LONG).show();
                                }


                            }
                        });

                        dialog.show();
                        break;
                    case 2:
                        final DialogSheet dialogSheet = new DialogSheet(MainActivity.this)
                                .setMessage("Anda akan menghapus akun anda dan perlu membuat kemabali agar dapat masuk kembali")
                                .setCancelable(false)
                                .setBackgroundColor(Color.RED)
                                .setButtonsColorRes(R.color.colorPrimary)
                                .setPositiveButton("Yes", new DialogSheet.OnPositiveClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("delete from detail_pemesanan where detail_pemesanan.id_pemesanan = " +
                                                "(select pemesanan._id from pemesanan where pemesanan.id_pemesan='"+session+"')");
                                        db.execSQL("delete from pemesanan where pemesanan.id_pemesanan = " +
                                                "'"+session+"'");
                                        db.execSQL("Delete from " + dbHelper.TABLE_NAME + " Where "
                                                + dbHelper.COL_ID + " = '" + session + "'");
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Akun Anda Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(MainActivity.this, SignUp.class);
                                        startActivity(intent1);
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setTitle("Delete Account");

                        dialogSheet.show();


                        break;

                }
            }
        });
        builder.create().show();
    }

    private void logoutUser() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("update user set status_login='false'");
        session1.logoutUser();
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}
