package com.soumen.rideshearing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
Button btnSubmit2;
TextView txtSignUp;
EditText edtSEmail,edtSPassword;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSEmail=findViewById(R.id.edtSName);
        edtSPassword=findViewById(R.id.edtSPassword);
        btnSubmit2=findViewById(R.id.btnSubmit2);
        txtSignUp=findViewById(R.id.signUp);

        btnSubmit2.setOnClickListener(v->{
            isAvailable();
        });

        txtSignUp.setOnClickListener(v->{
            Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void isAvailable(){
        if (!emailChecker()||!passwordChecker()){
            return;
        }

        RequestQueue requestQueue= Volley.newRequestQueue(SignInActivity.this);
        String url="http:192.168.128.150:8080/user/login";
        Map<String,String>params=new HashMap<>();
        params.put("email",edtSEmail.getText().toString());
        params.put("password",edtSPassword.getText().toString());


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name=(String) response.get("name");
                            String email=(String) response.get("email");

                            Intent goProfile=new Intent(SignInActivity.this, HomeActivity.class);
                            goProfile.putExtra("name",name);
                            goProfile.putExtra("email",email);

                            startActivity(goProfile);
                            finish();

                            Toast.makeText(SignInActivity.this, "Profile find", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, "Profile Not find", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public boolean emailChecker() {
        String email = edtSEmail.getText().toString();
        if (email.isEmpty()) {
            edtSEmail.setError("Name can't be empty.");
            return false;
        } else {
            return true;
        }
    }

    public boolean passwordChecker() {
        String password = edtSPassword.getText().toString().trim();

        if (password.isEmpty()) {
            edtSPassword.setError("Password can't be empty.");
            return false;
        } else {
            return true;  // Validation successful
        }

    }
}