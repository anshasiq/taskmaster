package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class SettingsPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Button save = (Button) findViewById(R.id.button4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor preferneceEditor= sharedPreferences.edit();
                EditText userNicknameEditText = (EditText) findViewById(R.id.editTextText3);
                String userNicknameString = userNicknameEditText.getText().toString();
                preferneceEditor.putString(KEY, userNicknameString);//k,v
                preferneceEditor.apply();
                Snackbar.make(findViewById(R.id.S), "Settings Saved :)", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsPage.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}