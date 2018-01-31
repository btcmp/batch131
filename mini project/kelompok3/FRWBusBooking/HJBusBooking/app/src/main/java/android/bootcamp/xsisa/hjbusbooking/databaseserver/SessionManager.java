package android.bootcamp.xsisa.hjbusbooking.databaseserver;

import android.bootcamp.xsisa.hjbusbooking.MainActivity;
import android.bootcamp.xsisa.hjbusbooking.SignUp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by TAM on 1/13/2018.
 */

public class SessionManager {



    //shared Preferences
    SharedPreferences pref ;

    //edittor for Sharef Preferences
    Editor editor;

    //Context
    Context _context ;

    //Shared pred mode
    int  PRIVATE_MODE = 0;

    //Shared file name
    private  static final String PREF_NAME ="KetaXsisRestauran";

    //shared prefereces key
    private static final String IS_LOGIN = "IsLoggedIn";

    //phone key
    public  static final  String KEY_EMAIL = "email";

    //password key
    public  static final  String KEY_PASS = "password";

    //Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    //Login Session
    public void createLoginSession (String phone, String password){
        //storing login login value as true
        editor.putBoolean(IS_LOGIN, true);

        //storing name in pref
     //   editor.putString(KEY_NAME, name);

        //storing phone in pref
        editor.putString(KEY_EMAIL, phone);

        //storing password in pref
        editor.putString(KEY_PASS, password);

        //commit changes
        editor.commit();
    }

    //login checking
    public void checkLogin(){

        if (!this.isLoggedIn()){

            //is not logged willbe directed to Login Activity
            Intent intent = new Intent(_context, SignUp.class);

            //closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //add new flag to start new activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Staring Login Activity
            _context.startActivity(intent);
        }else{
            Intent intent = new Intent(_context, MainActivity.class);
            _context.startActivity(intent);
        }

    }

    //get stored session data
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        return user;

    }

    public String email(){
        String phone =  pref.getString(KEY_EMAIL, null);
        return phone;
    }
    public String pass(){
        String pass =  pref.getString(KEY_PASS, null);
        return pass;
    }

    //Clear Session detail
    public void logoutUser(){
        //clearing all data from shared Preferences
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, SignUp.class);

        //close all Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //add flag to new start activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Starting Login Activity
        _context.startActivity(intent);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
