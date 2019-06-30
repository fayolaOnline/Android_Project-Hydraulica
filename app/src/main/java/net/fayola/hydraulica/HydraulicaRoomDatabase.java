package net.fayola.hydraulica;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Supplier.class, CarModel.class, EnginePart.class}, version = 1)
public abstract class HydraulicaRoomDatabase extends RoomDatabase {
    public static String TAG = MainActivity.TAG + "::HudraulicaRoomDatabase";
    public abstract SupplierDao SupplierDao();
    public abstract CarModelDao CarModelDao();
    public abstract EnginePartDao EnginePartDao();
    //maybe a Dao for the joining query to get names of supplier and car with my engine part??

    private static volatile HydraulicaRoomDatabase INSTANCE;

    private static final String DB_NAME = "hydraulica.db";
    private static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsync(INSTANCE).execute();
        }


        ////Was just checking to see why onCreate wasn't being called but I need to uninstall the app and run again to see it called.
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new PopulateDBAsync(INSTANCE).execute();
//
//        }
    };

    static HydraulicaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HydraulicaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HydraulicaRoomDatabase.class, DB_NAME)
                            .addCallback(rdc)
                            .build();
                    Log.d(TAG,"Database Created!");
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        private final SupplierDao mDao;
        //Insert initial Data so we have something to show
        String[] strArr = {
                "Canadian Tire",
                "Standard Auto Wreckers",
                "Tiger Auto Parts",
                "Allmake Select Auto Parts Inc"
        };

        PopulateDBAsync(HydraulicaRoomDatabase db) {
            mDao = db.SupplierDao();
            Log.d(TAG,"should be populating the Suppliers table");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (String s : strArr){
                Supplier item = new Supplier(s);
                mDao.insert(item);
                Log.d(TAG,"Added to Suppliers table: " + item.getName());
            }
            return null;
        }



    }

}
