package com.outfit.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.outfit.user.R;
import com.outfit.user.model.CustomerRegisterModel;

public class RegisterActivity extends AppCompatActivity {

    //not essential var's
    private static final String TAG = "driverRegisterActivity";

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private CustomerRegisterModel registerModel;

    Button btnRegister;
    private EditText firstNameTv, lastNameTv, emailTv, passTv, rePassTv;

    private String firstNameStr, lastNameStr, emailStr, passStr, rePassStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Toast.makeText(RegisterActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                getData();

                if(!passStr.equals(rePassStr)){
                    passTv.setError("Password Mismatch");
                }else{
                    createNewUser();
                }

            }
        });
    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: " + user.getEmail());
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "ERROR: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
//        final DatabaseReference newFeedRef = myRef;

        myRef.child(user.getUid()).setValue(registerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "ERROR: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // get input data
    private void getData() {
        firstNameStr = firstNameTv.getText().toString().trim();
        lastNameStr = lastNameTv.getText().toString().trim();
        emailStr = emailTv.getText().toString().trim();
        passStr = passTv.getText().toString().trim();
        rePassStr = rePassTv.getText().toString().trim();

        registerModel = new CustomerRegisterModel();
        registerModel.setFirstNameTv(firstNameStr);
        registerModel.setLastNameTv(lastNameStr);
        registerModel.setEmailTv(emailStr);
        registerModel.setPassTv(passStr);

    }

    // init variables
    private void init() {
        btnRegister = findViewById(R.id.btnRegister);
        firstNameTv = findViewById(R.id.edtFirstName);
        lastNameTv = findViewById(R.id.edtLastName);
        emailTv = findViewById(R.id.edtEmail);
        passTv = findViewById(R.id.edtPasword);
        rePassTv = findViewById(R.id.edtConfirmPasword);


//        firebase variables declaration
        mAuth = FirebaseAuth.getInstance();
//        updating new user information to the database
        myRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Customers");


    }


}
