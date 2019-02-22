package com.example.desafiocarro.Cars;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.example.desafiocarro.RetrofitConfig;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.services.CarlistService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListCarsModel {

    private ListCarsPresenter presenter;
    private CarlistService service;
    private AppDatabase db;

    public ListCarsModel(ListCarsPresenter presenter) {
        this.presenter = presenter;
        this.service = RetrofitConfig.getRetrofitConfig().create(CarlistService.class);
        this.db = Room.databaseBuilder(presenter.getContext(), AppDatabase.class, "database-car")
                .allowMainThreadQueries().build();
    }

    public void initListCar() {
        service.getCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()) {
                    if (dbIsEmpty()) { insertAllCars(response.body()); }
                }
                else { Log.d("RetrofitError", response.errorBody().toString()); }
            }
            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

            }
        });
    }

    private boolean dbIsEmpty(){
        int dbsize = db.carDAO().getAll().size();
        return dbsize == 0;
    }

    public AppDatabase getDatabase(){
        return this.db;
    }

    private void insertAllCars(List<Car> listCars){
        db.carDAO().insertAllCars(listCars);
    }

    public List<Car> getListCars(){
        return db.carDAO().getAll();
    }

    public void deleteCar(Car carro){
        db.carDAO().delete(carro);
    }


}
