package com.example.jhon.smi_logistica.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.jhon.smi_logistica.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProblemOptionsFragment extends Fragment {

    IProblemInterface iProblemInterface;
    String[] data;
    String tag;


    public interface IProblemInterface {
        void OnOptionSetted(String option, String tag);
    }



    public ProblemOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iProblemInterface = (IProblemInterface) context;
        }
        catch (ClassCastException e) {
        throw new ClassCastException(context.toString()
                + " must implement OnHeadlineSelectedListener");
    }
    }


    public void initFragment(String[] data, String tag) {
        this.data = data;
        this.tag = tag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_problem_options, container, false);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        final TextView otroProblema = (EditText) v.findViewById(R.id.otro);
        otroProblema.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iProblemInterface.OnOptionSetted(s.toString(),tag);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item,data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Otro")) {
                    otroProblema.setVisibility(View.VISIBLE);
                } else {
                    otroProblema.setVisibility(View.GONE);
                    iProblemInterface.OnOptionSetted(parent.getItemAtPosition(position).toString(),tag);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

}
