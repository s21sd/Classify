package com.classgo.keepnotes;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailedittext,passwordeditext;
    Button loginbtn;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    TextView createaccountbtntextview,forgotPass;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;



    // google sign in

//    TextView googlesignin;
//    GoogleSignInOptions gso;
//    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");


        emailedittext = findViewById(R.id.email_edit_text);
        passwordeditext = findViewById(R.id.pass_edit_text);
        loginbtn = findViewById(R.id.login_btn);
        createaccountbtntextview = findViewById(R.id.create_account_text_view_btn);
        forgotPass=findViewById(R.id.forgetPass);
        progressBar=findViewById(R.id.progress_bar);
//        googlesignin = findViewById(R.id.logingoogle);


        loginbtn.setOnClickListener(view -> loginUser());
        createaccountbtntextview.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

        // this is for the forgot password activity

        forgotPass.setOnClickListener(view -> fogotUSer());





        // this is for the google
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (gAccount != null) {
//            finish();
//            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            assert account != null;
//            intent.putExtra("text",account.getId());
//            startActivity(intent);
//        }
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                Intent data = result.getData();
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                try {
//                 task.getResult(ApiException.class);
//
//                    finish();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } catch (ApiException e) {
//                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        googlesignin.setOnClickListener(view -> {
//            Intent signInIntent=mGoogleSignInClient.getSignInIntent();
//            activityResultLauncher.launch(signInIntent);
//
//        });
    }

    private void fogotUSer() {
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
                Toast.makeText(this, "Log in Failed", Toast.LENGTH_SHORT).show();
                // log in failed
            }
        });
    }



    private void startMainActivity() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        Toast.makeText(this, "Log in Successful", Toast.LENGTH_SHORT).show();
        finish();

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