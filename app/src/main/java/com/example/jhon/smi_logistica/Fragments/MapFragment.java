package com.example.jhon.smi_logistica.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhon.smi_logistica.R;
import com.google.android.gms.maps.GoogleMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    public GoogleMap map;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

}
