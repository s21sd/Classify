package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    Button opengit,openlinkedin,openmail;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        opengit=view.findViewById(R.id.share_github);
        openlinkedin=view.findViewById(R.id.share_insta);
        openmail=view.findViewById(R.id.share_mail);

        opengit.setOnClickListener(view1 -> {
            String giturl="https://github.com/s21sd/Classify";
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(giturl));
            startActivity(intent);
        });

        openlinkedin.setOnClickListener(view12 -> {
            String linkdinaurl="https://www.linkedin.com/in/sunny-srivastava-a82996244";
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(linkdinaurl));
            startActivity(intent);
        });

        openmail.setOnClickListener(view13 -> startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:srivastavasunny359@gmail.com"))));





        return  view;
    }
}