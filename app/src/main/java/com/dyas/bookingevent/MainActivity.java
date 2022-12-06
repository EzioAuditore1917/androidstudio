package com.dyas.bookingevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView buttonNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        buttonNavigationBar = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
            fragmentTransaction.commit();
        }
        buttonNavigationBar.setOnItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
                    fragmentTransaction.commit();
                    break;
                case R.id.navigation_ticket:
                    TicketFragment ticketFragments = new TicketFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, ticketFragments);
                    fragmentTransaction.commit();
                    break;

                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, profileFragment);
                    fragmentTransaction.commit();
                    break;
            }

            return true;
        });

    }
}