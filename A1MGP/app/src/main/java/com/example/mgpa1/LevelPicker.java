package com.example.mgpa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LevelPicker extends Activity {


    Button btnOne, btnTwo, btnThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_level_picker);
        btnOne = findViewById(R.id.btnLevelOne);
        btnTwo = findViewById(R.id.btnLeveltwo);
        btnThree = findViewById(R.id.btnlevelthree);




        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelPicker.this, GamePage.class);
                startActivity(intent);
            }
        });


        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelPicker.this, LevelTwoPage.class);
                startActivity(intent);
            }
        });


        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelPicker.this, LevelThreePage.class);
                startActivity(intent);
            }
        });



    }
}
