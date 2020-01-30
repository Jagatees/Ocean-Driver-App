package com.example.mgpa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// Code Written By : Jagateesvaran , 180776N

public class Login extends Activity {

    Button btnLogin, btnGoCreateAccount;

    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Set ir to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of the tool bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoCreateAccount = findViewById(R.id.btnCreateAccount);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnGoCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password  = editTextPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this   , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Login.this, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, SplashScreen.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "signInWithEmail:failure", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
