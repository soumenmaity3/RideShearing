package com.soumen.rideshearing;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    String email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent getData = getIntent();

// Check if the intent has extras
        name = getData.hasExtra("name") ? getData.getStringExtra("name") : "Default Name";
        email = getData.hasExtra("email") ? getData.getStringExtra("email") : "Default Email";


        bottomNav = findViewById(R.id.navBar);
        loadFragment(new DestinationFragment(), true);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menuProfile) {
                    loadFragment(new ProfileFragment(), false);
                } else if (id == R.id.menuHistory) {
                    loadFragment(new HistoryFragment(), false);
                } else {
                    loadFragment(new DestinationFragment(), false);
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            Fragment currentFragment = fm.findFragmentById(R.id.container);
            if (!(currentFragment instanceof DestinationFragment)) {
                bottomNav.setSelectedItemId(R.id.menuDestination);
            } else {
                super.onBackPressed();
            }
        }
    }


    public void loadFragment(Fragment fg, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fg instanceof ProfileFragment) {
            // Create a bundle and add data
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("email", email);

            // Set arguments in the fragment
            fg.setArguments(bundle);
        }
        ft.replace(R.id.container, fg);

        if (flag) {
            ft.addToBackStack(null);
        }

        ft.commit();
    }


}