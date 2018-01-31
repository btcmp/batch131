package com.example.android.cinemax.cinemax.DatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
    private SQLiteDbHelper dbHelper;

    public QueryHelper(SQLiteDbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    private void openDatabase(){
        dbHelper.openDatabase();
    }

    private void closeDatabase(){
        dbHelper.close();
    }

    /**
     * Deklarasikan macam-macam query disini
     */



    //query tipe motor
    public Cursor readUser(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);

        return cursor;
    }

    public Cursor readUserLogin(String email, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email='"+email+"' AND password='"+password+"'", null);

        return cursor;
    }

    public Cursor readListFilm(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list_film", null);

        return  cursor;
    }

    public Cursor readListBioskop(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list_bioskop", null);

        return cursor;
    }

//    public Cursor readStatus(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE status_login = 'true'", null);
//
//        return cursor;
//    }

    public  Cursor readListTayangFilm(String nama_bioskop){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select fil.nama_film, jad.jam_1, bio.harga, bio.nama_bioskop, bio._id, jad._id, tay.id_film, bio.img_rsc, fil.img_src from list_tayang tay  join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_bioskop = '"+nama_bioskop+"' order by nama_film", null);

        return cursor;
    }

    public  Cursor readListGambar(String nama_bioskop){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select fil.img_src from list_tayang tay  join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_bioskop = '"+nama_bioskop+"' group by nama_film", null);

        return cursor;
    }

    public Cursor readListFilmTayangBioskop(String nama_film){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select bio.nama_bioskop, jad.jam_1, bio._id, bio.harga, fil.nama_film, jad._id from list_tayang tay  join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_film = '"+nama_film+"'", null);

        return cursor;
    }


    public  Cursor readListGambarAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select img_src, nama_film from list_film order by img_src asc", null);

        return cursor;
    }

    public  Cursor readListFilmDetail(String source_film){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from list_film where nama_film = '"+source_film+"'", null);

        return cursor;
    }

    public Cursor readJamFilm(String nama_bioskop, String nama_film){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select jad.jam_1, bio._id, jad._id, fil.nama_film from list_tayang tay join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_bioskop = '"+nama_bioskop+"' and nama_film = '"+nama_film+"' order by jam_1", null);

        return cursor;
    }

    public Cursor readPemesanan(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pemesanan", null);

        return cursor;
    }

    public Cursor jamPembayaran(int id_jam){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM jadwal_film where _id = '"+id_jam+"'", null);

        return cursor;
    }

    public Cursor ambilIdSaja(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT max(_id) FROM pemesanan", null);

        return cursor;
    }

    public Cursor readJam(int idFilm, int id_bioskop){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM list_tayang join jadwal_film on list_tayang.pemutaran = jadwal_film._id where id_film = '"+idFilm+"' and id_bioskop = '"+id_bioskop+"'", null);

        return cursor;
    }


    public Cursor readNomorKursi(String judul, int id_bioskop, int jam, String tanggal){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select detail_pemesanan.* from detail_pemesanan " +
                " join pemesanan on pemesanan._id = detail_pemesanan.id_pemesanan" +
                " where pemesanan.nama_film='"+judul +"'" +
                " and pemesanan.id_bioskop = '"+id_bioskop+"'" +
                " and pemesanan.tanggal = '"+tanggal+"'" +
                "and pemesanan.id_jadwal = '"+jam+"'", null);

        return cursor;
    }
    public Cursor readStatus(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE status_login = 'true'", null);

        return cursor;
    }
  /*  public Cursor readAlldataPesanan(String id_user){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select psn._id,usr.name,bio.nama_bioskop,psn.nama_film,jad.jam_1,psn.tanggal_pesan,psn.no_rek,psn.status,psn.nama_rek,psn.tanggal from pemesanan psn join user usr on usr._id = psn.id_user join list_bioskop bio on bio._id=psn.id_bioskop join jadwal_film jad on jad._id = psn.id_jadwal where status='TERBAYAR' and id_user ='"+id_user+"'",null);
        return cursor;
    }*/
  public Cursor readAlldataPesanan(String id_user){
      SQLiteDatabase db = dbHelper.getReadableDatabase();
      Cursor cursor = db.rawQuery("select psn._id,usr.name,bio.nama_bioskop,psn.nama_film,jad.jam_1,psn.tanggal_pesan,psn.no_rek,psn.status,psn.nama_rek,psn.tanggal,fil.img_src from pemesanan psn join user usr on usr._id = psn.id_user join list_bioskop bio on bio._id=psn.id_bioskop join jadwal_film jad on jad._id = psn.id_jadwal join list_film fil on fil.nama_film = psn.nama_film where status='TERBAYAR' and id_user ='"+id_user+"'",null);
      return cursor;
  }
    public Cursor liatkursi(String id_user,String tgl, String kode){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select psn.nama_film,bio.nama_bioskop,det.kursi, psn.tanggal from detail_pemesanan det join pemesanan psn on det.id_pemesanan = psn._id join list_bioskop bio on psn.id_bioskop=bio._id where id_user='"+id_user+"'and psn.tanggal='"+tgl+"' and det.id_pemesanan='"+kode+"'",null);
        return cursor;
    }
    public Cursor sesi(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email='"+email+"'",null);
        return cursor;
    }
//    public List<String> readListTayangFilmJam(String nama_film, String nama_bioskop){
//        List<String> list = new ArrayList<>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select jad.jam_1 from list_tayang tay  join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_bioskop = '"+nama_bioskop+"' and nama_film = '"+nama_film+"'", null);
//        cursor.moveToFirst();
//        for (int cc = 0; cc < cursor.getCount(); cc++) {
//            cursor.moveToPosition(cc);
//            list.add(cursor.getString(1));
//        }
//        return list;
//    }
//
//    public  Cursor readListTayangFilmJam(String nama_film, String nama_bioskop){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select jad.jam_1 from list_tayang tay  join list_bioskop bio on tay.id_bioskop = bio._id join list_film fil on tay.id_film = fil._id join jadwal_film jad on tay.pemutaran = jad._id where nama_bioskop = '"+nama_bioskop+"' and nama_film = '"+nama_film+"'", null);
//
//        return cursor;
//    }
}
