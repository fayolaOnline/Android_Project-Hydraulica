package net.fayola.hydraulica;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SuppliersViewModel extends AndroidViewModel {
    private HydraulicaRepository mRepository;
    private LiveData<List<Supplier>> mAllSuppliers;

    public SuppliersViewModel(Application application) {
        super(application);
        mRepository = new HydraulicaRepository(application);
        mAllSuppliers = mRepository.getAllSuppliers();
    }

    LiveData<List<Supplier>> getAllSuppliers() {

        return mAllSuppliers;
    }

    public void insert(Supplier supplier) {
        mRepository.insertSupplier(supplier);
    }
}
