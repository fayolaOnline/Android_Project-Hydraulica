package net.fayola.hydraulica;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EnginePartDao {
    @Insert
    void insert(EnginePart enginePart);

    @Update
    void update(EnginePart engineParts);

    @Delete
    void delete(EnginePart engineParts);

    @Query("SELECT * FROM engine_part")
    LiveData<List<EnginePart>> getAllEngineParts();

    @Query("SELECT * FROM engine_part WHERE _id=:id")
    LiveData<List<EnginePart>> getEnginePartWithID(final int id);
}
