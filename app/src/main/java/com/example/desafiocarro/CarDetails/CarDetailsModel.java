package com.example.desafiocarro.CarDetails;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.database.DAOs.CarDAO;
import com.example.desafiocarro.database.DAOs.ItemCartDAO;
import com.example.desafiocarro.database.DatabaseConfig;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;

public class CarDetailsModel {

    private CarDetailsPresenter presenter;

    private AppDatabase db;

    public CarDetailsModel(CarDetailsPresenter presenter) {
        this.presenter = presenter;
        this.db = new DatabaseConfig().geraBd(presenter.getViewContext());
    }

    public AppDatabase getDatabase() {
        return this.db;
    }

    public ItemCartDAO getItemCartDAO() {
        return db.itemCartDAO();
    }

    public CarDAO getCarDAO() {
        return db.carDAO();
    }


}
