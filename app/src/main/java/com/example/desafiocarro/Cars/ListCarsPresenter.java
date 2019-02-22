package com.example.desafiocarro.Cars;

import android.content.Context;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;

import java.util.List;

public class ListCarsPresenter implements ListCarsContract.presenter{

    private ListCarsContract.view view;
    private ListCarsModel model;

    ListCarsPresenter(ListCarsContract.view view){
        this.view = view;
        this.model = new ListCarsModel(this);
    }

    @Override
    public Context getContext(){
        return view.getContext();
    }

    @Override
    public void initListCar() {
        model.initListCar();
    }

    @Override
    public List<Car> getListCars() {
        return model.getListCars();
    }

    @Override
    public AppDatabase getDataBase() {
        return model.getDatabase();
    }


}
