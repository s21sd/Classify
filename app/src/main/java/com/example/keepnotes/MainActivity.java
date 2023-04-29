package com.example.keepnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout =findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
                Intent intent=new Intent(this,Timetableshow.class);
                startActivity(intent);
                break;


            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new SettingsFragment()).commit();
                break;


            case R.id.share:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ShareFragment()).commit();
                break;


            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AboutFragment()).commit();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "LogOut Successful", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }




}