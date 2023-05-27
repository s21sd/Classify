package com.classgo.keepnotes;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText emailedittext,passwordeditext;
    private Button loginbtn,createaccountbtntextview;
    private ProgressBar progressBar;
//    private ProgressDialog progressDialog;
     TextView forgotPass;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        emailedittext = findViewById(R.id.email_edit_text);
        passwordeditext = findViewById(R.id.pass_edit_text);
        loginbtn = findViewById(R.id.login_btn);
        createaccountbtntextview = findViewById(R.id.create_account_text_view_btn);
        forgotPass=findViewById(R.id.forgetPass);
        progressBar=findViewById(R.id.progress_bar);

        loginbtn.setOnClickListener(view -> loginUser());
        createaccountbtntextview.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

        // this is for the forgot password activity

        forgotPass.setOnClickListener(view -> forgotUser());


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

        firebaseAuth=FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            changeInProgress(false);
            progressBar.setVisibility(View.GONE);
            loginbtn.setEnabled(true);
            if(task.isSuccessful())
            {

                if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified())
                {

                    startMainActivity();


                }
                else{
                    Toast.makeText(this, "Email not verified, Please verify your email.", Toast.LENGTH_SHORT).show();

                }

            }
            else{
                Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void startMainActivity() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        finish();

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
            passwordeditext.setError("Password length must be at least 6 characters");
            return false;
        }
        return true;
    }


    private void forgotUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.forgot_dialog, null);
        EditText emailBox = dialogView.findViewById(R.id.emailBox);



        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnReset).setOnClickListener(view1 -> {
            String userEmail = emailBox.getText().toString();
            if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(LoginActivity.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "Unable to send , failed", Toast.LENGTH_SHORT).show();

                }
            });
        });

        dialogView.findViewById(R.id.btnCancel).setOnClickListener(view12 -> dialog.dismiss());
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();

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

}