package com.example.minterin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minterin.helperS.PrefsHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView tvToRegist;
    EditText email, password;
    Button signIn;
    FirebaseAuth mFirebaseAuth;
    ProgressBar pb_masuk;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvToRegist = findViewById(R.id.tv_daftar);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        signIn = findViewById(R.id.btn_masuk);
        mFirebaseAuth = mFirebaseAuth.getInstance();
        pb_masuk = findViewById(R.id.pb_login);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    //Toast.makeText(LoginActivity.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();

                } else {
                    //Jika Belum Login
//                    Toast.makeText(LoginActivity.this, "Silahkan Login", Toast.LENGTH_SHORT)
//                            .show();
                }
            }
        };

        //Sign In Button Event
        Boolean statusLogin = PrefsHelper.sharedInstance(this).getStatusLogin();
        if (statusLogin) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailVal = email.getText().toString();
                final String passwordVal = password.getText().toString();

                if(emailVal.isEmpty()){
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                }
                else if(passwordVal.isEmpty()){
                    password.setError("Password Tidak Boleh Kosong");
                    password.requestFocus();
                }
                else if(emailVal.isEmpty() && passwordVal.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi Seluruh Data", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(!(emailVal.isEmpty()) && !(passwordVal.isEmpty())){
                    pb_masuk.setVisibility(View.VISIBLE);
                    signIn.setVisibility(View.GONE);
                    mFirebaseAuth.signInWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                pb_masuk.setVisibility(View.GONE);
                                signIn.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, ReferalActivity.class);
                                PrefsHelper.sharedInstance(LoginActivity.this).setStatusLogin(true);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                    pb_masuk.setVisibility(View.GONE);
                    signIn.setVisibility(View.VISIBLE);
                }
            }
        });

        //Intent to Registration Page
        tvToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
