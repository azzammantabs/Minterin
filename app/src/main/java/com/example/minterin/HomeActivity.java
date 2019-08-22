package com.example.minterin;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.minterin.fragment.AkunFragment;
import com.example.minterin.fragment.BerandaFragment;
import com.example.minterin.fragment.KuisFragment;
import com.example.minterin.fragment.Tab1;
import com.example.minterin.fragment.Tab2;
import com.example.minterin.fragment.Tab3;
import com.example.minterin.helperS.PrefsHelper;


public class HomeActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_home);

        bottomNav = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();

        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;

                        switch (menuItem.getItemId()){
                            case R.id.nav_beranda :{
                                selectedFragment = new BerandaFragment();
                            }break;
                            case R.id.nav_kuis :{
                                selectedFragment = new KuisFragment();
                            }break;
                            case R.id.nav_akun :{
                                selectedFragment = new AkunFragment();
                            }default:
                                //do nothing
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                };
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
