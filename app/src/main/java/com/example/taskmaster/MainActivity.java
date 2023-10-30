package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskmaster.Model.StateOfTask;
import com.example.taskmaster.Model.Task;
import com.example.taskmaster.adapter.ProductListRecyclerVIewAdapter;
import com.example.taskmaster.database.DatabaseForTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
   public static final String  DATABASE_NAME = "Database_For_Task" ;

    DatabaseForTask databaseForTask;
//    List<Task> Tasks=null;

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
//DatabaseForTask

        setUpProductListRecyclerView();
  ////////////////////////////////////


//        databaseForTask = Room.databaseBuilder(
//                      getApplicationContext(),
//                       DatabaseForTask.class,
//                        DATABASE_NAME)
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//               .build();
//        Tasks = databaseForTask.taskDao().findAll();
//        Tasks.clear();
//        Tasks.addAll(databaseForTask.taskDao().findAll());
//        ProductListRecyclerVIewAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
       setUpProductListRecyclerView();
    }




        private void setUpProductListRecyclerView(){

        RecyclerView productListRecyclerView = (RecyclerView) findViewById(R.id.productListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        productListRecyclerView.setLayoutManager(layoutManager);

            databaseForTask = Room.databaseBuilder(
                            getApplicationContext(),
                            DatabaseForTask.class,
                            "database_For_Task")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        List<Task> Taskss = databaseForTask.taskDao().findAll();
            ProductListRecyclerVIewAdapter adapter = new ProductListRecyclerVIewAdapter(Taskss, this);
        productListRecyclerView.setAdapter(adapter);

    }

    }