package com.outfit.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.R;

public class LoginActivity extends AppCompatActivity {

    TextView txtForgotPassword, txtRegister, edtLoginEmail, edtLoginPassword;
    Button btnLogin;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser mUser;

    private String email, pass;
    private static Boolean status = false;

    private String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                login();
            }
        });
    }

    private void login() {
        //logging user using login method provided by firebase
        mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                mUser = mAuth.getCurrentUser();
                comfirmPassenger();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btnLogin.setEnabled(true);
                txtRegister.setEnabled(true);
                Toast.makeText(LoginActivity.this, "ERROR: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void comfirmPassenger() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot.getKey().equals(mUser.getUid())){
                            Log.d(TAG, "login: 5");
                            status = true;
                            Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    if (!status) {
                        mAuth.signOut();
                    }
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "ERROR: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
//                    try {
//                        if (!status) {
//                            mAuth.signOut();
//                        }
//                    }catch (Exception e){}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Customers");
    }

    private void getData(){
        email = edtLoginEmail.getText().toString().trim();
        pass = edtLoginPassword.getText().toString().trim();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(mUser != null && mAuth != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
