package com.example.hp.fitfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    void goToHomeActivity()
    {
        Intent intent = new Intent(getBaseContext(),HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

       private FirebaseAuth mAuth;
    public static final String TAG="username";
    public static final String DEFAULT="Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences= getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        String user= sharedPreferences.getString(TAG,DEFAULT);
        if(!user.equals(DEFAULT))
        {

            Log.v("MainActivity","going to home activity");
            goToHomeActivity();
        }

        mAuth= FirebaseAuth.getInstance();
       Button mRegisterButton =(Button)findViewById(R.id.signupButton);
        final EditText mUnameEditText= (EditText)findViewById(R.id.usernameEditText);
        final EditText mPassEditText= (EditText)findViewById(R.id.passwordEditText);
        Button   mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=mUnameEditText.getText().toString().trim();
                String pass=mPassEditText.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult>  task) {
                        if(task.isSuccessful())
                        {
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                             editor.putString(TAG,mUnameEditText.getText().toString());
                            goToHomeActivity();
                        }
                    }
                });
            }
        });


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getBaseContext(),RegisterActivity.class);
                   startActivity(intent);
                            }
        });


    }
}
