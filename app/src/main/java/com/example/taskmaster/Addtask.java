package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//import com.example.taskmaster.Model.StateOfTask;
//import com.example.taskmaster.Model.Task;
//import com.example.taskmaster.database.DatabaseForTask;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Stateotask;
import com.amplifyframework.datastore.generated.model.Task;
//import com.example.taskmaster.Model.StateOfTask;
//import com.example.taskmaster.Model.StateOfTask;

import java.util.Date;

public class Addtask extends AppCompatActivity {
    public static final String TAG = "AddProductActivity";
//DatabaseForTask databaseForTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
/////////////////////
//        databaseForTask = Room.databaseBuilder(
//                        getApplicationContext(),
//                        DatabaseForTask.class,
//                        "database_For_Task")
//             //   .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();
//        Tasks = databaseForTask.taskDao().findAll();
        /////////////////////
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Stateotask.values()));

        Button saveone = (Button)  findViewById(R.id.saveone);
        saveone.setOnClickListener(v-> {
//            Task newtask = new Task(
//                    ((EditText) findViewById(R.id.savetilte)).getText().toString(),
//                    ((EditText) findViewById(R.id.savebody)).getText().toString(),
//                    new Date(),
//                    StateOfTask.fromString(spinner.getSelectedItem().toString());
//            );

            String title = ((EditText)findViewById(R.id.savetilte)).getText().toString();
            String body = ((EditText)findViewById(R.id.savebody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            Task newProduct = Task.builder()
                    .title(title)
                    .body(body)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                            .stateofTask((com.amplifyframework.datastore.generated.model.Stateotask) spinner.getSelectedItem()).build();
//                    .stateofTask((stateoTask) spinner.getSelectedItem()).build();

            Amplify.API.mutate(
                    ModelMutation.create(newProduct),
                    successResponse -> Log.i(TAG, "AddProductActivity.onCreate(): made a product successfully"),//success response
                    failureResponse -> Log.e(TAG, "AddProductActivity.onCreate(): failed with this response" + failureResponse)// in case we have a failed response
            );

//            Task a = new Task();
//            databaseForTask.taskDao().insertATask(newtask);
        });

    }
}