package com.example.desafiocarro.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.desafiocarro.models.Car;

import java.util.List;

@Dao
public interface CarDAO {
    @Query("SELECT * FROM cars")
    List<Car>getAll();

    @Query("SELECT * FROM cars WHERE id LIKE :id")
    Car getCarByID(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Car car);

    @Delete
    void delete(Car car);

}
