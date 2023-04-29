package com.example.keepnotes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerMondayAdapter extends RecyclerView.Adapter<RecyclerMondayAdapter.ViewHolder> {


    Context context;
    ArrayList<myaddmondayapter>myaddmondayapters;
    RecyclerMondayAdapter(Context context, ArrayList<myaddmondayapter>myaddmondayapters)
    {
        this.context=context;
        this.myaddmondayapters=myaddmondayapters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.time.setText(myaddmondayapters.get(position).time);
        holder.className.setText(myaddmondayapters.get(position).className);
        holder.roomNo.setText(myaddmondayapters.get(position).roomNo);
        holder.teacherName.setText(myaddmondayapters.get(position).teacherName);

        holder.rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.activity_custom_dialog_adapter);
                TextView heading;
                EditText time,roomno,classname,teachername;
                Button addupdatebtn;
                time=dialog.findViewById(R.id.time);
                roomno=dialog.findViewById(R.id.room_no);
                classname=dialog.findViewById(R.id.class_name);
                teachername=dialog.findViewById(R.id.teacher_name);
                addupdatebtn=dialog.findViewById(R.id.Add_Update_btn);
                heading=dialog.findViewById(R.id.messagetxt);

                heading.setText("UPDATE TIMETABLE");
                addupdatebtn.setText("UPDATE");

                time.setText(myaddmondayapters.get(position).time);
                roomno.setText(myaddmondayapters.get(position).roomNo);
                classname.setText(myaddmondayapters.get(position).className);
                teachername.setText(myaddmondayapters.get(position).teacherName);



                addupdatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String timgo="",roomnewNo="",teachName="",classnewName="";
                        timgo=time.getText().toString();
                        roomnewNo=roomno.getText().toString();
                        teachName=teachername.getText().toString();

                        if(!classname.getText().toString().equals(""))
                        {
                            classnewName=classname.getText().toString();
                        }
                        else{
                            Toast.makeText(context, "Class Name Can Not be Empty!", Toast.LENGTH_SHORT).show();

                        }

                        myaddmondayapters.set(position,new myaddmondayapter(timgo,roomnewNo,teachName,classnewName));
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        holder.rc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure want to delete?")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myaddmondayapters.remove(position);
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return myaddmondayapters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time,roomNo,className,teacherName;
        RelativeLayout rc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.timerc);
            className=itemView.findViewById(R.id.classnamerc);
            roomNo=itemView.findViewById(R.id.roomnorc);
            teacherName=itemView.findViewById(R.id.teachernamerc);
            rc=itemView.findViewById(R.id.rcrelativelayout);
        }
    }
}
