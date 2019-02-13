package com.example.desafiocarro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.widget.TextView;

import com.example.desafiocarro.adapters.CarlistAdapter;
import com.example.desafiocarro.fragments.ListCarsFragments;
import com.example.desafiocarro.fragments.MyCart;
import com.example.desafiocarro.models.Car;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CarlistAdapter carlistAdapter;
    private TextView mTextMessage;
    private List<Car> carList;
    private LinearLayoutManager layoutManager ;

    /*Fragments*/
    private ListCarsFragments listCarsFragments;
    private MyCart myCart;



    /*bind views*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setupFragmentCar();
                    return true;
                case R.id.navigation_cart:
                    setupFragmentCartList();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFragmentCar();

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void setupFragmentCar(){
        listCarsFragments = new ListCarsFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fraame_container, listCarsFragments );
        transaction.commitAllowingStateLoss();
    }

    public void setupFragmentCartList(){
        myCart = new MyCart();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fraame_container, myCart );
        transaction.commitAllowingStateLoss();
    }


}
