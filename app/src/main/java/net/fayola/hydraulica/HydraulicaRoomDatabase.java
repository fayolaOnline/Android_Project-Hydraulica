package net.fayola.hydraulica;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Supplier.class, CarModel.class, EnginePart.class}, version = 1)
public abstract class HydraulicaRoomDatabase extends RoomDatabase {
    public abstract SupplierDao SupplierDao();
    public abstract CarModelDao CarModelDao();
    public abstract EnginePartDao EnginePartDao();
    //maybe a Dao for the joining query to get names of supplier and car with my engine part??

    private static volatile HydraulicaRoomDatabase INSTANCE;

    private static final String DB_NAME = "hydraulica.db";
    static HydraulicaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HydraulicaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HydraulicaRoomDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
