package com.example.taskmaster;

import static com.example.taskmaster.Addtask.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Edit_Activity extends AppCompatActivity {




    private EditText nameEditText;
    private EditText bodyEditText;
    private Task productToEdit= null;
    private CompletableFuture<Task> productCompletableFuture=null;

    public String Id ;

    public static final String TAG = "AddProductActivity";
    private EditText descriptionEditText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);




        productCompletableFuture = new CompletableFuture<>();

        setUpSaveButton();
        setUpDeleteButton();

        Intent intent = getIntent();
        Id = (intent.getStringExtra("Id"));
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success ->
                {
                    Log.i(TAG,"Read products Successfully");

                    for (Task databaseproduct: success.getData()){
                        if(databaseproduct.getId().equals(Id)){
                            productCompletableFuture.complete(databaseproduct);
                        }}
                    runOnUiThread(() ->
                    {
                        //Update UI element
                    });
                },
                failure -> Log.i(TAG, "Did not read product successfully")
        );
        try {
            productToEdit = productCompletableFuture.get();
        }catch (InterruptedException ie){
            Log.e(TAG, "InterruptedException while getting product");
            Thread.currentThread().interrupt();
        }catch (ExecutionException ee){
            Log.e(TAG, "ExecutionException while getting product");
        }

        nameEditText = ((EditText) findViewById(R.id.editTaskName));
        String receivedData2 = productToEdit.getTitle();
        nameEditText.setText(receivedData2);

        bodyEditText= ((EditText) findViewById(R.id.editTaskBody));
        String receivedData3 = productToEdit.getBody();
        bodyEditText.setText(receivedData3);

//productToEdit.getBody().toString();
        System.out.println(productToEdit.getBody());

    }
    private void setUpSaveButton()
    {
        Button saveButton = (Button)findViewById(R.id.saveB);

        saveButton.setOnClickListener(v ->
        {
        Task updatetask = Task.builder()

                .title(nameEditText.getText().toString())
                .id(productToEdit.getId())
                .body(bodyEditText.getText().toString())
                .dateCreated(productToEdit.getDateCreated())
                .stateofTask(productToEdit.getStateofTask())
                .teamTask(productToEdit.getTeamTask())
                .build();

        System.out.println(nameEditText.getText().toString());
        Amplify.API.mutate(
                ModelMutation.update(updatetask),  // making a GraphQL request to the cloud
                successResponse ->
                {
                    Log.i(TAG, "EditProductActivity.onCreate(): edited a product successfully");
                    // TODO: Display a Snackbar
                    Snackbar.make(findViewById(R.id.Editact), "Task has saved!", Snackbar.LENGTH_SHORT).show();
                },  // success callback
                failureResponse -> Log.i(TAG, "EditProductActivity.onCreate(): failed with this response: " + failureResponse)  // failure callback
        );


        });
    }


    private void setUpDeleteButton(){
        Button deleteButton = (Button) findViewById(R.id.deleteB);
        deleteButton.setOnClickListener(v ->{

            Amplify.API.mutate(
                    ModelMutation.delete(productToEdit),
                    successResponse ->
                    {
                        Log.i(TAG, "EditProductActivity.onCreate(): deleted a product successfully");
                        Snackbar.make(findViewById(R.id.Editact), "Task has Deleted", Snackbar.LENGTH_SHORT).show();
                        Intent goToProductListActivity = new Intent(Edit_Activity.this, MainActivity.class);
                        startActivity(goToProductListActivity);
                    },
                    failureResponse -> Log.i(TAG,"EditProductActivity.onCreate(): failed with this response: "+ failureResponse)
            );
        });
    }


}