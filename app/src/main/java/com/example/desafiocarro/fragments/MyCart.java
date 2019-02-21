package com.example.desafiocarro.fragments;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.DelegateItemCart;
import com.example.desafiocarro.R;
import com.example.desafiocarro.adapters.CartItemsAdapter;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCart extends Fragment implements DelegateItemCart {

    private AppDatabase db;
    private RecyclerView rv_cartItems;
    private TextView totalCount;
    private Button comprarBtn;
    List<ItemCart> items;
    private TextView txtCarrinhoVazio;

    public MyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        //inicializa BD
        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "database-car").allowMainThreadQueries().build();

        bindViews(view);
        setTotal();
        getItemsCart();
        finalizarCompra();
        setupList(view);

        return view;
    }

    private void getItemsCart() {
        items = db.itemCartDAO().getItemsCart();
    }

    private void bindViews(View view) {
        totalCount = view.findViewById(R.id.PrecoTotalTxt);
        comprarBtn = view.findViewById(R.id.FinalizarCompraBtn);
        txtCarrinhoVazio = view.findViewById(R.id.TxtCarrinhoVazio);
    }

    private void finalizarCompra() {
        comprarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.itemCartDAO().deletaLista(items);
                setTotal();
                items.clear();
                rv_cartItems.getAdapter().notifyDataSetChanged();
                Toast.makeText(getContext(), "Compra finalizada com sucesso!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void btnComprarVisibilidade() {
        if (carrinhoVazio())
            txtCarrinhoVazio.setVisibility(View.VISIBLE);
        else
            txtCarrinhoVazio.setVisibility(View.GONE);
    }

    private boolean carrinhoVazio() {
        return items.size() == 0;
    }

    private void setupList(View view) {
        //setup recyclerview
        rv_cartItems = view.findViewById(R.id.ItemCartsRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_cartItems.setLayoutManager(layoutManager);
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(db.itemCartDAO().getItemsCart(), this, db);
        rv_cartItems.setAdapter(cartItemsAdapter);
    }

    @Override
    public void setTotal() {
        getItemsCart();
        float total = 0;

        for (ItemCart item: items)
            total = total + item.getQuantidade() * item.getPreco();

        totalCount.setText(String.format("R$ %.2f", total));
        btnComprarVisibilidade();
    }

}
