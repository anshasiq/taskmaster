package com.example.taskmaster;

import static com.example.taskmaster.Addtask.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class Signup extends AppCompatActivity {
    public static final String TAG = "AddProductActivity";

    EditText email;
    EditText password;
    Context callingActivity;

    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Button saveButton = (Button)findViewById(R.id.Entersign);

        saveButton.setOnClickListener(v ->
                {
                    email = ((EditText) findViewById(R.id.editTextTextEmailAddress));
                    String emailstring = email.getText().toString();

                    password = ((EditText) findViewById(R.id.editTextTextPassword));
                    String passwordstring = password.getText().toString();
//                    System.out.println(emailstring + " " + passwordstring);
//                    Intent goToOrderFormIntent = new Intent(callingActivity, VerifyEmail.class);
//                    goToOrderFormIntent.putExtra("email", emailstring);
                    Amplify.Auth.signUp(emailstring,
                passwordstring,
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), emailstring)
                        .userAttribute(AuthUserAttributeKey.nickname(), "T")
                       .build(),
                good ->
                {


//                    Intent intent = new Intent(Signup.this, VerifyEmail.class);
//                    startActivity(intent);
                    Intent goToVerifyIntent= new Intent(Signup.this, VerifyEmail.class);
                    goToVerifyIntent.putExtra(SIGNUP_EMAIL_TAG, emailstring);
                    startActivity(goToVerifyIntent);
                    Log.i(TAG, "Signup succeeded: "+ good.toString());
                },
                bad ->
               {
//                Intent goToOrderFormIntent = new Intent(callingActivity, VerifyEmail.class);
//                    goToOrderFormIntent.putExtra("email", emailstring);
                   Toast.makeText(Signup.this, "Signup failed", Toast.LENGTH_LONG);
                    Log.i(TAG, "Signup failed with username: "+ "tselawe706@gmail.com"+ " with this message: "+ bad.toString());
                }
        );

                });

    }



}