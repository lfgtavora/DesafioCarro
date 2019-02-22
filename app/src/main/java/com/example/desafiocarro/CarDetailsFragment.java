package com.example.desafiocarro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desafiocarro.models.Car;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarDetailsFragment extends Fragment {

    TextView marca;
    TextView descricao;
    ImageView imagem;

    public CarDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_details, container, false);

        marca = view.findViewById(R.id.MarcaID);
        descricao = view.findViewById(R.id.DescricaoID);
        imagem = view.findViewById(R.id.ImagemID);

        Bundle bundle = getArguments();
        Car carro= (Car) bundle.getSerializable("CAR_OBJ");

        marca.setText(carro.getMarca());
        descricao.setText(carro.getDescricao());

        MainDelegate mainDelegate = (MainDelegate) getActivity();
        mainDelegate.setMainTitle(carro.getNome());

        mainDelegate.shouldDisplayHomeUp(true);
        return view;


    }

}
