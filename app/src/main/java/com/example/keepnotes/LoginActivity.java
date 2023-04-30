package com.example.keepnotes;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText emailedittext,passwordeditext;
    Button loginbtn;
    ProgressBar progressBar;
    TextView createaccountbtntextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailedittext=findViewById(R.id.email_edit_text);
        passwordeditext=findViewById(R.id.pass_edit_text);
        loginbtn=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.progress_bar);
        createaccountbtntextview=findViewById(R.id.create_account_text_view_btn);

        loginbtn.setOnClickListener(view -> loginUser());
        createaccountbtntextview.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)));
    }
    void loginUser()
    {
        String email=emailedittext.getText().toString();
        String password=passwordeditext.getText().toString();


        boolean isValidated=validateData(email,password);
        if(!isValidated)
        {
            return;
        }

        loginAccountInFirebase(email,password);

    }
    void  loginAccountInFirebase(String email,String password)
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            changeInProgress(false);
            if(task.isSuccessful())
            {
                if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified())
                {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Toast.makeText(this, "Log in Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Email not verified, Please verify your email.", Toast.LENGTH_SHORT).show();

                }

            }
            else{
                Toast.makeText(this, "Log in Failed", Toast.LENGTH_SHORT).show();
                // log in failed
            }
        });
    }
    void  changeInProgress(boolean inProgress)
    {
        if(inProgress)
        {
            progressBar.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            loginbtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String email, String password)
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
        return true;
    }
}