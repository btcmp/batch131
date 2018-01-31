package android.bootcamp.xsisa.hjbusbooking.databaseserver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by XSIS-NB on 1/23/2018.
 */

public class QueryHelper {
    private SQLiteDbHelper dbHelper;

    public QueryHelper(SQLiteDbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    private void openDatabase() throws SQLException {
        dbHelper.openDatabase();
    }

    private void closeDatabase(){
        dbHelper.close();
    }

    /**
     * Deklarasikan macam-macam query disini
     */


    public Cursor readUser(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);

        return cursor;
    }

    public Cursor cekEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email='"+email+"'", null);
        return cursor;
    }


    public Cursor readUserLogin(String email, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email='"+email+"' AND password='"+password+"'", null);

        return cursor;
    }

    public Cursor readStatus(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE status_login = 'true'", null);

        return cursor;
    }

    public Cursor readTrayek(String id_trayek){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM trayek where trayek._id = '"+id_trayek+"'", null);

        return cursor;
    }

    public Cursor readKota(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kota", null);

        return cursor;
    }

    public Cursor readPool(int id_kota){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pool where id_kota = '"+id_kota+"'", null);

        return cursor;
    }

    public Cursor readTrayekPilihan(String kota_asal, String kota_tujuan){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT trayek._id, bus.nomor_bus, trayek.jam_keberangkatan, trayek.harga , kelas.jumlah_seat, kelas.nama_kelas FROM trayek  " +
                "join bus on trayek.id_bus = bus._id " +
                "join kelas on kelas.id_kelas = bus.id_kelas" +
                " where kota_asal = '"+kota_asal+"' and kota_tujuan = '"+kota_tujuan+"'", null);

        return cursor;
    }

    public Cursor readKursiDipesan(int id_trayek, String tanggal_keberangkatan){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(detail_pemesanan.id_detail_pemesanan) as jumlahdipesan from detail_pemesanan" +
                "        join pemesanan on pemesanan._id = detail_pemesanan.id_pemesanan" +
                "        join trayek on pemesanan.id_trayek = trayek._id" +
                "        where trayek._id='"+id_trayek+"'" +
                "        and pemesanan.tanggal_keberangkatan = '"+tanggal_keberangkatan+"'  and pemesanan.status_konfirmasi != 'CANCELED'", null);

        return cursor;

    }

    public Cursor readDetailBus(String id_trayek){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select bus.*, kelas.*, fasilitas.*, trayek.* from trayek " +
                "join bus on trayek.id_bus = bus._id " +
                "join kelas on bus.id_kelas = kelas.id_kelas " +
                "join kelas_fasilitas on kelas_fasilitas.id_kelas = kelas.id_kelas " +
                "join fasilitas on kelas_fasilitas.id_fasilitas = fasilitas._id " +
                " where trayek._id = '"+id_trayek+"'";
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public Cursor readDetailTrayek(String id_trayek){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select trayek.*, kota.*, bus.*, kelas.* from trayek " +
                "join kota on trayek.kota_tujuan = kota._id or trayek.kota_asal=kota._id " +
                "join bus on trayek.id_bus = bus._id " +
                "join kelas on bus.id_kelas = kelas.id_kelas " +
                "where trayek._id = '"+id_trayek+"'";

        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }


    public Cursor readDetailPemesanan(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM detail_pemesanan", null);

        return cursor;
    }

    public Cursor readNomorKursi(int id_trayek, String tanggal_keberangkatan){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select detail_pemesanan.* from detail_pemesanan " +
                " join pemesanan on pemesanan._id = detail_pemesanan.id_pemesanan" +
                " join trayek on pemesanan.id_trayek = trayek._id" +
                " where trayek._id='"+id_trayek+"'" +
                " and pemesanan.tanggal_keberangkatan = '"+tanggal_keberangkatan+"'  and pemesanan.status_konfirmasi != 'CANCELED'", null);

        return cursor;
    }

    public Cursor readIdPemesanan(int id_user, String tanggal_keberangkatan, int id_trayek, String tanggal_pemesanan, String jam_pesan){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pemesanan._id FROM pemesanan where " +
                "id_pemesan = '"+id_user+"' and " +
                "tanggal_keberangkatan = '"+tanggal_keberangkatan+"' and " +
                "id_trayek = '"+id_trayek+"' and " +
                "tanggal_pemesanan = '"+tanggal_pemesanan+"' and " +
                "jam_pesan = '"+jam_pesan+"'", null);

        return cursor;
    }

    public Cursor detailBooking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select pemesanan._id, " +
                "pemesanan.kode_pesan," +
                "user.nama, " +
                "pool.alamat_pool, " +
                "kota.nama_kota, " +
                "pemesanan.tanggal_pemesanan, " +
                "pemesanan.jam_max_konfirmasi, " +
                "pemesanan.tanggal_keberangkatan, " +
                "pemesanan.total_harga, " +
                "pemesanan.total_transfer " +
                "from pemesanan " +
                "join user on pemesanan.id_pemesan = user._id " +
                "join trayek on pemesanan.id_trayek = trayek._id " +
                "join kota on trayek.kota_tujuan = kota._id " +
                "join pool on pemesanan.pool_awal = pool._id";

        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public void updatePemesanan(int codeBooking , int idPemesan) {
        SQLiteDatabase update = dbHelper.getWritableDatabase();
        update.execSQL("UPDATE pemesanan SET kode_pesan = '" + codeBooking + "' where pemesanan._id = '" + idPemesan + "' ");
    }

    public void updateTimeMax(String timeMax , int idPemesan) {
        SQLiteDatabase update = dbHelper.getWritableDatabase();
        update.execSQL("UPDATE pemesanan SET jam_max_konfirmasi = '" + timeMax + "' where pemesanan._id= '" + idPemesan + "' ");
    }

    public void deletePemesanan(int idPemesan) {
        SQLiteDatabase update = dbHelper.getWritableDatabase();
        update.execSQL("DELETE pemesanan where pemesanan._id= '" + idPemesan + "' ");
    }


    public void updateBookingPesanan(String atasNama, String namaBank, int totalTransfer, String status, int idPemesan) {
        SQLiteDatabase update = dbHelper.getWritableDatabase();
        update.execSQL("UPDATE pemesanan SET atas_nama_pembayaran = '" + atasNama + "'," +
                "tipe_pembayaran = ' " + namaBank +" '" +
                "total_transfer = ' " + totalTransfer + " '" +
                "status_konfirmasi = ' " + status + " ' where pemesanan.id_pemesan = '" + idPemesan + "' ");
    }

    public Cursor readPemesanan() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM pemesanan where _id ='1' ", null);

        return cursor1;
    }

    public Cursor detailHistoryBooking(int idPemesan) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select pemesanan._id, " +
                "pemesanan.kode_pesan," +
                "user.nama, " +
                "pool.alamat_pool, " +
                "kota.nama_kota, " +
                "pemesanan.tanggal_pemesanan, " +
                "pemesanan.jam_max_konfirmasi, " +
                "pemesanan.tanggal_keberangkatan, " +
                "pemesanan.total_harga, " +
                "pemesanan.total_transfer " +
                "from pemesanan " +
                "join user on pemesanan.id_pemesan = user._id " +
                "join trayek on pemesanan.id_trayek = trayek._id " +
                "join kota on trayek.kota_tujuan = kota._id " +
                "join pool on pemesanan.pool_awal = pool._id where pemesanan.id_pemesan = '" + idPemesan + "' order by pemesanan._id desc ";

        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public Cursor detailPayment(int idPemesanan) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select pemesanan._id, " +
                "pemesanan.kode_pesan," +
                "user.nama, " +
                "pool.alamat_pool, " +
                "kota.nama_kota, " +
                "pemesanan.tanggal_pemesanan, " +
                "pemesanan.jam_max_konfirmasi, " +
                "pemesanan.tanggal_keberangkatan, " +
                "pemesanan.total_harga, " +
                "pemesanan.total_transfer, " +
                "pemesanan.nomor_rekening " +
                "from pemesanan " +
                "join user on pemesanan.id_pemesan = user._id " +
                "join trayek on pemesanan.id_trayek = trayek._id " +
                "join kota on trayek.kota_tujuan = kota._id " +
                "join pool on pemesanan.pool_awal = pool._id where pemesanan._id= '" + idPemesanan + "' ";

        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    public Cursor detailCodeBooking(int idPemesanan) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "select pemesanan._id, " +
                "pemesanan.kode_pesan," +
                "user.nama, " +
                "pool.alamat_pool, " +
                "kota.nama_kota, " +
                "pemesanan.tanggal_pemesanan, " +
                "pemesanan.jam_max_konfirmasi, " +
                "pemesanan.tanggal_keberangkatan, " +
                "pemesanan.total_harga, " +
                "pemesanan.total_transfer, " +
                "pemesanan.status_konfirmasi " +
                "from pemesanan " +
                "join user on pemesanan.id_pemesan = user._id " +
                "join trayek on pemesanan.id_trayek = trayek._id " +
                "join kota on trayek.kota_tujuan = kota._id " +
                "join pool on pemesanan.pool_awal = pool._id where pemesanan._id = '" + idPemesanan + "' ";

        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

}
