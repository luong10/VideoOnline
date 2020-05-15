package com.example.mywatch;

import android.content.Intent;
import android.os.Bundle;

import com.example.mywatch.danhMuc.FragmentThinhHanh;
import com.example.mywatch.danhMuc.FragmentTrangChu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private DrawerLayout drawer;
    public static  String API_KEY = "AIzaSyAawyFAI1MK_0H6PjaiGpEOxf6llTNNi-o";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_con, new FragmentTrangChu()).commit();
            navigationView.setCheckedItem(R.id.trangChu);
        }
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.trangChu:
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_con, new FragmentTrangChu()).commit();
                break;
            case R.id.thinhHanh:

                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_con, new FragmentThinhHanh()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            case R.id.tim:
//                Intent intent = new Intent(this,TimKiemActivity.class);
//                startActivity(intent);
//                break;
//            default:break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
