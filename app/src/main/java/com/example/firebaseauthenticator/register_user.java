package com.example.firebaseauthenticator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class register_user extends AppCompatActivity implements View.OnClickListener {
    TextView nameTextView, ageTextView, emailTextView, pwdTextView;
    ProgressBar bar;
    Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        nameTextView=findViewById(R.id.name);
        ageTextView=findViewById(R.id.age);
        emailTextView=findViewById(R.id.email);
        pwdTextView=findViewById(R.id.pwd);
        register=(Button) findViewById(R.id.reg);
        register.setOnClickListener(this);
        bar=findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name=nameTextView.getText().toString().trim();
        String age=ageTextView.getText().toString().trim();
        String email=emailTextView.getText().toString().trim();
        String pwd=pwdTextView.getText().toString().trim();

        if(name.isEmpty()) {
            nameTextView.setError("Full name is required");
            nameTextView.requestFocus();
            return;
        }if(age.isEmpty()) {
            ageTextView.setError("Full name is required");
            ageTextView.requestFocus();
            return;
        }if(age.isEmpty()) {
            ageTextView.setError("Age is required");
            ageTextView.requestFocus();
            return;
        }if(email.isEmpty()) {
            emailTextView.setError("Email is required");
            emailTextView.requestFocus();
            return;
        }if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTextView.setError("Please input a valid email");
            emailTextView.requestFocus();
            return;
        }if(pwd.isEmpty()) {
            pwdTextView.setError("Password is required");
            pwdTextView.requestFocus();
            return;
        }if(pwd.length()<6){
            pwdTextView.setError("Password should be atleast 6 characters long");
            pwdTextView.requestFocus();
            return;
        }

        bar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(name,age,email);
                            Log.i("mine","First task done");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register_user.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(register_user.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);

                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(register_user.this, "Failed to register 2", Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                                                        Log.i("mine",task.getResult().toString());


                        }
                    }
                });

    }
}