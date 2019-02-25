package com.example.desafiocarro.Cars;

import android.content.Context;
import android.view.View;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;

import java.util.List;

public interface ListCarsContract {

    interface view {
        void setupList(View view);
        Context getContext();
        void startDetailsCarsActivity(Car carro);
        void showSuccess();
        void showLoading();
        void showError();
    }

    interface presenter{
        void initListCar();
        Context getContext();
        List<Car> getListCars();
        AppDatabase getDataBase();
    }
}
