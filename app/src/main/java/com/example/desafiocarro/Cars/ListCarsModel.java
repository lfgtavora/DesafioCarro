package com.example.desafiocarro.Cars;

import com.example.desafiocarro.RetrofitConfig;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.database.DatabaseConfig;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.services.CarlistService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListCarsModel {

    private CarlistService service;
    private List<Car> carList2;
    private AppDatabase db;

    ListCarsModel(ListCarsPresenter presenter) {
        this.service = RetrofitConfig.getRetrofitConfig().create(CarlistService.class);
        this.db = new DatabaseConfig().geraBd(presenter.getContext());
    }


    void initListCar() {
        service.getCars().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Car>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Car> carList) {
                        carList2 = carList;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (dbIsEmpty())
                            insertAllCars(carList2);

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

    List<Car> getListCars(){
        return db.carDAO().getAll();
    }

}
