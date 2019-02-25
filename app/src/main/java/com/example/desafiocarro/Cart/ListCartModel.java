package com.example.desafiocarro.Cart;


import android.arch.persistence.room.Room;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.database.DatabaseConfig;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;

public class ListCartModel {

    private ListCartPresenter presenter;
    private AppDatabase db;

    //Constructor
    ListCartModel(ListCartPresenter presenter) {
        this.presenter = presenter;
        this.db = new DatabaseConfig().geraBd(presenter.getContext());
    }

    List<ItemCart> getItemsCart(){
        return db.itemCartDAO().getItemsCart();
    }

    public AppDatabase getDatabase(){
        return this.db;
    }


}
