package com.example.desafiocarro.services;

import com.example.desafiocarro.models.Car;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CarlistService {
    String BASE_URL = "http://desafiobrq.herokuapp.com/";

    @GET("v1/carro/")
    Observable<List<Car>> getCars();
}
