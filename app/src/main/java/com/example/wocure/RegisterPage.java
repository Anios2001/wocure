package com.example.wocure;
import android.app.Activity;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;


import com.firebase.client.Firebase;
import com.firebase.client.authentication.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterPage extends Activity {

    SavedPref savedPreferences = SavedPref.getSavedPreferenceInstance();
    String uid = new String();
    EditText t1,t2,t3,t4,t5,t6,t7,t8;
    Button b1;
    ProgressBar p1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        mAuth = FirebaseAuth.getInstance();
        t1= (EditText) findViewById(R.id.email);
        t2= (EditText) findViewById(R.id.password);
        t3= (EditText) findViewById(R.id.name);
        t4= (EditText) findViewById(R.id.mob_no);
        t5= (EditText) findViewById(R.id.age);
        t6= (EditText) findViewById(R.id.sos1);
        t7= (EditText) findViewById(R.id.sos2);
        t8= (EditText) findViewById(R.id.sos3);
        p1=(ProgressBar) findViewById(R.id.progress) ;
        b1=(Button) findViewById(R.id.edt_save);
        uid = savedPreferences.getData("uid");
        Firebase.setAndroidContext(getApplicationContext());


    }
    public static boolean isValidAge(int target){
        if(target<100){
            return true;
        }
        else
            return false;
    }



    public void click(View view){


        p1.setVisibility(View.VISIBLE);
        final boolean reg_check[] = {true};

        String email= t1.getText().toString().trim();
        String pass= t2.getText().toString();
        String name= t3.getText().toString();
        String mob_no= t4.getText().toString();
        int mob_len= mob_no.length();
        int age = Integer.parseInt(t5.getText().toString());
        boolean check_age = isValidAge(age);
        String sos1= t6.getText().toString();
        int sos1_len=sos1.length();
        String sos2= t7.getText().toString();
        int sos2_len=sos2.length();
        String sos3= t8.getText().toString();
        int sos3_len=sos3.length();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            t1.setError("enter correct format");
            t1.requestFocus();
        }
        if(pass.length()<6){
            t2.setError("length cannot be less than 6");
            t2.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            Toast.makeText(RegisterPage.this, "User Already Exist",
                                    Toast.LENGTH_SHORT).show();
                            reg_check[0]=false;


                        }


                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterPage.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                             reg_check[0]=false;
                        }
                    }
                });
        FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();
        if (muser != null) {
            uid = muser.getUid();
        }else
        {
            reg_check[0]= false;
        }



        if((reg_check[0]==true)&&(check_age==true)&&(mob_len==10)&&(sos1_len==10)&&(sos2_len==10)&&(sos3_len==10))
        {

            User user = new User();
            email.replace('.','A');
            user.setName(name);
            user.setMob_no(mob_no);
            user.setAge(age);
            user.setEsos1(sos1);
            user.setEsos2(sos2);
            user.setEsos3(sos3);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mDatabaseRef = database.getReference();
            mDatabaseRef.child("User").child(uid).setValue(user);
            savedPreferences.setData("uid",uid);
            startActivity(new Intent(RegisterPage.this, HomeScreen.class));
            finish();
            p1.setVisibility(View.GONE);

        }
            else{
                if(check_age==false)
                    t5.setError("enter correct age");
                if(mob_len!=10)
                    t4.setError("check length");
                if(sos1_len!=10)
                    t6.setError("check length");
                if(sos2_len!=10)
                    t7.setError("check length");
                if(sos3_len!=10)
                    t8.setError("check length");
                p1.setVisibility(View.GONE);
                if (reg_check[0]==false){
                    Toast.makeText(getApplicationContext(),"Check is false", Toast.LENGTH_SHORT).show();
                }

            }

    }
}