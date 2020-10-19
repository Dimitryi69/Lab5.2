package com.example.l52;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText edEmail, edPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edEmail = findViewById(R.id.edMail);
        edPassword = findViewById(R.id.edPass);
        mAuth = FirebaseAuth.getInstance();
    }
    public void onExistingClick(View view)
    {
        Intent i = new Intent(this, LogInAct.class);
        startActivity(i);
        finish();
    }
    public void onRegisterClick(View view)
    {
        if(edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Field are empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        mAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString());
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Register.this, "Registration is unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}