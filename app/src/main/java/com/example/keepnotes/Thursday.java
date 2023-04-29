package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Thursday extends Fragment {

    ArrayList<myaddmondayapter> myaddmondayapters=new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monday, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mondayrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton floatingActionButton= view.findViewById(R.id.btnopendialog);


        RecyclerMondayAdapter adapter=new RecyclerMondayAdapter(getActivity(),myaddmondayapters);
        recyclerView.setAdapter(adapter);

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