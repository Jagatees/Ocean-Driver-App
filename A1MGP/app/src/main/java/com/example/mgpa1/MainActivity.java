package com.example.mgpa1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.media2.MediaUtils.TAG;

// Code Written By : Jagateesvaran , 180776N


public class MainActivity extends Activity {

    Button button, btnSetting, btnLeaderBoard, btnCustomiza, btnLogout;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference MusicmyRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Settings");

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("GameOver");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRef.child("GameOver").setValue("false");
        MusicmyRef.child("Music").setValue("ON");


        //Music player done by Yanson
        MusicManager musicplayer = null;
        if (musicplayer.getCurrentMusic() != 0 && musicplayer.getCurrentMusic() != -1){
            musicplayer.reset(this, 0, false);
        }
        else
        {
            musicplayer.start(this, 0);
        }
        //Music player done by Yanson



        MusicmyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Music").getValue().equals("ON")){
                    onResume();
                }else if (dataSnapshot.child("Music").getValue().equals("OFF")){
                    onPause();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Set ir to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of the tool bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Setting the Screen width and height stores in the constatnt class
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(R.layout.activity_main);




        btnLeaderBoard = findViewById(R.id.btnLeader);
        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderBoard.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.startGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelPicker.class);
                startActivity(intent);

            }
        });

        btnSetting = findViewById(R.id.btnOptions);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent);
            }
        });

        btnCustomiza = findViewById(R.id.btnCustomiose);
        btnCustomiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Customization.class);
                startActivity(intent);
            }
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }


    //Music player done by Yanson
    @Override
    protected void onPause() {
        super.onPause();
        MusicManager musicplayer = null;
        musicplayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicManager musicplayer = null;
        musicplayer.start(this, 0);
    }

    @Override
    protected void onStart(){
        super.onStart();
        MusicManager musicplayer = null;
        musicplayer.start(this,0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MusicManager musicplayer = null;
        musicplayer.start(this, 0);
    }

    //Music player done by Yanson

}
