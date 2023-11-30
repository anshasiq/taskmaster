package com.example.taskmaster;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private String s3ImageKey = "";
    ActivityResultLauncher<Intent> activityResultLauncher;


    //DatabaseForTask databaseForTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        activityResultLauncher = getImagePickingActivityResultLauncher();
        spinner=findViewById(R.id.spinner);
        setUpAddImageButton();
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
                Log.e(TAG, " InterruptedException while getting Teams");
            }catch (ExecutionException ee){
                Log.e(TAG," ExecutionException while getting Teams");
            }

            Team selectedContact = allteams.stream()
                    .filter(c -> c.getTeamName().equals(selectedContactString))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Selected team not found"));

            Task newProduct = Task.builder()
                    .title(title)
                    .body(body)
                    .dateCreated(new Temporal.DateTime(new Date(), 0))
                    .stateofTask((Stateotask) spinner.getSelectedItem())
                    .teamTask(selectedContact)
                    .productImageS3Key(s3ImageKey)
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

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>()
                        {
                            @Override
                            public void onActivityResult(ActivityResult result)
                            {
                                Button addImageButton = findViewById(R.id.addimage);
                                if (result.getResultCode() == Activity.RESULT_OK)
                                {
                                    if (result.getData() != null)
                                    {
                                        Uri pickedImageFileUri = result.getData().getData();
                                        try
                                        {
                                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                            String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                            Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                            // Part 3: Use our InputStream to upload file to S3
//                                            switchFromAddButtonToDeleteButton(addImageButton);
                                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename,pickedImageFileUri);

                                        } catch (FileNotFoundException fnfe)
                                        {
                                            Log.e(TAG, "Could not get file from file picker! " + fnfe.getMessage(), fnfe);
                                        }
                                    }
                                }
                                else
                                {
                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
                                }
                            }
                        }
                );

        return imagePickingActivityResultLauncher;
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



    private void setUpAddImageButton()
    {
        Button addImageButton = (Button) findViewById(R.id.addimage);
        addImageButton.setOnClickListener(b ->
        {
            launchImageSelectionIntent();
        });

    }

    private void launchImageSelectionIntent()
    {
        // Part 1: Launch activity to pick file

        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");  // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent"
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        // Below is simple version for testing
        //startActivity(imageFilePickingIntent);

        // Part 2: Create an image picking activity result launcher
        activityResultLauncher.launch(imageFilePickingIntent);

    }
// Taken from https://stackoverflow.com/a/25005243/16889809
@SuppressLint("Range")
public String getFileNameFromUri(Uri uri) {
    String result = null;
    if (uri.getScheme().equals("content")) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
    }
    if (result == null) {
        result = uri.getPath();
        int cut = result.lastIndexOf('/');
        if (cut != -1) {
            result = result.substring(cut + 1);
        }
    }
    return result;
}
///////


    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename,Uri pickedImageFileUri)
    {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,  // S3 key
                pickedImageInputStream,
                success ->
                {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    // Part 4: Update/save our Product object to have an image key
                    s3ImageKey=(success.getKey());
//                    updateImageButtons();
                    ImageView productImageView = findViewById(R.id.editProductAddImageButton);
                    InputStream pickedImageInputStreamCopy = null;  // need to make a copy because InputStreams cannot be reused!
                    try
                    {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    }
                    catch (FileNotFoundException fnfe)
                    {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    productImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));

                },
                failure ->
                {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );
    }



}