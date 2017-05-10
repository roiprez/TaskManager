package com.example.roiprez.taskmanager;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Si no es p`´ublico va a crashear la aplicación
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView taskDescription;
        ImageButton expandButton;

        public MessageViewHolder(View v) {
            super(v);
            taskDescription = (TextView) itemView.findViewById(R.id.taskText);
            expandButton = (ImageButton) itemView.findViewById(R.id.expandButton);
        }
    }

    public static final String MESSAGES_CHILD = "messages";

    private ProgressBar mProgressBar;
    private ImageButton addTaskButton;
    private RecyclerView taskList;
    private LinearLayoutManager mLinearLayoutManager;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseRecyclerAdapter<Task, MessageViewHolder> mFirebaseAdapter;
    public static DatabaseReference mFirebaseDatabaseReference = null; //Reference to the database

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    protected static String mUsername;   //Name of the current user
    protected static String mUserUid;
    private String mPhotoUrl;   //Photo of the current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername="anonymous";

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        taskList = (RecyclerView) findViewById(R.id.taskRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //mLinearLayoutManager.setStackFromEnd(true); Esto es para que empiece a mostrar la lista desde el fondo
        taskList.setLayoutManager(mLinearLayoutManager);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mUserUid = mFirebaseUser.getUid();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();



        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Task, MessageViewHolder>(Task.class,
                R.layout.list_item,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(mUserUid)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Task task, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (task.getTaskText() != null) {
                    viewHolder.taskDescription.setText(task.getTaskText());
                    viewHolder.expandButton.setVisibility(TextView.VISIBLE);
                    viewHolder.expandButton.setVisibility(ImageView.VISIBLE);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int taskCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 || (positionStart >= (taskCount - 1) &&
                        lastVisiblePosition == (positionStart - 1))) {
                    taskList.scrollToPosition(positionStart);
                }
            }
        });

        taskList.setLayoutManager(mLinearLayoutManager);
        taskList.setAdapter(mFirebaseAdapter);


        addTaskButton = (ImageButton) findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTaskIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(addTaskIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = "anonymous";
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
