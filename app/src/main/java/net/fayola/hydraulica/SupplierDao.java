package net.fayola.hydraulica;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SupplierDao {
    @Insert
    void insert(Supplier supplier);

    @Update
    void update(Supplier suppliers);

    @Delete
    void delete(Supplier suppliers);

    @Query("SELECT * FROM supplier")
    LiveData<List<Supplier>> getAllSuppliers();

    @Query("SELECT * FROM supplier WHERE _id=:id")
    LiveData<List<Supplier>> getSupplierWithID(final int id);
}
