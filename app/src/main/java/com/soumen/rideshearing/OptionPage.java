package com.soumen.rideshearing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OptionPage extends AppCompatActivity {
Button btnSignUp,btnSignIn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_option_page);

        btnSignIn  =findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v->{
            Intent intent = new Intent(OptionPage.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignIn.setOnClickListener(v->{
            Intent intent=new Intent(OptionPage.this,SignInActivity.class);
            startActivity(intent);
            finish();
        });

    }
}