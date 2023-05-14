package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class Monday extends Fragment {


    // this is for retriving the data
    RecyclerView recyclerView;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference reference=db.getReference().child("Monday");

    MyAdapter myAdapter;
    ArrayList<user> list;
    //


    ArrayList<myaddmondayapter>myaddmondayapters=new ArrayList<>();
    RecyclerMondayAdapter adapter;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;

    ArrayList<user>userArrayList;

//    ProgressDialog progressDialog;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        recyclerView = view.findViewById(R.id.mondayrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton floatingActionButton= view.findViewById(R.id.btnopendialog);
        adapter=new RecyclerMondayAdapter(getActivity(),myaddmondayapters);
        recyclerView.setAdapter(adapter);



//        progressDialog=new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data..");
//        progressDialog.show();



        recyclerView=view.findViewById(R.id.mondayrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list=new ArrayList<>();
        myAdapter=new MyAdapter(getActivity(),list);
        recyclerView.setAdapter(myAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    user user=dataSnapshot.getValue(user.class);
                    list.add(user);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        floatingActionButton.setOnClickListener(view1 -> {
            EditText time,roomNo,teacherName,className;
            TextView heading;

            Dialog dialog =new Dialog(getActivity());
            dialog.setContentView(R.layout.activity_custom_dialog_adapter);

            time=dialog.findViewById(R.id.time);
            roomNo=dialog.findViewById(R.id.room_no);
            teacherName=dialog.findViewById(R.id.teacher_name);
            className=dialog.findViewById(R.id.class_name);
            heading=dialog.findViewById(R.id.messagetxt);
            Button btnAction=dialog.findViewById(R.id.Add_Update_btn);

            databaseReference = FirebaseDatabase.getInstance().getReference("Monday");
            heading.setText("CREATE TIMETABLE");
            btnAction.setText("ADD");


            btnAction.setOnClickListener(view11 -> {
                String timgo,roomnewNo,teachName,classnewName="";
                 timgo=time.getText().toString();
                 roomnewNo=roomNo.getText().toString();
                 teachName=teacherName.getText().toString();

                if(!className.getText().toString().equals(""))
                {
                     classnewName=className.getText().toString();
                     // For Adding the data successfully
                    myaddmondayapter data = new myaddmondayapter(timgo, roomnewNo, teachName, classnewName);
                    databaseReference.push().setValue(data);
                    Toast.makeText(getActivity(), "Data added successfully!", Toast.LENGTH_SHORT).show();




                }
                else{
                    Toast.makeText(getContext(), "Class Name Can Not be Empty!", Toast.LENGTH_SHORT).show();

                }



                myaddmondayapters.add(new myaddmondayapter(timgo,roomnewNo,teachName,classnewName));
                adapter.notifyItemChanged(myaddmondayapters.size()-1);
                recyclerView.scrollToPosition(myaddmondayapters.size()-1);

                dialog.dismiss();

            });

            dialog.show();
        });

        return  view;

    }




}