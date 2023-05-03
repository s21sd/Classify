package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dataget extends AppCompatActivity {
    EditText etTime, etRoomNo, etTeacherName, etClassName;
    Button btnSave;

    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime = findViewById(R.id.time);
        etRoomNo = findViewById(R.id.room_no);
        etTeacherName = findViewById(R.id.teacher_name);
        etClassName = findViewById(R.id.class_name);

        btnSave = findViewById(R.id.Add_Update_btn);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("mydata");

        btnSave.setOnClickListener(v -> {
            // Get the entered data from the EditText fields
            String time = etTime.getText().toString();
            String roomNo = etRoomNo.getText().toString();
            String teacherName = etTeacherName.getText().toString();
            String className = etClassName.getText().toString();

            // Create a new instance of myaddmondayapter class
            myaddmondayapter data = new myaddmondayapter(time, roomNo, teacherName, className);

            // Add the data to the database
            databaseReference.push().setValue(data);

            Toast.makeText(Dataget.this, "Data added successfully!", Toast.LENGTH_SHORT).show();

            // Clear the EditText fields
            etTime.setText("");
            etRoomNo.setText("");
            etTeacherName.setText("");
            etClassName.setText("");
        });

    }


    }
