package com.example.mgpa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


// Code Written By : Jagateesvaran , 180776N


public class GamePage extends Activity {

    GamePanel gamePanel;
    String gameOver;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("GameOver");

    Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get Screen Dim
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(new GamePanel(this));

        //Music player code Done by Yanson
        MusicManager musicplayer = null;
        musicplayer.start(this, 1);

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); // need to test on androis phone

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String name = ds.getKey();
                    String name = String.valueOf(ds.getValue());
                    gameOver = name;
                }

                if ( gameOver.equals("true") ){
                    vibrator.vibrate(500); // not sure if it work need to test on a andoird phone
                    Intent intent = new Intent(GamePage.this, DeathScreen.class);
                    startActivity(intent);
                }else if (gameOver.equals("false"))  {
                   // setContentView(new GamePanel(this));

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
