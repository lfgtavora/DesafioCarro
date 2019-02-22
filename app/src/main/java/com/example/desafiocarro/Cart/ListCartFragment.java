package com.example.desafiocarro.Cart;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.R;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.ItemCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListCartFragment extends Fragment implements ListCartContract.view {

    private RecyclerView rv_cartItems;
    private TextView totalCount;
    private Button comprarBtn;
    private List<ItemCart> items;
    private TextView txtCarrinhoVazio;
    private ListCartPresenter presenter;
    private AppDatabase db;

    public ListCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater  inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        bindViews(view);
        presenter = new ListCartPresenter(this);
        items = new ArrayList<>();
        db = presenter.getDatabase();

        setTotal();
        finalizarCompra();
        setupList(view);

        return view;
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
                presenter.getDatabase().itemCartDAO().deletaLista(items);
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


    @Override
    public void setupList(View view) {
        //setup recyclerview
        rv_cartItems = view.findViewById(R.id.ItemCartsRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_cartItems.setLayoutManager(layoutManager);
        ListCartAdapter listCartAdapter = new ListCartAdapter(items, this, presenter);
        rv_cartItems.setAdapter(listCartAdapter);
    }

    @Override
    public void setTotal() {
        float total = 0;
        items = db.itemCartDAO().getItemsCart();
        for (ItemCart item: items)
            total = total + item.getQuantidade() * item.getPreco();

        totalCount.setText(String.format(Locale.getDefault(),"R$ %.2f", total));
        btnComprarVisibilidade();
    }

}
