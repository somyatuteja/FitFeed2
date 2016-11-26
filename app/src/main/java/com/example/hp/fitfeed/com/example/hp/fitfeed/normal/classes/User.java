package com.example.hp.fitfeed.com.example.hp.fitfeed.normal.classes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hp.fitfeed.MainActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String email;
    private String password;
    private String name;
    private String sex;
    private int age;
    private int weight;
    private int height;
    private String id;
    public boolean status;
  public User(String email, String password, int age, int weight, int height, String sex)
  {
      this.setEmail(email);
      this.setPassword(password);
      this.setName(name);
      this.setAge(age);
      this.setHeight(height);
      this.setWeight(weight);
      this.setSex(sex);
  }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void saveUser() {
        Firebase myFirebaseRef = new Firebase("https://tai-school.firebaseio.com/");
        myFirebaseRef = myFirebaseRef.child("users").child(getId());
        myFirebaseRef.setValue(this);
    }
}
