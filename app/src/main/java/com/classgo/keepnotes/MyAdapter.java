package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// this is the adapter to get the data from the firebase
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<user>userArrayList;
    DatabaseReference databaseReference;

    private  int lastPosition=-1;
    private String day;


    public MyAdapter(Context context, ArrayList<user> userArrayList,String day) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.day = day;
        databaseReference = FirebaseDatabase.getInstance().getReference().child(day);
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rc_items,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        user user=userArrayList.get(position);
        holder.classname.setText(user.getClassname());
        holder.roomno.setText(user.getRoomno());
        holder.teachername.setText(user.getTeachername());
        holder.time.setText(user.getTime());

        setAnimation(holder.itemView,position);

        // this is for updating the for recyclerview

        databaseReference = FirebaseDatabase.getInstance().getReference().child(day);
        holder.rc.setOnClickListener(view -> {
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


            time.setText(user.getTime());
            roomno.setText(user.getRoomno());
            classname.setText(user.getClassname());
            teachername.setText(user.getTeachername());



            addupdatebtn.setOnClickListener(view1 -> {
                String timgo = time.getText().toString();
                String roomnewNo = roomno.getText().toString();
                String teachName = teachername.getText().toString();
                String classnewName = classname.getText().toString();

                if(!classnewName.isEmpty())
                {
                    user updatedData = new user(classnewName, roomnewNo,teachName,timgo);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(day);
                    String dataKey = userArrayList.get(position).getKey(); // Assuming you have a "getKey" method in your "myaddmondayapter" class
                    databaseReference.child(dataKey).setValue(updatedData);
                    userArrayList.set(position, updatedData);
                    notifyItemChanged(position);
                    dialog.dismiss();

                }
                else {
                    Toast.makeText(context, "Class Name Can Not be Empty!", Toast.LENGTH_SHORT).show();

                }
            });
            dialog.show();
        });

        // for deleting the item

        holder.rc.setOnLongClickListener(view -> {

            AlertDialog.Builder builder=new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure want to delete?")
                    .setIcon(R.drawable.baseline_delete_24)
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(day);
                        String dataKey = userArrayList.get(position).getKey();
                        databaseReference.child(dataKey).removeValue();
                        userArrayList.remove(position);
                        notifyItemRemoved(position);
                    }).setNegativeButton("No", (dialogInterface, i) -> {

                    });

            builder.show();

            return true;
        });





    }

    private void setAnimation(View itemView, int position) {
        if(position> lastPosition)
        {
            Animation slideIn= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.startAnimation(slideIn);
            lastPosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView time,classname,teachername,roomno;
        RelativeLayout rc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            time=itemView.findViewById(R.id.timerc);
            classname=itemView.findViewById(R.id.classnamerc);
            teachername=itemView.findViewById(R.id.teachernamerc);
            roomno=itemView.findViewById(R.id.roomnorc);
            rc=itemView.findViewById(R.id.rcrelativelayout);


        }
    }
}
