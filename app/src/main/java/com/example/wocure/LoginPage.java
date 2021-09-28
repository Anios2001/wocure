package com.example.wocure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginPage extends Activity {

    String uid;
    EditText t1, t2;
    Button b1, b2;
    ProgressBar p1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mAuth = FirebaseAuth.getInstance();
        t1 = (EditText) findViewById(R.id.edt_username);
        t2 = (EditText) findViewById(R.id.edt_password);
        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.edt_register);
        p1 = (ProgressBar) findViewById(R.id.progress_login);
    }
    public void click_login(View view){
        String email= t1.getText().toString().trim();
        String pass= t2.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            t1.setError("enter correct format");
            t1.requestFocus();
        }
        if(pass.length()<6){
            t2.setError("length cannot be less than 6");
            t2.requestFocus();
        }
        p1.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("MSG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this, "User Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this,HomeScreen.class).putExtra(null,false));
                            finish();
                            p1.setVisibility(View.GONE);
                            FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();

                            if (muser != null) {
                                uid = muser.getUid();
                            }
                            SavedPref savedPreferences = SavedPref.getSavedPreferenceInstance();
                            savedPreferences.setData("uid",uid);

                        }

                        if (!task.isSuccessful()) {
                            Log.w("MSG", "signInWithEmail", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            p1.setVisibility(View.GONE);
                        }

                    }
                });
    }
    public void click_register(View view){
        startActivity(new Intent(LoginPage.this, RegisterPage.class));
        finish();
    }

}