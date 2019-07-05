package net.fayola.hydraulica;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.InputType;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        CalcFragment.OnCalcFragmentInteractionListener,
        ContactUsFragment.OnContactFragmentInteractionListener {

    public static String TAG ="TheHydraulicaApp";
    private static final String SID = "Laura Fayola Wallace\t#125-431-197";
    private String initialVal = "0.0";

    //Architectual Components & Rooms
    public static SuppliersViewModel mainSVM;
    public static SupplierListAdapter mainSupplierAdapter;

    //Contact Permissions
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private GoogleMap mMap;

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.hide();
        //fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Would be great if this actually did something.", Snackbar.LENGTH_LONG).setAction("Action", null).show();


                //Can create a custom Alert dialog without creating a Fragment class with xml layout file.
                Context context= view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Supplier");

                // Set up input
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // set buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //put input text in database
                        if(!input.getText().toString().isEmpty())
                        mainSVM.insert(new Supplier(input.getText().toString()));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Print  my name, Student ID and date
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CANADA);
        String dateStr = df.format(date);
        //printing to Logcat window
        Log.i(TAG,"~~~~~ "+SID+" ~ "+dateStr+" ~~~~~");

        Log.d(TAG, "Main Activity Created!");


        //Initalize database, view model and adapter
        mainSupplierAdapter =  new SupplierListAdapter(this);
        mainSVM = ViewModelProviders.of(this).get(SuppliersViewModel.class);
        mainSVM.getAllSuppliers().observe(this, new Observer<List<Supplier>>() {
            @Override
            public void onChanged(List<Supplier> suppliers) {
                mainSupplierAdapter.setSuppliers(suppliers);
            }
        });


        //Loading initial fragment
        //sending initial valued for the calculation form
        CalcFragment cf = CalcFragment.newInstance(initialVal,initialVal);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_out_right);
        ft.replace(R.id.main_placeholder,cf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        //Load in appropriate fragment
        if (id == R.id.nav_pcheck) {

            if(fab.isEnabled()){
                fab.setEnabled(false);
                fab.hide();
            }
            // do pressure calculation
            CalcFragment cf = CalcFragment.newInstance(initialVal,initialVal);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_out_right);
            ft.replace(R.id.main_placeholder,cf);

            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_supplier) {
            if(!fab.isEnabled()) {
                fab.setEnabled(true);
                fab.show();
            }
            //Runtime Permission asking to ALLOW or DENY access to Android Contacts (Dangerous Permission) will appear
            requestContactPermission();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_out_right);
            AddSuppliersFragment asf = new AddSuppliersFragment();
            ft.replace(R.id.main_placeholder,asf);

            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_contact) {
            if(fab.isEnabled()){
                fab.setEnabled(false);
                fab.hide();
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_out_right);
            ContactUsFragment cuf = new ContactUsFragment();
            ft.replace(R.id.main_placeholder,cuf);

            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_about) {
            //about as Dialog
            showAboutDialog();

        } else if (id == R.id.nav_exit) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /***
     * Handling Fragments
     */

    @Override
    public void onCalcPerformed(double result) {
        //Toast.makeText(this,"The force calculated is: " + result,Toast.LENGTH_LONG).show();
        String resultStr = String.format("%.3f",result);
        showResultsDialog(resultStr);
    }

    private void showResultsDialog(String result) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(TAG+"::CalcResultFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        CalcResultFragment crf = new CalcResultFragment().newInstance(result);


        crf.show(ft,TAG+"::CalcResultFragment");
    }

    private void showAboutDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(TAG+"::AboutFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        AboutFragment af = new AboutFragment();


        af.show(ft,TAG+"::CalcResultFragment");
    }

    public void showGoogleMap() {
        //show activity as dialog
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);

    }
    /*** END OF Handling Fragments ***/

    /***
     * Handling Contact Permissions
     */
    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Read Contacts permission");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage("Thank you for enabling access to contacts.");
                builder.show();
                Log.d(TAG,"Contact Permission granted.");
            }
        } else {
            Log.d(TAG,"Build version doesn't ask for Contact Permission.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permissions Granted
                } else {
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /*** END OF Handling Contact Permissions ***/





}
