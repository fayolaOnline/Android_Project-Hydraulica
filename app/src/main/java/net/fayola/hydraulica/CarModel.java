package net.fayola.hydraulica;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "car_model")
public class CarModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String mName;

    @NonNull
    private double mRadius;

    public CarModel(int id, @NonNull String name, double radius){
        this.id = id;
        this.mName = name;
        this.mRadius = radius;
    }

    @Ignore
    public CarModel(@NonNull String name, double radius){
        this.mName = name;
        this.mRadius = radius;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getMName() {
        return mName;
    }

    public double getMRadius() {
        return mRadius;
    }

    @NonNull
    @Override
    public String toString() {
        return "Car Model: " + this.mName + ", with a piston radius of " + this.mRadius;
    }
}
