package com.example.desafiocarro.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseConfig {

    public AppDatabase geraBd(Context context){
        return Room
                .databaseBuilder(context, AppDatabase.class, "database-car")
                .allowMainThreadQueries()
                .build();

    }
}
