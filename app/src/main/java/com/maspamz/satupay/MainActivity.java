package com.maspamz.satupay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.maspamz.satupay.config.api.ConfigUrl;
import com.maspamz.satupay.fragment.FragmentUrl;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private String linkUrl = ConfigUrl.homeURL;
    String TAG = "Message : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        isStoragePermissionGranted();

        //Setting Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //End

        //DefaultSet
        setFragment(0);
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
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Yuk beli pulsa/ bayar pln,aetra lewat sini "+
                            "\nMurah meriah dan praktis loh, install aja dulu aplikasinya, dijamin ketagihan"+
                            "\n\nhttp://satupay.page.link/app"
            );
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.homeURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_product) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.productURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_transaction) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.transactionURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_promo) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.promoURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_faq) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.faqURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_question) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.questionURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_help) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.helpURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.nav_about) {
            menuItem.setChecked(true);
            linkUrl = ConfigUrl.aboutURL;
            setFragment(0);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentUrl fragmentUrl = new FragmentUrl();
                Bundle dataKey = new Bundle();
                dataKey.putString("URL_ITEM", linkUrl);
                fragmentUrl.setArguments(dataKey);
                fragmentTransaction.replace(R.id.fragment, fragmentUrl);
                fragmentTransaction.commit();
                break;
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
