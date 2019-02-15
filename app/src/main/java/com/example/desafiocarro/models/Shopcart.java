package com.example.desafiocarro.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "shopcart")
public class Shopcart {

    @ColumnInfo(name = "id_car")
    private int idCar;

    @ColumnInfo(name = "total")
    private float total;



}
