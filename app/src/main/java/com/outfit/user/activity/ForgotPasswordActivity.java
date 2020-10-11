package com.outfit.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInstaller;
import android.media.MediaCas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.R;
import com.outfit.user.model.CustomerRegisterModel;
import com.sun.mail.smtp.SMTPTransport;

import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//TODO: only left with sending mail
public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnSubmit;
    ImageView back;
    String TAG = "Forgot";

    EditText edtLoginEmail;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private CustomerRegisterModel registerModel;
    private String email;
    private Random random;
    private String forgotPass;

    private Boolean status = false;

    //mail
    // for example, smtp.mailgun.org
    private static final String SMTP_SERVER = "smtp server ";
    private static final String USERNAME = "Gurpreet Singh";
    private static final String PASSWORD = "PREeT8054.";

    private static final String EMAIL_FROM = "Gs9638200@gmail.com";
    private static String EMAIL_TO = "";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Request of Password change";
    private static String EMAIL_TEXT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtLoginEmail.getText().toString().trim();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                            if(snapshot.child("emailTv").getValue().toString().equals(email)){
                                updateCustomerWithNewPassword();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                    }
                });

            }
        });
    }

    private void updateCustomerWithNewPassword() {
        myRef.child(mAuth.getUid()).child("passTv").setValue(forgotPass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                EMAIL_TO = email;
                EMAIL_TEXT = "This is your new password. Login using bellow password.\n\t\t\t\t" + forgotPass;
                sendMail();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPasswordActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendMail() {

    }


    private void init() {
        btnSubmit = findViewById(R.id.btnSubmit);
        back = findViewById(R.id.back);
        edtLoginEmail = findViewById(R.id.edtLoginEmail1);

        //        firebase variables declaration
        mAuth = FirebaseAuth.getInstance();
//        updating new user information to the database
        myRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Customers");

        random = new Random();
        forgotPass = String.format("%04d", random.nextInt(10000));
    }


}
