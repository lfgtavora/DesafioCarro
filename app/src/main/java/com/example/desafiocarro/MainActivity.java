package com.example.desafiocarro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;

import com.example.desafiocarro.Cars.ListCarsFragment;
import com.example.desafiocarro.Cart.ListCartFragment;
import com.example.desafiocarro.models.Car;

public class MainActivity extends AppCompatActivity implements MainDelegate {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setupFragment(new ListCarsFragment());
                    setMainTitle("Lista de carros");
                    return true;
                case R.id.navigation_cart:
                    setupFragment(new ListCartFragment());
                    setMainTitle("Meu carrinho");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        setupFragment(new ListCarsFragment());
        setMainTitle("Lista de carros");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(getIntent().getExtras() != null){
            setupFragment(new ListCartFragment());
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }

    }

    public void setupFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fraame_container, fragment );
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void setMainTitle(String title) {
        setTitle(title);
    }

    @Override
    public void shouldDisplayHomeUp(boolean check) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(check);
        getSupportActionBar();
    }




}
