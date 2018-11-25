package com.example.luisfelix.gym;
//Luis Felix

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


public class NavigationSide extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Settings_Frag.OnFragmentInteractionListener {

    private FrameLayout mMainFrame;
    private Home_Frag home_frag;
    private Fitness_Frag fitness_frag;
    private Tracking_Frag tracking_frag;
    private Settings_Frag setting_frag;
    private App_Info_Frag app_info_frag;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_side);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMainFrame = (FrameLayout) findViewById(R.id.main_fragment);
        //Aqui estan los fragment
        home_frag = new Home_Frag();
        fitness_frag = new Fitness_Frag();
        tracking_frag = new Tracking_Frag();
        //esto es para que inice el primer fragmento como default
        setFragment(home_frag);

        //para que funcione el navigation side
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //para poner las tres rayitas en el toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //para cambiar el navigation bar
                case R.id.navigation_home:
                    setFragment(home_frag);
                    Toast.makeText(NavigationSide.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(fitness_frag);
                    Toast.makeText(NavigationSide.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    setFragment(tracking_frag);
                    Toast.makeText(NavigationSide.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        //el metodo para poner cada fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment);
        fragmentTransaction.commit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //para que las tres rayitas funcionen
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //para que funcionene los botones dentro del navigation side

        int id = item.getItemId();

        if (id == R.id.nav_info) {
            app_info_frag = new App_Info_Frag();
            setFragment(app_info_frag);

        } else if (id == R.id.nav_manage) {

            setting_frag = new Settings_Frag();
            setFragment(setting_frag);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
