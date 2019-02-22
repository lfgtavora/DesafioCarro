package com.example.desafiocarro.Cart;

import android.content.Context;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;

public class ListCartPresenter implements ListCartContract.presenter {

    private ListCartModel model;
    private ListCartContract.view view;


    ListCartPresenter(ListCartContract.view view) {
        this.view = view;
        this.model = new ListCartModel(this);
    }

    @Override
    public Context getContext(){
        return view.getContext();
    }

    @Override
    public AppDatabase getDatabase(){
        return model.getDatabase();
    }

    @Override
    public List<ItemCart> getItemsCart(){
        return model.getItemsCart();
    }

}
