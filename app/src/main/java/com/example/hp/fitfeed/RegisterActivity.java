package com.example.hp.fitfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.Firebase;
import com.example.hp.fitfeed.com.example.hp.fitfeed.normal.classes.User;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private final String LOG_TAG = "RegisterActivity";
    private Button mRegisterButton;
    private EditText mNameValue;
    private EditText mUNameValue;
    private EditText mPasswordValue;
    private EditText mPasswordAgainValue;
    private EditText mAgeValue;
    private EditText mWeightValue;
    private EditText mHeightValue;
    private Firebase mRootRef;
    private RadioGroup mRadioG;
    private RadioButton mSex;
    private ProgressDialog mProgress;
    private void makeToast(String text, boolean duration)
    {
        if(duration)
        {
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.v(LOG_TAG, "In onCreate of RegisterActivity");
        mProgress = new ProgressDialog(this);
    }

    protected void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "In onStart of RegisterActivity");
        mUNameValue = (EditText) findViewById(R.id.newUsernameEditText);
        mPasswordValue = (EditText) findViewById(R.id.newPasswordEditText);
        mPasswordAgainValue = (EditText) findViewById(R.id.newPasswordEditText2);
        mAgeValue = (EditText) findViewById(R.id.ageEditText);
        mWeightValue = (EditText) findViewById(R.id.weightEditText);
        mHeightValue = (EditText) findViewById(R.id.heightEditText);
        mRadioG = (RadioGroup) findViewById(R.id.sexRadioGroup);
        mRegisterButton = (Button) findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "Pressed SignUp Button");
                int selected_id = mRadioG.getCheckedRadioButtonId();
                mSex = (RadioButton) findViewById(selected_id);
                register();
            }
        });
    }

    private void register(){
        mProgress.setMessage("Let's get started!");
        mProgress.show();
//        final String nameValue=mNameValue.getText().toString();
        final String uNameValue=mUNameValue.getText().toString();
        final String passwordValue=mPasswordValue.getText().toString();
        final String passwordAgainValue=mPasswordAgainValue.getText().toString();
        final String ageValue=mAgeValue.getText().toString();
        final String weightValue=mWeightValue.getText().toString();
        final String heightValue=mHeightValue.getText().toString();
        final String sexValue=mSex.getText().toString();

        if( !TextUtils.isEmpty(uNameValue)&& !TextUtils.isEmpty(passwordValue)&&
                !TextUtils.isEmpty(ageValue)&& !TextUtils.isEmpty(weightValue)&& !TextUtils.isEmpty(heightValue)&&
                !TextUtils.isEmpty(passwordAgainValue) ){
            int weight=Integer.parseInt(weightValue);
            int height=Integer.parseInt(heightValue);
            int age=Integer.parseInt(ageValue);
            User user=new User(uNameValue,passwordValue,age,weight,height,sexValue);
           addUserToFireBase(user);
             mProgress.dismiss();

             }    }

    public void addUserToFireBase(User user1)
    {
        final User user=user1;
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.v("UserClass","In addUserToDatabase");
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String user_id=mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db=mDatabase.child(user_id);
                    current_user_db.child("Age").setValue(user.getAge());
                    current_user_db.child("Weight").setValue(user.getWeight());
                    current_user_db.child("Height").setValue(user.getHeight());
                    current_user_db.child("Sex").setValue(user.getSex());
                    makeToast("Registration Successful",true);
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
   else
                makeToast("There was some error, Try again",true);
            }

        });
        }
}