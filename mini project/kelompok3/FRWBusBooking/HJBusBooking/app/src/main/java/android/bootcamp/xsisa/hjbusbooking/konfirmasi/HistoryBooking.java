package android.bootcamp.xsisa.hjbusbooking.konfirmasi;

import android.bootcamp.xsisa.hjbusbooking.MainActivity;
import android.bootcamp.xsisa.hjbusbooking.R;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.QueryHelper;
import android.bootcamp.xsisa.hjbusbooking.databaseserver.SQLiteDbHelper;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryBooking extends AppCompatActivity {

    private SQLiteDbHelper dbHelper;
    private QueryHelper queryHelper;

    TextView destinationField, deperatureField, dateField;

    ListView myListView;
    int[] data_code, data_codeBooking;
    String[] data_date, city_dep, city_des;
    private String _idPemesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle(R.string.tittle3);
        setContentView(R.layout.activity_history_booking);

        dbHelper = new SQLiteDbHelper(getApplicationContext());
        queryHelper = new QueryHelper(dbHelper);

        Intent history = getIntent();
        _idPemesan = history.getExtras().getString("session");

        //QueryHelper (.detailHistoryBooking) berdasarkan id pemesan
        Cursor cursor = queryHelper.detailHistoryBooking(Integer.parseInt(_idPemesan));
        Log.d("jumlah", cursor.getCount() + "");
        data_code = new int[cursor.getCount()];
        data_codeBooking = new int[cursor.getCount()];
        data_date = new String[cursor.getCount()];
        city_dep = new String[cursor.getCount()];
        city_des = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            data_code[cc] = cursor.getInt(0);
            data_codeBooking[cc] = cursor.getInt(1);
            city_dep[cc] = cursor.getString(3);
            city_des[cc] = cursor.getString(4);
            data_date[cc] = cursor.getString(5);
        }
        cursor.close();

        myListView = (ListView) findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter();
        myListView.setAdapter(customAdapter);
//        myListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data_date));
        myListView.setSelected(true);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int selection = data_code[i];
                Intent intentHis = new Intent(getApplicationContext(), DetailBooking.class);
                intentHis.putExtra("codeBooking", data_codeBooking[i]);
                intentHis.putExtra("idPemesanan", selection);
                intentHis.putExtra("session", _idPemesan);
                startActivity(intentHis);
            }
        });
//        ((ArrayAdapter) myListView.getAdapter()).notifyDataSetInvalidated();
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return city_dep.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_view_history, null);
            deperatureField = view.findViewById(R.id.departureField);
            destinationField = view.findViewById(R.id.destinationField);
            dateField = view.findViewById(R.id.dateField);

            dateField.setText(data_date[i]);
            deperatureField.setText(city_dep[i]);
            destinationField.setText(city_des[i]);

            return view;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new
                Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("session", _idPemesan);
        startActivity(i);

    }
}
