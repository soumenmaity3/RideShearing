package com.soumen.rideshearing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class DestinationFragment extends Fragment {

Button btnSubmit;
EditText edtStart,edtDesti;
Context context;
    public DestinationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        EditText edtStart = view.findViewById(R.id.edtStart);
        EditText edtDesti = view.findViewById(R.id.edtEnd);
        Button btnSubmit = view.findViewById(R.id.btnSubmit4);

        btnSubmit.setOnClickListener(v -> {
            String start = edtStart.getText() != null ? edtStart.getText().toString().trim() : "";
            String end = edtDesti.getText() != null ? edtDesti.getText().toString().trim() : "";

            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(getContext(), "Please enter both locations", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(requireContext(), CarChoiceActivity.class);
            intent.putExtra("START_LOCATION", start);
            intent.putExtra("END_LOCATION", end);
            startActivity(intent);
        });

        return view;
    }
}