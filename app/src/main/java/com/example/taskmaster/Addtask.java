package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.taskmaster.Model.StateOfTask;
import com.example.taskmaster.Model.Task;
import com.example.taskmaster.database.DatabaseForTask;

import java.util.Date;

public class Addtask extends AppCompatActivity {
DatabaseForTask databaseForTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
/////////////////////
        databaseForTask = Room.databaseBuilder(
                        getApplicationContext(),
                        DatabaseForTask.class,
                        "database_For_Task")
             //   .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
//        Tasks = databaseForTask.taskDao().findAll();
        /////////////////////
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                StateOfTask.values()));
        Button saveone = (Button)  findViewById(R.id.saveone);
        saveone.setOnClickListener(v-> {
            Task newtask = new Task(
                    ((EditText) findViewById(R.id.savetilte)).getText().toString(),
                    ((EditText) findViewById(R.id.savebody)).getText().toString(),
                    new Date(),
                    StateOfTask.fromString(spinner.getSelectedItem().toString())
            );
            databaseForTask.taskDao().insertATask(newtask);
        });

    }
}