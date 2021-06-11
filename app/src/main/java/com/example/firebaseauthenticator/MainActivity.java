package com.example.firebaseauthenticator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GoogleSignInOptions gso;
    static GoogleSignInClient client;

    private TextView register;

    private void signIn() {
        Intent signInIntent=client.getSignInIntent();
        startActivityForResult(signInIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){

            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//            The GoogleSignInAccount object i.e task contains information about the signed-in user, such as the user's name.
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            moveToNext();
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
            Log.i("mine", "signInResult:failed code=" + e.getStatusCode());
        }

    }
    void moveToNext(){
        Intent loggedIn=new Intent(this,user_page.class);
        TaskStackBuilder.create(this).addNextIntentWithParentStack(loggedIn).startActivities();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView)findViewById(R.id.reg);
        register.setOnClickListener(this);






//        configure Google Sign-In to request the user data
         gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
//        create a GoogleSignInClient object
         client= GoogleSignIn.getClient(this,gso);

//        Check for an existing signed-in user
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account !=null){
            //a user is signed in
            moveToNext();
        }
        else {
            SignInButton signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_WIDE);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Sign in button clicked", Toast.LENGTH_SHORT).show();
                    signIn();
                }
            });

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg:
                startActivity(new Intent(this,register_user.class));
                break;

        }
    }
}