package com.example.hp.fitfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

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

    private ProgressDialog mProgress;
       private FirebaseAuth mAuth;
    public static final String TAG="username";
    public static final String DEFAULT="Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         SharedPreferences sharedPreferences= getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        Log.v("MainActivity","jwebkjf");

        String user= sharedPreferences.getString("username",DEFAULT);
        Log.v("MainActivity","got preference : "+user);
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
        mProgress = new ProgressDialog(this);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "login pressed");
                String uname = mUnameEditText.getText().toString().trim();
                String pass = mPassEditText.getText().toString().trim();
                if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(pass)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter the username and password", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    mAuth.signInWithEmailAndPassword(uname, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {

                                    mProgress.setMessage("Signing In");
                                    mProgress.show();
                                }
                                catch (Exception e)
                                {
                                    Log.v("","this is error1");
                                }
                                Log.v("MainActivity", "successful");
                                SharedPreferences sharedPreferences1 = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                Log.v("MainActivity", "something");
                                editor.putString("username", mUnameEditText.getText().toString());
                                editor.commit();
                                Toast toast = Toast.makeText(getApplicationContext(), "Logging you in", Toast.LENGTH_LONG);
                                toast.show();
                                Log.v("MainActivity", "blah");
                                mProgress.dismiss();
                                goToHomeActivity();

                            }
                            if (task.isComplete()) {
                                Log.v("MainActivity", "complete");
                            } else Log.v("MainActivity", "bakwaas");
                        }
                    });
                }
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