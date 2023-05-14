package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;

//    RecyclerView recyclerView;
//    ArrayList<user>userArrayList;
//    MyAdapter myAdapter;
//    FirebaseFirestore db;
//    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        progressDialog=new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data..");
//        progressDialog.show();



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


//        recyclerView=findViewById(R.id.mondayrecycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        db=FirebaseFirestore.getInstance();
//        userArrayList=new ArrayList<user>();
//        myAdapter=new MyAdapter(MainActivity.this,userArrayList);
//        recyclerView.setAdapter(myAdapter);
//
//        EventChangeListner();



    }

//    private void EventChangeListner() {
//        db.collection("Monday").orderBy("time", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if(error!=null)
//                            {
//                                if(progressDialog.isShowing())
//                                {
//                                    progressDialog.dismiss();
//                                }
//                                Log.e("Firebase error",error.getMessage());
//                            }
//                            for(DocumentChange dc:value.getDocumentChanges())
//                            {
//                                if(dc.getType()==DocumentChange.Type.ADDED)
//                                {
//                                    userArrayList.add(dc.getDocument().toObject(user.class));
//
//                                }
//                                myAdapter.notifyDataSetChanged();
//                                if(progressDialog.isShowing())
//                                {
//                                    progressDialog.dismiss();
//                                }
//
//                            }
//
//                    }
//                });
//
//
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
                break;


            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new SettingsFragment()).commit();

                break;





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
                            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));

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