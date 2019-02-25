package com.example.desafiocarro.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

import java.util.List;

@Dao
public interface ItemCartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemOnCart(ItemCart itemCart);

    @Query("SELECT * FROM ItemCart")
    List<ItemCart> getItemsCart();

    @Query("SELECT * FROM ItemCart WHERE idCar = :idCar")
    ItemCart getItemCartById(int idCar);

    @Query("UPDATE itemCart SET quantidade= :quantidade WHERE idCar = :idCar")
    int updateQuantidade(int quantidade, int idCar);

    @Delete
    void deleta(ItemCart itemCart);

    @Delete
    void deletaLista(List<ItemCart> itemCarts);

}
