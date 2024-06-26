package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;

    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        firebaseAuth=FirebaseAuth.getInstance();
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
                break;


//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new SettingsFragment()).commit();
//
//                break;





            case R.id.share:
                try {
                    Intent i=new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"Check out this cool app! CLASSIFY");
                    i.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(i,"Share With"));
                }catch (Exception e)
                {
                    Toast.makeText(this, "Unable to share this app", Toast.LENGTH_SHORT).show();
                }
                    

//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new ShareFragment()).commit();
                break;


            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new AboutFragment()).commit();
                break;

            case R.id.nav_logout:

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setIcon(R.drawable.baseline_logout_24)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            firebaseAuth.signOut();

                            googleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                }
                            });


//                            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this,LoginActivity.class));

                        }).setNegativeButton("No", (dialogInterface, i) -> {

                        });

                builder.show();

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