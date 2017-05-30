package com.example.roiprez.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static com.example.roiprez.taskmanager.MainActivity.mFirebaseDatabaseReference;
import static com.example.roiprez.taskmanager.MainActivity.mUserUid;

public class EditorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        final EditText taskEditText = (EditText) findViewById(R.id.editTextViewTask);

        final Intent myIntent = getIntent(); // gets the previously created intent
        String taskTextFromEdition = myIntent.getStringExtra("Text");

        if (taskTextFromEdition != null) {
            taskEditText.getText().append(taskTextFromEdition);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskText = String.valueOf(taskEditText.getText());
                if (taskText.trim().length() <= 0) {
                    Toast notSavedTask = Toast.makeText(getApplication().getApplicationContext(), "The task is empty", Toast.LENGTH_SHORT);
                    notSavedTask.show();
                } else {
                    Task newTask = new Task(mUserUid, taskText);
                    mFirebaseDatabaseReference.child(mUserUid)
                            .push().setValue(newTask);
                    Toast savedTask = Toast.makeText(getApplication().getApplicationContext(), "The task have been succesfully saved", Toast.LENGTH_SHORT);
                    savedTask.show();
                    finish();
                }
            }
        });
    }
}
