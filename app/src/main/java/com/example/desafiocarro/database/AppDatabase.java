package com.example.desafiocarro.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.desafiocarro.models.Car;

@Database(entities = {Car.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase  {
        public abstract CarDAO carDAO();
        public abstract ShopcartDAO ShopcartDAO();
}

