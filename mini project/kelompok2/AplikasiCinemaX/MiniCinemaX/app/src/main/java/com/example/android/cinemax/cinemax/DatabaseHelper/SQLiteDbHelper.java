package com.example.android.cinemax.cinemax.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.cinemax.cinemax.BuildConfig;
import com.example.android.cinemax.cinemax.modal.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteDbHelper extends SQLiteOpenHelper{
    private Context dContext;
    private SQLiteDatabase myDatabase;

    private static final String DATABASE_PATH = "/data/data/"+BuildConfig.APPLICATION_ID+"/databases/";
    private static final String DATABASE_NAME = "cinemaxxx.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.dContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            deleteDatabase();
        }
    }

    /**
     * check database status is exist or not
     * @return
     */
    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;

        try{
            checkDB = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
            Log.d("SQLiteDbHelper", "Database not exist");
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * create database from imported sql file
     * @throws IOException
     */
    public void createDatabaseFromImportedSQL() throws IOException{
        if(checkDatabase()){
            //database already exist
            Log.d("SQLiteDbHelper", "Database already exist");
        }
        else{
            SQLiteDatabase readDb = this.getReadableDatabase();
            readDb.close();

            try {
                importDatabase();
            } catch (IOException e) {
                Log.d("SQLiteDbHelper", "Error importing database from SQL");
                throw new Error("Error importing database from SQL");
            }
        }
    }

    /**
     * method to importing sql file into sqlite databases
     * using stream
     * @throws IOException
     */
    private void importDatabase() throws IOException{
        //Open your local db as the input stream
        InputStream inputStream = dContext.getAssets().open(DATABASE_NAME);

        //Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream outputStream = new FileOutputStream(outFileName);

        //Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer))>0){
            outputStream.write(buffer, 0, length);
        }

        //Close all streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        Log.d("SQLiteDbHelper", "Importing Database from SQL file success");
    }

    /**
     * Open connection to database
     * @throws SQLException
     */
    public void openDatabase() throws SQLException {
        myDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * Close connection to database
     */
    @Override
    public synchronized void close() {
        if(myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }

    /**
     * delete existing if newer version available
     */
    public void deleteDatabase()
    {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if(file.exists()){
            file.delete();
            Log.d("SQLiteDbHelper", "Older database successfully deleted");
        }
    }
}
