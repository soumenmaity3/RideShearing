package com.soumen.rideshearing;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView imgNetSlow;
    ProgressBar pgBar;
    Handler handler;
    Runnable networkCheckRunnable;
    boolean isInternetAvailable = false;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgNetSlow = findViewById(R.id.imgNetSlow);
        pgBar = findViewById(R.id.progress_circular);
        handler = new Handler();
        startTime = System.currentTimeMillis();

        networkCheckRunnable = new Runnable() {
            @Override
            public void run() {
                if (NetWorkUtils.isInternetAvailable(MainActivity.this)) {
                    isInternetAvailable = true;
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long remainingTime = 4000 - elapsedTime;

                    if (remainingTime > 0) {
                        handler.postDelayed(this, remainingTime);
                    } else {
                        startActivity(new Intent(MainActivity.this, OptionPage.class));
                        finish();
                    }
                } else {
                    imgNetSlow.setVisibility(VISIBLE);
                    Toast.makeText(MainActivity.this, "Error Network", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 2000);
                }
            }
        };

        handler.post(networkCheckRunnable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && networkCheckRunnable != null) {
            handler.removeCallbacks(networkCheckRunnable);
        }
    }
}
