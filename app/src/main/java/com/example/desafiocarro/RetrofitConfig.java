package com.example.desafiocarro;

import com.example.desafiocarro.services.CarlistService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit getRetrofitConfig(){
        return new Retrofit.Builder()
                .baseUrl(CarlistService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
