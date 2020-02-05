package com.example.mgpa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

// Code Written By : Jagateesvaran , 180776N


public class SettingPage extends Activity {


    MusicManager musicManager = new MusicManager();

    //Leaderboard
    TextView textView1, textView2, textView3;
    ToggleButton toggle, toggle2;

    Button btnBack;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(currentFirebaseUser.getUid()).child("Settings");
    DatabaseReference MusicmyRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Settings");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_page);
        onStartup();

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


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(SettingPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonEffect(btnBack);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myRef.child("Music").setValue("ON");
                    onResume();
                } else {
                    // The toggle is disabled
                    myRef.child("Music").setValue("OFF");
                    onPause();
                }
            }
        });

        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myRef.child("Accelerometer").setValue("ON");
                } else {
                    // The toggle is disabled
                    myRef.child("Accelerometer").setValue("OFF");
                }
            }
        });
    }


    void onStartup(){
        toggle =  findViewById(R.id.toggleButton);
        toggle2 =  findViewById(R.id.toggleButton2);
        btnBack =  findViewById(R.id.btnbacks);
    }



    // Code by Yanson
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
    // Code by Yanson
    private void buttonEffect(Button button)
    {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0a9a9a9, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

}
