package android.bootcamp.xsisa.hjbusbooking.fragment_help_about;

import android.app.Fragment;
import android.bootcamp.xsisa.hjbusbooking.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Miracles-PC on 25/01/2018.
 */

public class FragmentTwo extends android.support.v4.app.Fragment {

    public FragmentTwo(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }
}
