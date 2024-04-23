package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class Monday extends Fragment {


    // this is for retrieving the data
    RecyclerView recyclerView;

    DatabaseReference reference;

    // this is for the google to get the current user id

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;

    MyAdapter myAdapter;
    ArrayList<user> list;
    String userId;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


//        Intent intent= requireActivity().getIntent();
        if(currentUser!=null)
        {
            userId=currentUser.getUid();

        }

//            userId=intent.getStringExtra("text");



        recyclerView = view.findViewById(R.id.mondayrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list=new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), list,"Monday");
        recyclerView.setAdapter(myAdapter);


        reference= FirebaseDatabase.getInstance().getReference().child("Monday").child(userId);





        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user user = dataSnapshot.getValue(user.class);
                    assert user != null;
                    user.setKey(dataSnapshot.getKey());
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FloatingActionButton floatingActionButton = view.findViewById(R.id.btnopendialog);
        floatingActionButton.setOnClickListener(view1 -> openDialog());

        return view;



    }

    @SuppressLint("SetTextI18n")
    private void openDialog() {
        EditText time, roomNo, teacherName, className;
        TextView heading;
        Button btnAction;

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_custom_dialog_adapter);

        time = dialog.findViewById(R.id.time);
        roomNo = dialog.findViewById(R.id.room_no);
        teacherName = dialog.findViewById(R.id.teacher_name);
        className = dialog.findViewById(R.id.class_name);
        heading = dialog.findViewById(R.id.messagetxt);
        btnAction = dialog.findViewById(R.id.Add_Update_btn);

        reference = FirebaseDatabase.getInstance().getReference().child("Monday").child(userId);

        heading.setText("CREATE TIMETABLE");
        btnAction.setText("ADD");

        btnAction.setOnClickListener(view11 -> {
            String timgo = time.getText().toString();
            String roomnewNo = roomNo.getText().toString();
            String teachName = teacherName.getText().toString();
            String classnewName = className.getText().toString();

            if (classnewName.equals("")) {
                Toast.makeText(getContext(), "Class Name Cannot be Empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            user newUser = new user(classnewName, roomnewNo, teachName, timgo);
            reference.push().setValue(newUser)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Data added successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {});

            dialog.dismiss();
        });

        dialog.show();
//        private void scheduleClassNotification(myaddmondayapter class)
//        {
//            Calendar calendar=Calendar.getInstance();
//            int currentHour=calendar.get(Calendar.HOUR_OF_DAY);
//            int currMinute=calendar.get(Calendar.MINUTE);
//
//        }

    }



}