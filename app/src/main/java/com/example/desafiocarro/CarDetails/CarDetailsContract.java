package com.example.desafiocarro.CarDetails;

import android.content.Context;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;

public interface CarDetailsContract {

    interface view {
        Context getViewContext();
    }

    interface presenter{
        Context getViewContext();
        AppDatabase getDatabase();
        void doWhenbtnAddToCartisClicked(Car carro, int quantidade);
    }
}
