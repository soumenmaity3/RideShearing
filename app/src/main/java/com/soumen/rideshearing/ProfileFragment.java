package com.soumen.rideshearing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ProfileFragment extends Fragment {
    TextView txtName, txtEmail;
    Button btnSubmit3, btnDelete;
    Context context;
    String email;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        txtName = v.findViewById(R.id.txtName);
        txtEmail = v.findViewById(R.id.txtEmail);
        btnSubmit3 = v.findViewById(R.id.btnSubmit3);
        btnDelete = v.findViewById(R.id.btnDeleteAccount);

        // Retrieve arguments from the fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            email = bundle.getString("email");

            // Set text to TextViews
            txtName.setText(name);
            txtEmail.setText(email);
        }

        btnSubmit3.setOnClickListener((view) -> {
            Intent intent = new Intent(context, OptionPage.class);
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });

        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirm")
                    .setMessage("Delete from Database.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
                            String url = "http://192.168.255.150:8080/user/delete?email="+ Uri.encode(email);
                            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(requireContext(), SignUpActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(requireContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();

                                }
                            });

                            requestQueue.add(stringRequest);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            AlertDialog alert=builder.create();
            alert.show();
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Toast.makeText(getActivity(), "Back pressed in Fragment", Toast.LENGTH_SHORT).show();
                        // You can also navigate back manually
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                }
        );
        return v;
    }
}