package net.fayola.hydraulica;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HydraulicaRepository {
    public static String TAG = MainActivity.TAG + "::HydraulicaRepository";

    private SupplierDao mSupplierDao;
    private CarModelDao mCarModelDao;
    private EnginePartDao mEnginePartDao;

    private LiveData<List<Supplier>> mAllSuppliers;
    private LiveData<List<CarModel>> mAllCarModels;
    private LiveData<List<EnginePart>> mAllEngineParts;



    HydraulicaRepository(Application application) {
        HydraulicaRoomDatabase db = HydraulicaRoomDatabase.getDatabase(application);
        mSupplierDao = db.SupplierDao();
        mCarModelDao = db.CarModelDao();
        mEnginePartDao = db.EnginePartDao();

        mAllSuppliers = mSupplierDao.getAllSuppliers();
        //see all suppliers
        //Log.d(TAG,"mAllSuppliers: " + mAllSuppliers.getValue().toString());
        mAllCarModels = mCarModelDao.getAllCarModels();
        mAllEngineParts = mEnginePartDao.getAllEngineParts();
    }

    LiveData<List<Supplier>> getAllSuppliers() {

        return mAllSuppliers;
    }



    LiveData<List<CarModel>> getAllCarModels() {
        return mAllCarModels;
    }
    LiveData<List<EnginePart>> getAllEngineParts() {
        return mAllEngineParts;
    }




    public void insertSupplier (Supplier supplier) {
        new insertSupplierAsyncTask(mSupplierDao).execute(supplier);
    }

    public void deleteSupplier (Supplier supplier) {
        new deleteSupplierAsyncTask(mSupplierDao).execute(supplier);
    }

    public void updateSupplier (Supplier supplier) {
        new updateSupplierAsyncTask(mSupplierDao).execute(supplier);
    }





    private static class insertSupplierAsyncTask extends AsyncTask<Supplier, Void, Void> {

        private SupplierDao mAsyncSupplierTaskDao;

        insertSupplierAsyncTask(SupplierDao dao) {
            mAsyncSupplierTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Supplier... params) {
            mAsyncSupplierTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteSupplierAsyncTask extends AsyncTask<Supplier, Void, Void>{
        private SupplierDao mAsyncSupplierTaskDao;

        deleteSupplierAsyncTask(SupplierDao dao){
            mAsyncSupplierTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Supplier... params) {
            mAsyncSupplierTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateSupplierAsyncTask extends AsyncTask<Supplier, Void, Void>{
        private SupplierDao mAsyncSupplierTaskDao;

        updateSupplierAsyncTask(SupplierDao dao){
            mAsyncSupplierTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Supplier... params) {
            mAsyncSupplierTaskDao.update(params[0]);
            return null;
        }
    }


}
