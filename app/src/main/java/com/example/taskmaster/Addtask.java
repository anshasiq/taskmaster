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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Stateotask;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
//import com.example.taskmaster.Model.StateOfTask;
//import com.example.taskmaster.Model.StateOfTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Addtask extends AppCompatActivity {
    public static final String TAG = "AddProductActivity";
    public static final String TAGG = "EEE";

    Spinner s2 = null;
    Spinner spinner = null;
    CompletableFuture<List<Team>> TeamFuture = new CompletableFuture<List<Team>>();

//DatabaseForTask databaseForTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        spinner=findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Stateotask.values()));
        TeamFuture = new CompletableFuture<>();
        setUpSpinners();
       ////////////////////////
        Button saveone = (Button)  findViewById(R.id.saveone);
        saveone.setOnClickListener(v-> {


            String title = ((EditText)findViewById(R.id.savetilte)).getText().toString();
            String body = ((EditText)findViewById(R.id.savebody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
            String selectedContactString = s2.getSelectedItem().toString();
            List<Team> allteams=null;
            try {
                allteams=TeamFuture.get();
            }catch (InterruptedException ie){
                Log.e(TAG, " InterruptedException while getting contacts");
            }catch (ExecutionException ee){
                Log.e(TAG," ExecutionException while getting contacts");
            }

            Team selectedContact = allteams.stream()
                    .filter(c -> c.getTeamName().equals(selectedContactString))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Selected team not found"));

            Task newProduct = Task.builder()
                    .title(title)
                    .body(body)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .stateofTask((com.amplifyframework.datastore.generated.model.Stateotask) spinner.getSelectedItem())
                    .teamTask(selectedContact)
                    .build();


            Amplify.API.mutate(
                    ModelMutation.create(newProduct),
                    successResponse -> {Log.i(TAG, "AddProductActivity.onCreate(): made a product successfully");
                        Snackbar.make(findViewById(R.id.AddTaskk), "Task saved!", Snackbar.LENGTH_SHORT).show();
                    },
                    failureResponse -> Log.i(TAGG, "eroore "+ failureResponse)// in case we have a failed response
            );

        });



        //////////////////////


    }

    private void setUpSpinners() {
        s2 = findViewById(R.id.spinner2);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success ->
                {
                    Log.i(TAG, "Read Teams Successfully");
                    ArrayList<String> TeamNames = new ArrayList<>();
                    ArrayList<Team> Teams = new ArrayList<>();
                    for (Team team : success.getData()) {
                        Teams.add(team);
                        TeamNames.add(team.getTeamName());
                    }
                    TeamFuture.complete(Teams);
                    runOnUiThread(() ->
                    {
                        s2.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                TeamNames
                        ));
                    });
                },
                failure -> {
                    TeamFuture.complete(null);
                    Log.i(TAG, "Did not read Teams successfully");
                });}










}