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

        TextView textView5 = findViewById(R.id.textView5);
        String receivedData2 = intent.getStringExtra("getBody");
        textView5.setText(receivedData2);

        TextView textView7 = findViewById(R.id.getd);
        String receivedData3 = intent.getStringExtra("getdate");
        textView7.setText(receivedData3);

        TextView textView8 = findViewById(R.id.textView7);
        String receivedData4 = intent.getStringExtra("getstate");
        textView8.setText(receivedData4);

        TextView textView9 = findViewById(R.id.textView9);
        String receivedData6 = intent.getStringExtra("getTeam");
        System.out.println(receivedData6);
        textView9.setText(receivedData6);


//        textView7


    }
}