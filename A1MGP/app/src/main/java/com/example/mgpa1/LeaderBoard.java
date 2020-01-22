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
import java.util.Vector;


// Code Written By : Jagateesvaran , 180776N

public class LeaderBoard extends Activity {


    MusicManager musicManager = new MusicManager();

    //Leaderboard
    TextView textView1, textView2, textView3;
    TextView textView1Coin, textView2Coin, textView3Coin;
    Button btnBack;


    //DatabaseReference g_leaderBoard = FirebaseDatabase.getInstance().getReference("G_LeaderBoard");

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference p_leaderbaord = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("P_LeaderBoard");
    DatabaseReference MusicmyRef = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Settings");


    private ArrayList<Integer> pScore = new ArrayList<Integer>();
    private ArrayList<Integer> pCoin = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set ir to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Get rid of the tool bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_leader_board);

        textView1 = (TextView) findViewById(R.id.firstPlace);
        textView2 = (TextView) findViewById(R.id.secondPlace);
        textView3 = (TextView) findViewById(R.id.thridPlace);

        btnBack = (Button) findViewById(R.id.btnBack);


        textView1Coin = (TextView) findViewById(R.id.txtCoinfisrt);
        textView2Coin = (TextView) findViewById(R.id.txtSecound);
        textView3Coin = (TextView) findViewById(R.id.txtThirdCoin);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderBoard.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // redo the leader board
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("G_LeaderBoard");
        p_leaderbaord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String name = ds.getKey();
                    String name = String.valueOf(ds.getValue());
                    pScore.add(Integer.parseInt(name));

                }
                Collections.sort(pScore);



                if (pScore.size() <= 0){
                    textView1.setText("0");
                    textView2.setText("0");
                    textView3.setText("0");
                } else if (pScore.size() == 1){
                    textView1.setText(pScore.get(pScore.size() - 1).toString());
                    textView2.setText("0");
                    textView3.setText("0");
                }
                else if (pScore.size() == 2){
                    textView1.setText(pScore.get(pScore.size() - 1).toString());
                    textView2.setText(pScore.get(pScore.size() - 2).toString());
                    textView3.setText("0");
                }
                else if (pScore.size() >= 3){
                    textView1.setText(pScore.get(pScore.size() - 1).toString());
                    textView2.setText(pScore.get(pScore.size() - 2).toString());
                    textView3.setText(pScore.get(pScore.size() - 3).toString());
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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



        // redo the leader board
        DatabaseReference coinss = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Coins");
        coinss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //String name = ds.getKey();
                    String name = String.valueOf(ds.getValue());
                    pCoin.add(Integer.parseInt(name));
                }

               Collections.sort(pCoin);



                if (pCoin.size() <= 0){
                    textView1Coin.setText("0");
                    textView2Coin.setText("0");
                    textView3Coin.setText("0");
                } else if (pCoin.size() == 1){
                    textView1Coin.setText(pCoin.get(pCoin.size() - 1).toString());
                    textView2Coin.setText("0");
                    textView3Coin.setText("0");
                }
                else if (pCoin.size() == 2){
                    textView1Coin.setText(pCoin.get(pCoin.size() - 1).toString());
                    textView2Coin.setText(pCoin.get(pCoin.size() - 2).toString());
                    textView3Coin.setText("0");
                }
                else if (pCoin.size() >= 3){
                    textView1Coin.setText(pCoin.get(pCoin.size() - 1).toString());
                    textView2Coin.setText(pCoin.get(pCoin.size() - 2).toString());
                    textView3Coin.setText(pCoin.get(pCoin.size() - 3).toString());
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
}
