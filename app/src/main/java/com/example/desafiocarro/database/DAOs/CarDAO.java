package com.example.desafiocarro.database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.desafiocarro.models.Car;

import java.util.List;

@Dao
public interface CarDAO {
    @Query("SELECT * FROM cars")
    List<Car>getAll();

    @Query("SELECT * FROM cars WHERE id LIKE :id")
    Car getCarByID(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllCars(List<Car> carList);

    @Query("UPDATE cars SET quantidade= :quantidade WHERE id = :id")
    void setQuantidade(int id, int quantidade);

    @Update
    void setQtd(Car carro);

    @Delete
    void delete(Car carro);
}
