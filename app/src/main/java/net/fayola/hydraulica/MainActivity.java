package net.fayola.hydraulica;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CalcFragment.OnCalcFragmentInteractionListener {

    public static String TAG ="TheHydraulicaApp";
    private static final String SID = "Laura Fayola Wallace\t#125-431-197";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Would be great if this actually did something.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //Loading initial fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //sending initial valued for the calculation form
        String initialVal = "0.0";
        CalcFragment cf = CalcFragment.newInstance(initialVal,initialVal);
        ft.replace(R.id.main_placeholder,cf);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pcheck) {
            // do pressure calculation
        } else if (id == R.id.nav_supplier) {

        } else if (id == R.id.nav_supplier) {
            //Runtime Permission asking to ALLOW or DENY access to Android Contacts will appear
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Handling Fragments



    @Override
    public void onCalcPerformed(double result) {
        Toast.makeText(this,"The force calculated is: " + result,Toast.LENGTH_LONG).show();
        String resultStr = String.format("%.3f",result);
        showResultsDialog(resultStr);
    }

    private void showResultsDialog(String result) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CalcResultFragment crf = new CalcResultFragment().newInstance(result);

        crf.show(ft,TAG+"::CalcResultFragment");
    }
}
