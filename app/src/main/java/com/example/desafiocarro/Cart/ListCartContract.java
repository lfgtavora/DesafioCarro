package com.example.desafiocarro.Cart;

import android.content.Context;
import android.view.View;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;

public interface ListCartContract {

    interface view {
        void btnComprarVisibilidade();
        void setTotal();
        void setupList(View view);
        Context getContext();
    }

    interface presenter {
        Context getContext();
        AppDatabase getDatabase();
        List<ItemCart> getItemsCart();
    }
}
