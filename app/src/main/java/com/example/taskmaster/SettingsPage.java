package com.example.taskmaster;

import static com.example.taskmaster.Addtask.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SettingsPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String KEY = "key";
    public static final String KEY2 = "key2";

    Spinner s2 = null;
    CompletableFuture<List<Team>> TeamFuture = new CompletableFuture<List<Team>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setUpSpinners();



        Button save = (Button) findViewById(R.id.button4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor preferneceEditor= sharedPreferences.edit();
                SharedPreferences.Editor preferneceEditor2= sharedPreferences.edit();
                String af = s2.getSelectedItem().toString();
//                System.out.println(af);
                EditText userNicknameEditText = (EditText) findViewById(R.id.editTextText3);
                String userNicknameString = userNicknameEditText.getText().toString();
                preferneceEditor.putString(KEY, userNicknameString);//k,v
                preferneceEditor.apply();

                preferneceEditor2.putString(KEY2, af);//k,v
                preferneceEditor2.apply();
//

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

    private void setUpSpinners() {
        s2 = findViewById(R.id.spinner3);
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