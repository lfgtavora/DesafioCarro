package com.example.desafiocarro.Cars;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.desafiocarro.CarDetailsActivity;
import com.example.desafiocarro.R;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarsFragment extends Fragment implements ListCarsContract.view {

    private ListCarsAdapter listCarsAdapter;
    private ListCarsPresenter presenter;
    private List<Car> carList;
    private LinearLayoutManager layoutManager;

    public ListCarsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_cars, container, false);
        presenter = new ListCarsPresenter(this);
        presenter.initListCar();

        setupList(view);
        carList = new ArrayList<>();

        return view;
    }

    @Override
    public void setupList(View view) {
        RecyclerView rv_carList = view.findViewById(R.id.carlistID);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_carList.setLayoutManager(layoutManager);
        listCarsAdapter = new ListCarsAdapter(presenter.getListCars(), this);
        listCarsAdapter.notifyDataSetChanged();
        rv_carList.setAdapter(listCarsAdapter);
    }

//    @Override
//    public void startDetailsCarsActivity(Car carro) {
//        Intent intent = new Intent(getContext(), CarDetailsActivity.class);
//        intent.putExtra("CARRO_OBJ", carro);
//        startActivity(intent);
//    }

    @Override
    public void startDetailsCarsActivity(Car carro) {
        FragmentManager fragmentManager = new FragmentManager() {
        }
    }


}
