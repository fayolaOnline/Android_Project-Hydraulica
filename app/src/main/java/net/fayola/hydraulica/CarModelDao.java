package net.fayola.hydraulica;

import android.provider.LiveFolders;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarModelDao {
    @Insert
    void insert(CarModel carModel);

    @Update
    void update(CarModel carModels);

    @Delete
    void delete(CarModel carModels);

    @Query("SELECT * FROM car_model")
    LiveData<List<CarModel>> getAllCarModels();

    @Query("SELECT * FROM car_model WHERE _id=:id")
    LiveData<List<CarModel>> getCarModelWithID(final int id);
}
