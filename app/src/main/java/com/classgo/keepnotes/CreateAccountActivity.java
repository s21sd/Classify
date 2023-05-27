package com.classgo.keepnotes;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    EditText emailedittext,passwordeditext,confirmpasswordedittext;
    Button createAccountbtn,loginbtntextView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailedittext=findViewById(R.id.email_edit_text);
        passwordeditext=findViewById(R.id.pass_edit_text);
        confirmpasswordedittext=findViewById(R.id.confirm_pass_edit_text);
        createAccountbtn=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.progress_bar);
        loginbtntextView=findViewById(R.id.login_text_view_btn);

        createAccountbtn.setOnClickListener(view -> createAccount());
        loginbtntextView.setOnClickListener(view -> finish());




    }

    void  createAccount()
    {
        String email=emailedittext.getText().toString();
        String password=passwordeditext.getText().toString();
        String confirmPassword=confirmpasswordedittext.getText().toString();

        boolean isValidated=validateData(email,password,confirmPassword);
        if(!isValidated)
        {
            return;
        }

        createAccountFireBase(email,password);

    }

    void createAccountFireBase(String email,String password)
    {
        // this is because of we want to show the create account btn again
        changeInProgress(true);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this, task -> {
            changeInProgress(false);
            if(task.isSuccessful())
            {
                // task successful

                Toast.makeText(CreateAccountActivity.this,"Successfully create account,Check email to verify",Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification();
                firebaseAuth.signOut();
                finish();
            }
            else{
                // task failure
//
                    Toast.makeText(CreateAccountActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );


    }
    void  changeInProgress(boolean inProgress)
    {
        if(inProgress)
        {
            progressBar.setVisibility(View.VISIBLE);
            createAccountbtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            createAccountbtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String email, String password, String confirmPassword)
    {
        // validate the data that are input by user
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailedittext.setError("Email is invalid");
            return false;
        }
        if(password.length()<6)
        {
            passwordeditext.setError("Password length is less than 6");
            return false;
        }
        if(!password.equals(confirmPassword))
        {
            confirmpasswordedittext.setError("Password not matched");
            return false;
        }
        return  true;
    }
}