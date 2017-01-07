package com.pothole.potholedetector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userNameField;
    private TextView userEmailField;
    private int flag = 0;
    FloatingActionButton rideButton;
    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        if (!sharedPreferences.contains("id")) {
            Intent login = new Intent(this,MainActivity.class);
            startActivity(login);
            finish();
        }

        ride = new Ride();
        rideButton = (FloatingActionButton) findViewById(R.id.rideButton);
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    Log.d("Hello","I am here");
//                    ride.startService(new Intent(getBaseContext(), Ride.class));
                    rideButton.setImageResource(R.drawable.ic_landing);
                    Snackbar.make(view, "Your ride has begun!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    flag = 1;
                } else {
//                    ride.stopSelf();
                    rideButton.setImageResource(R.drawable.ic_takeoff);
                    Snackbar.make(view, "Your ride has ended!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    flag=0;

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderHome = navigationView.getHeaderView(0);

        userNameField = (TextView) navHeaderHome.findViewById(R.id.userName);
        userEmailField = (TextView) navHeaderHome.findViewById(R.id.userEmail);

        userNameField.setText(sharedPreferences.getString("name","Unknown"));
        userEmailField.setText(sharedPreferences.getString("email","Unknown"));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.logout) {
            SharedPreferences  sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();

            Intent login = new Intent(this, MainActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
