package com.example.desafiocarro.fragments;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.desafiocarro.R;
import com.example.desafiocarro.adapters.CartItemsAdapter;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCart extends Fragment {

    private List<ItemCart> itemCart;
    private AppDatabase db;
    private RecyclerView rv_cartItems;
    private TextView totalCount;



    public MyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        totalCount = view.findViewById(R.id.PrecoTotalTxt);

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "database-car").allowMainThreadQueries().build();


        //setup recyclerview
        rv_cartItems = view.findViewById(R.id.ItemCartsRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_cartItems.setLayoutManager(layoutManager);
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(db.itemCartDAO().getItemsCart(), getContext(), db);
        rv_cartItems.setAdapter(cartItemsAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTotal();
    }

    public void setTotal() {
        float total = 0;
        List<ItemCart> items = db.itemCartDAO().getItemsCart();
        for (ItemCart item: items) {
            total = total + item.getQuantidade() * item.getPreco();
        }

        totalCount.setText(String.format("R$ %.2f", total));
    }


}
