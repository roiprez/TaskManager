package com.example.roiprez.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import static com.example.roiprez.taskmanager.MainActivity.mFirebaseDatabaseReference;
import static com.example.roiprez.taskmanager.MainActivity.mUserUid;

/**
 * Created by Roiprez on 15/04/2017.
 */

public class EditorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        final EditText taskEditText = (EditText) findViewById(R.id.editTextViewTask);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskText = String.valueOf(taskEditText.getText());
                Task newTask = new Task(mUserUid, taskText);
                mFirebaseDatabaseReference.child(mUserUid)
                        .push().setValue(newTask);
                Toast savedTask = Toast.makeText(getApplication().getApplicationContext(), "The task have been succesfully saved", Toast.LENGTH_SHORT);
                savedTask.show();
                finish();
            }
        });
    }
}
