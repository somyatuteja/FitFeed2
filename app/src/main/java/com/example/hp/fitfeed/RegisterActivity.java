package com.example.hp.fitfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegisterButton;
    private EditText mNameValue;
    private EditText mUNameValue;
    private EditText mPasswordValue;
    private EditText mPasswordAgainValue;
    private EditText mAgeValue;
    private EditText mWeightValue;
    private EditText mHeightValue;
    private Firebase mRootRef;
   // private TextView mNameKey;
   // private TextView mUNameKey;
   // private TextView mPasswordKey;
    //private TextView mPasswordAgainKey;
    //private TextView mAgeKey;
    //private TextView mWeightKey;
    //private TextView mHeightKey;

    private RadioGroup mRadioG;
    private RadioButton mSex;


    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;


   // boolean passwordFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.v("RegisterActivity","starting");
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress=new ProgressDialog(this);

       // mRootRef=new Firebase("https://fitfeed-7ae6a.firebaseio.com/Users");
        mNameValue=(EditText)findViewById(R.id.newNameEditText);
        mUNameValue=(EditText)findViewById(R.id.newUsernameEditText);
        mPasswordValue=(EditText)findViewById(R.id.newPasswordEditText);
        mPasswordAgainValue=(EditText)findViewById(R.id.newPasswordEditText2);
        mAgeValue=(EditText)findViewById(R.id.ageEditText);
        mWeightValue=(EditText)findViewById(R.id.weightEditText);
        mHeightValue=(EditText)findViewById(R.id.heightEditText);
        mRadioG=(RadioGroup)findViewById(R.id.sexRadioGroup);



       /* while(passwordFlag!=true){
            if(mPasswordValue.equals(mPasswordAgainValue)){
                passwordFlag=true;
                Log.v("RegisterActivity","looping silly!");
            }
            else
            {
                Toast.makeText(RegisterActivity.this,"Please confirm the password correctly",Toast.LENGTH_LONG).show();
            }
        }*/


        // mNameKey=(TextView)findViewById(R.id.newNameTextView) ;
        //mUNameKey=(TextView)findViewById(R.id.newUNameTextView) ;
        //mPasswordKey=(TextView)findViewById(R.id.newPasswordTextView);
        //mPasswordAgainKey=(TextView)findViewById(R.id.passwordTextView2);
        //mAgeKey=(TextView)findViewById(R.id.ageTextView);
        //mWeightKey=(TextView)findViewById(R.id.weightTextView);
        //mHeightKey=(TextView)findViewById(R.id.heightTextView);







        mRegisterButton=(Button)findViewById(R.id.registerButton);
                mRegisterButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        int selected_id=mRadioG.getCheckedRadioButtonId();

                        mSex=(RadioButton)findViewById(selected_id);



                        registerFunc();



                    }
                });

    }

    private void registerFunc(){
        final String nameValue=mNameValue.getText().toString();
        final String uNameValue=mUNameValue.getText().toString();
        final String passwordValue=mPasswordValue.getText().toString();
        final String passwordAgainValue=mPasswordAgainValue.getText().toString();
        final String ageValue=mAgeValue.getText().toString();
        final String weightValue=mWeightValue.getText().toString();
        final String heightValue=mHeightValue.getText().toString();
        final String sexValue=mSex.getText().toString();



        // String nameKey=mNameKey.getText().toString();
        //String uNameKey=mNameKey.getText().toString();
        //String passwordKey=mPasswordKey.getText().toString();
        //String ageKey=mAgeKey.getText().toString();
        //String weightKey=mWeightKey.getText().toString();
        //String heightKey=mHeightKey.getText().toString();

        if(!TextUtils.isEmpty(nameValue)&& !TextUtils.isEmpty(uNameValue)&& !TextUtils.isEmpty(passwordValue)&&
                !TextUtils.isEmpty(ageValue)&& !TextUtils.isEmpty(weightValue)&& !TextUtils.isEmpty(heightValue)&&
                !TextUtils.isEmpty(passwordAgainValue) ){

            mProgress.setMessage("Let's get started!");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(uNameValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String user_id=mAuth.getCurrentUser().getUid();
                        Log.v("RegisterActivity","here1");
                        DatabaseReference current_user_db=mDatabase.child(user_id);

                        current_user_db.child("Email").setValue(uNameValue);
                        current_user_db.child("Name").setValue(nameValue);
                        current_user_db.child("Age").setValue(ageValue);
                        Log.v("RegisterActivity","here2");
                        current_user_db.child("Weight").setValue(weightValue);
                        current_user_db.child("Height").setValue(heightValue);
                        current_user_db.child("Sex").setValue(sexValue);
                        mProgress.dismiss();

                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {

                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter the details correctly!", Toast.LENGTH_LONG);

                    }
                }
            });

        }



        //Firebase childRef=mRootRef.child(nameKey);
        //childRef.setValue(nameValue);
    }
}
