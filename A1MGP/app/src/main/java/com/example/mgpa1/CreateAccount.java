package com.example.mgpa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

// Code Written By : Jagateesvaran , 180776N



public class CreateAccount extends Activity {

    EditText editTextUsername,editTextPassword;
    Button btnCreateAccount, btnReturn;

    String username, password;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set ir to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of the tool bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_create_account);



        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnCreateAccount = findViewById(R.id.buttonCreateAccount);
        btnReturn = findViewById(R.id.button4Back);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(CreateAccount.this, Login.class);
                                    startActivity(intent);

                                    Toast.makeText(CreateAccount.this, "Authentication Good.", Toast.LENGTH_SHORT).show();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(v);

//                Intent intent = new Intent(CreateAccount.this, Login.class);
//                startActivity(intent);
            }
        });
    }

    public void showAlertDialog(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("asd");
        alert.setMessage("You suer want to make account");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CreateAccount.this, "Yes", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CreateAccount.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}

