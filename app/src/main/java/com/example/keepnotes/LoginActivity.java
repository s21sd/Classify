package com.example.keepnotes;

import android.app.ProgressDialog;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailedittext,passwordeditext;
    Button loginbtn;
    ProgressBar progressBar;
    TextView createaccountbtntextview;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    TextView googlesignin;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        googlesignin=findViewById(R.id.logingoogle);
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();


        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);


        googlesignin.setOnClickListener(view -> signIn());

        emailedittext=findViewById(R.id.email_edit_text);
        passwordeditext=findViewById(R.id.pass_edit_text);
        loginbtn=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.progress_bar);
        createaccountbtntextview=findViewById(R.id.create_account_text_view_btn);

        loginbtn.setOnClickListener(view -> loginUser());
        createaccountbtntextview.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)));
    }
     void signIn() {
        Intent signinIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000)
        {
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
              GoogleSignInAccount account=  task.getResult(ApiException.class);
                finish();
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);


            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            User user1=new User();
                            user1.setUserId(user.getUid());
                            user1.setName(user.getDisplayName());
                            user1.setProfile(user.getPhotoUrl().toString());

                            database.getReference().child("Users").child(user.getUid()).setValue(user1);

                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                });

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