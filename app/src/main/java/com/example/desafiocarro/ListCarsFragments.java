package com.example.desafiocarro;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.desafiocarro.adapters.CarlistAdapter;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.services.CarlistService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarsFragments extends Fragment {

    private CarlistAdapter carlistAdapter;
    private TextView mTextMessage;
    private List<Car> carList;
    private LinearLayoutManager layoutManager ;
    private AppDatabase db;

    public ListCarsFragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_cars, container, false);

        carList = new ArrayList<>();

        callListCar();

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "database-car").allowMainThreadQueries().build();

        RecyclerView rv_carList = view.findViewById(R.id.carlistID);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_carList.setLayoutManager(layoutManager);
        carlistAdapter = new CarlistAdapter(db,db.carDAO().getAll(), getContext());
        rv_carList.setAdapter(carlistAdapter);

        return view;

    }

    public void callListCar() {
        CarlistService api = RetrofitConfig.getRetrofitConfig().create(CarlistService.class);
        api.getCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()){
                    if (dbIsEmpty()){
                        db.carDAO().insertAllCars(response.body());
                        carlistAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("RetrofitError", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

            }
        });
    }

    public boolean dbIsEmpty(){
        int dbsize = db.carDAO().getAll().size();
        Log.i("DB", String.valueOf(dbsize));
        return dbsize == 0;
    }
}
