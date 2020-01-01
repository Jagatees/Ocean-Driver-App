package com.example.mgpa1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Code Written By : Jagateesvaran , 180776N

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 5000;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference myRefColor = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set ir to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of the tool bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myRefColor.child("Color").setValue("Blue");
                Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(homeIntent);
            }
        }, SPLASH_TIME_OUT);
    }
}
