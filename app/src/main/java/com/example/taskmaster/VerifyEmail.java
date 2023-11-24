package com.example.taskmaster;

import static com.example.taskmaster.Addtask.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

public class VerifyEmail extends AppCompatActivity {
EditText Ve;
//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        Intent callingIntent= getIntent();
        String email = callingIntent.getStringExtra(Signup.SIGNUP_EMAIL_TAG);



        System.out.println(email);


        Button saveButton = (Button)findViewById(R.id.go_to_login);

        saveButton.setOnClickListener(v -> {
        Ve = ((EditText) findViewById(R.id.getnum));
        String theverify = Ve.getText().toString();
            System.out.println(Ve);
        Amplify.Auth.confirmSignUp(email,
                theverify,
                success ->
                {
                    Toast.makeText(VerifyEmail.this, " Verify account success!!", Toast.LENGTH_LONG);
                    Intent intent = new Intent(VerifyEmail.this, Login_page.class);
                    startActivity(intent);
                    Log.i(TAG,"verification succeeded: "+ success.toString());


                },
                failure ->
                {
                    Toast.makeText(VerifyEmail.this, " Verify account failed!!", Toast.LENGTH_LONG);
                    Log.i(TAG,"verification failed: "+ failure.toString());
                }
        );
        });
    }
}