package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);


//        Intent intent = null;
//        String receivedData = intent.getStringExtra("key");
//        findViewById(R.id.textView4).setOnClickListener(System.out.println("sdf"););
        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("key");
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText(receivedData);

    }
}