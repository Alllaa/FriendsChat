package com.example.aa.friendschat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener { FirebaseUser user;
    EditText etUserName, etPassword;
    Button bLogin;
    TextView txRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        txRegister = (TextView) findViewById(R.id.txRegister);
        bLogin.setOnClickListener(this);
        txRegister.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getBaseContext(), Drawer.class));
                    finish();
                } else {
                    // User is signed out
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == txRegister)
        {
            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
        }
        else if(v == bLogin)
        {
            String str_EmailAddress = etUserName.getText().toString().trim();
            String str_Password = etPassword.getText().toString().trim();
            if (!str_EmailAddress.isEmpty() && !str_Password.isEmpty())
            {
                mAuth.signInWithEmailAndPassword(str_EmailAddress,str_Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Drawer.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else
            {
                Toast.makeText(getApplicationContext(), "Please enter your data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
