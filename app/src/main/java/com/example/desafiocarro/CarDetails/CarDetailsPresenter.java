package com.example.desafiocarro.CarDetails;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;
import java.util.Locale;

public class CarDetailsPresenter implements CarDetailsContract.presenter {
    private CarDetailsContract.view view;
    private CarDetailsModel model;


    CarDetailsPresenter(CarDetailsContract.view view){
        this.view = view;
        this.model = new CarDetailsModel(this);
    }

    @Override
    public Context getViewContext() {
        return view.getViewContext();
    }

    @Override
    public AppDatabase getDatabase() {
        return model.getDatabase();
    }

    @Override
    public void doWhenbtnAddToCartisClicked(Car carro, int quantidade){
        List<ItemCart> itemCarts = model.getItemCartDAO().getItemsCart();
        int itensRestantes = carro.getQuantidade() - quantidade;
        int carId = carro.getId();

        model.getCarDAO().setQuantidade(carId, itensRestantes);

        int position = -1;
        for(int i =0; i < itemCarts.size();i++){
            if(itemCarts.get(i).getIdCar() == carId)
                position = i;
        }
        if(position == -1){
            ItemCart itemCart = new ItemCart(carId, quantidade, carro.getPreco());
            model.getItemCartDAO().insertItemOnCart(itemCart);
        } else{
            int quantidadeAtualizada = itemCarts.get(position).getQuantidade() + quantidade;
            int idCar = itemCarts.get(position).getIdCar();
            model.getItemCartDAO().updateQuantidade(quantidadeAtualizada, idCar);
        }
    }


}
