package com.example.android.cinemax.cinemax;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Rhandy on 1/14/2018.
 */

public class dinamisfragment extends android.app.Fragment {

        private TextView tvDetailFragment;
        private String detail;

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_dinamis, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            tvDetailFragment = (TextView) view.findViewById(R.id.text_view_detail_fragment);
            tvDetailFragment.setText(getDetail());
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

