package com.example.desafiocarro.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;

@Database(entities = {Car.class, ItemCart.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase  {
        public abstract CarDAO carDAO();
        public abstract ItemCartDAO itemCartDAO();
}

