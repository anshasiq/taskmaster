package com.example.taskmaster;

import static com.example.taskmaster.AddProAmlify.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.adapter.ProductListRecyclerVIewAdapter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences preferences2;
    String Tname = "TeamOne";
   public static final String  DATABASE_NAME = "Database_For_Task" ;

   ProductListRecyclerVIewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNickname = preferences.getString(SettingsPage.KEY, "No nickname");

        String Tname = preferences.getString(SettingsPage.KEY2, "No nickname");

        BB();
        TextView d = (TextView) findViewById(R.id.textView6);
        d.setText(userNickname);

        Button alltasks = (Button) findViewById(R.id.button);
        Button addtasks = (Button) findViewById(R.id.button2);

        Button login = (Button) findViewById(R.id.loginM);
        Button setting = (Button) findViewById(R.id.button6);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttt = new Intent(MainActivity.this, Login_page.class);
                startActivity(intenttt);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsPage.class);
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

//         Amplify.Auth.signUp("ahmadanshasi123@hotmail.com",
//                "p@ssw0rd523",
//                AuthSignUpOptions.builder()
//                        .userAttribute(AuthUserAttributeKey.email(), "ahmadanshasi123@hotmail.com")
//                        .userAttribute(AuthUserAttributeKey.nickname(), "T")
//                        .build(),
//                good ->
//                {
//                    Log.i(TAG, "Signup succeeded: "+ good.toString());
//                },
//                bad ->
//                {
//                    Log.i(TAG, "Signup failed with username: "+ "tselawe706@gmail.com"+ " with this message: "+ bad.toString());
//                }
//        );

////098701
//          Amplify.Auth.confirmSignUp("ahmadanshasi123@hotmail.com",
//                "098701",
//                success ->
//                {
//                    Log.i(TAG,"verification succeeded: "+ success.toString());
//
//                },
//                failure ->
//                {
//                    Log.i(TAG,"verification failed: "+ failure.toString());
//                }
//        );

//    Amplify.Auth.signIn("ahmadanshasi123@hotmail.com",
//                "p@ssw0rd523",
//                success ->
//                {
//                    Log.i(TAG, "Login succeeded: "+success.toString());
//                },
//                failure ->
//                {
//                    Log.i(TAG, "Login failed: "+failure.toString());
//                }
//        );

        // next we want to log out from out system
        /*Amplify.Auth.signOut(
                () ->
                {
                    Log.i(TAG,"Logout succeeded");
                },
                failure ->
                {
                    Log.i(TAG, "Logout failed");
                }
        );*/




    }
    @Override
    protected void onResume() {
        super.onResume();
       setUpProductListRecyclerView();
         Tname = preferences.getString(SettingsPage.KEY2, "No nickname");

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        String username="";
        if (authUser == null){
            Button loginButton = (Button) findViewById(R.id.loginM);
            loginButton.setVisibility(View.VISIBLE);
            Button logoutButton = (Button) findViewById(R.id.logoutM);
            logoutButton.setVisibility(View.INVISIBLE);
        }else{
            username = authUser.getUsername();
            Log.i(TAG, "Username is: "+ username);
            Button loginButton = (Button) findViewById(R.id.loginM);
            loginButton.setVisibility(View.INVISIBLE);
            Button logoutButton = (Button) findViewById(R.id.logoutM);
            logoutButton.setVisibility(View.VISIBLE);
//
            String username2 = username; // ugly way for lambda hack
            Amplify.Auth.fetchUserAttributes(
                    success ->
                    {
                        Log.i(TAG, "Fetch user attributes succeeded for username: "+username2);
                        for (AuthUserAttribute userAttribute: success){
                            if(userAttribute.getKey().getKeyString().equals("email")){
                                String userEmail = userAttribute.getValue();
                                runOnUiThread(() ->
                                {
                                    ((TextView)findViewById(R.id.hello)).setText(userEmail);
                                });
                            }
                        }
                    },
                    failure ->
                    {
                        Log.i(TAG, "Fetch user attributes failed: "+failure.toString());
                    }
            );
        }

    }

private void BB(){
    Button logoutButton = (Button) findViewById(R.id.logoutM);
    logoutButton.setOnClickListener(v->
    {
        Amplify.Auth.signOut(
                () ->
                {
                    Log.i(TAG,"Logout succeeded");
                    runOnUiThread(() ->
                    {
                        ((TextView)findViewById(R.id.hello)).setText("");
                    });
                    Intent goToLogInIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(goToLogInIntent);
                },
                failure ->
                {
                    Log.i(TAG, "Logout failed");
                    runOnUiThread(() ->
                    {
                        Toast.makeText(MainActivity.this, "Log out failed", Toast.LENGTH_LONG);
                    });
                }
        );
    });
}


        @SuppressLint("SuspiciousIndentation")
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
                            if(databaseProduct.getTeamTask().getTeamName().equals(Tname))
                            Tasks.add(databaseProduct);
                                Log.i(TAG, databaseProduct.getTeamTask().toString());
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