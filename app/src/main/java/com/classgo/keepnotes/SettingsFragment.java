package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch notificationonoff;

    RelativeLayout textsizechange;
    LinearLayout settinglenear;
    private Monday monday;
    private Tuesday tuesday;
    private Wednesday wednesday;
    private Thursday thursday;
    private Friday friday;
    private Saturday saturday;
    private sunday sunday;
    private boolean isNightMode = false;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);




        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.applogo, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

        assert bitmapDrawable != null;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//             notification=new Notification.Builder(getActivity())
            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.applogo)
                    .setContentText("New Message")
                    .setSubText("New Message from sunny");
            notification = builder.build();

            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.applogo)
                    .setContentText("New Message")
                    .setSubText("New Message from sunny");
            notification = builder.build();

        }
//        notificationManager.notify(NOTIFICATION_ID,notification); // this is two start notification

        // Inflate the layout for this fragment


        notificationonoff = view.findViewById(R.id.notificationonoff);

        notificationonoff.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                notificationManager.cancelAll();
            } else {
                Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.applogo, null);
                BitmapDrawable bitmapDrawable1 = (BitmapDrawable) drawable1;

                assert bitmapDrawable1 != null;
                Bitmap largeIcon1 = bitmapDrawable1.getBitmap();

//                NotificationManager notificationManager1 = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification1;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    notification1 =new Notification.Builder(getActivity())
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                            .setLargeIcon(largeIcon1)
                            .setSmallIcon(R.drawable.applogo)
                            .setContentText("New Message")
                            .setSubText("New Message from sunny");

                    notification1 = builder.build();

                    notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
                } else {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                            .setLargeIcon(largeIcon)
                            .setLargeIcon(largeIcon1)
                            .setSmallIcon(R.drawable.applogo)
                            .setContentText("New Message")
                            .setSubText("New Message from sunny");
                    notification1 = builder.build();

                }
//                notificationManager.notify(NOTIFICATION_ID, notification1);


            }
        });


        aSwitch = view.findViewById(R.id.nigntmodebtn);

        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isNightMode = true;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            } else {
                isNightMode = false;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            }

        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        changeFragmentBackground();
    }


    private void changeFragmentBackground() {

        if (isNightMode) {
            if (monday != null) {
                monday.requireView().setBackgroundResource(R.drawable.nightback);
            }
            if (tuesday != null) {
                tuesday.requireView().setBackgroundResource(R.drawable.nightback);
            }
            // Repeat for other fragments
        } else {
            // Reset background to default
            if (monday != null) {
                monday.requireView().setBackgroundResource(android.R.color.transparent);
            }
            if (tuesday != null) {
                tuesday.requireView().setBackgroundResource(android.R.color.transparent);
            }
            // Repeat for other fragments
        }


    }


}