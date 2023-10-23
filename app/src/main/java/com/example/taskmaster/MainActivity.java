package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNickname = preferences.getString(SettingsPage.KEY, "No nickname");
        TextView d = (TextView) findViewById(R.id.textView6);
        d.setText(userNickname);
        System.out.println(userNickname);
        Button alltasks = (Button) findViewById(R.id.button);
        Button addtasks = (Button) findViewById(R.id.button2);

        Button gowork = (Button) findViewById(R.id.button7);
        Button study = (Button) findViewById(R.id.button8);
        Button todo = (Button) findViewById(R.id.button9);
        Button setting = (Button) findViewById(R.id.button6);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(intent);
            }
        });
        gowork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskDetailPage.class);
                intent.putExtra("key", "gowork");
                startActivity(intent);
            }
        });
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskDetailPage.class);
                intent.putExtra("key", "study");
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskDetailPage.class);
                intent.putExtra("key", "todo");
                startActivity(intent);
            }
        });
        alltasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(MainActivity.this, Addtask.class);
                startActivity(go);
            }
        });
        addtasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go2 = new Intent(MainActivity.this, AllTasks.class);
startActivity(go2);
            }
        });


    }}