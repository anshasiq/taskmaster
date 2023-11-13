package com.example.taskmaster;

import static com.example.taskmaster.AddProAmlify.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.adapter.ProductListRecyclerVIewAdapter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
   public static final String  DATABASE_NAME = "Database_For_Task" ;

   ProductListRecyclerVIewAdapter adapter;


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

        setUpProductListRecyclerView();
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


            List<Task> Tasks = new ArrayList<>();

            Amplify.API.query(
                    ModelQuery.list(Task.class),
                    success ->
                    {
                        Log.i(TAG, "Read Product successfully");

                        Tasks.clear();
                        for (Task databaseProduct : success.getData()){
                            Tasks.add(databaseProduct);
                        }

                        runOnUiThread(() ->{
                            adapter.notifyDataSetChanged();
                        });
                    },
                    failure -> Log.i(TAG, "Did not read products successfully")

            );
            adapter = new ProductListRecyclerVIewAdapter(Tasks, this);
            productListRecyclerView.setAdapter(adapter);


    }

    }