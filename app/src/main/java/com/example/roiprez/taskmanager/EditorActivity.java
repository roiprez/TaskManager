package com.example.roiprez.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.roiprez.taskmanager.MainActivity.mFirebaseDatabaseReference;
import static com.example.roiprez.taskmanager.MainActivity.mUserUid;

public class EditorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);
        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
        final EditText taskEditText = (EditText) findViewById(R.id.editTextViewTask);

        final Intent myIntent = getIntent(); // gets the previously created intent
        final String taskTextFromEdition = myIntent.getStringExtra("Text");
        final String idFromEdition = myIntent.getStringExtra("id");

        if (taskTextFromEdition != null) {
            taskEditText.getText().append(taskTextFromEdition);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String taskText = String.valueOf(taskEditText.getText());
                if (taskText.trim().length() <= 0) {
                    Toast notSavedTask = Toast.makeText(getApplication().getApplicationContext(), "The task is empty", Toast.LENGTH_SHORT);
                    notSavedTask.show();
                } else {
                    if (taskTextFromEdition != null) {
                        mFirebaseDatabaseReference.child(mUserUid).orderByChild("id").equalTo(idFromEdition).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();

                                    Task newTask = new Task(mUserUid, taskText);
                                    mFirebaseDatabaseReference.child(mUserUid)
                                            .push().setValue(newTask);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("OnCancelled", "onCancelled", databaseError.toException());
                            }
                        });
                    }
                    else{
                        Task newTask = new Task(mUserUid, taskText);
                        mFirebaseDatabaseReference.child(mUserUid)
                                .push().setValue(newTask);
                    }

                    Toast savedTask = Toast.makeText(getApplication().getApplicationContext(), "The task have been succesfully saved", Toast.LENGTH_SHORT);
                    savedTask.show();

                    //Intent resultIntent = new Intent(EditorActivity.this, MainActivity.class);
                    //resultIntent.putExtra("FinishedEdit", true);
                    //startActivity(resultIntent);

                    finish();
                }
            }
        });
    }
}
