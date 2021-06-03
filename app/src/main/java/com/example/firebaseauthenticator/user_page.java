package com.example.firebaseauthenticator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class user_page extends AppCompatActivity {
    void signOut(){
        MainActivity.client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        moveToNext();
                    }
                });
    }
    void moveToNext(){
        Intent loggedIn=new Intent(this,MainActivity.class);
        TaskStackBuilder.create(this).addNextIntentWithParentStack(loggedIn).startActivities();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        TextView name=(TextView) findViewById(R.id.name);
        name.setText("Welcome "+account.getDisplayName());
        Button signOutButton=findViewById(R.id.signOut);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
}