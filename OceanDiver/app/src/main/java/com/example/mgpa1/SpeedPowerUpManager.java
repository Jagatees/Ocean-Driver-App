package com.example.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.media2.exoplayer.external.C;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SpeedPowerUpManager {

    // higer index = lower on screen = higer y value
    private ArrayList<Coin> coins; // store all the obstacles
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long initTime;
    private long startTime;
    private int score = 0;

    private Animation idle;
    private AnimationManager animManager;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;




    public SpeedPowerUpManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = initTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        coins = new ArrayList<>();
        populateObstacles();

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coint);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animManager = new AnimationManager(new Animation[]{idle});



    }


    int getScore(){
        return this.score;
    }

    // when the player hit a obstacle
    public boolean playerCollide(RectPlayer player){
        for (Coin ob : coins){
            if ( ob.playerCollide(player) ){
                score++;
                int xStart = (int)(Math.random() );
                coins.add(0, new Coin(obstacleHeight, color, xStart, coins.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap));
                coins.remove(coins.size() - 1 );
                return true;
            }
        }
        return false;
    }

    private void populateObstacles(){
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        // while we genrete the rectangle
        while (currY < 0){
            int xStart = (int)(Math.random() );
            // make the obsclae spawn above
            coins.add(new Coin(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }


    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime =  System.currentTimeMillis();

        // this is going to move the game over time and make it speed up

        // the number of sec then it will speed up the 2000 value
        float speed = (float) ((Math.sqrt (1 + ( startTime - initTime ) / 5000.0))* Constants.SCREEN_HEIGHT / ( 10000.0f ));


        for (Coin ob : coins){
            ob.IncreamentY(speed * elapsedTime);
        }

        if ( coins.get(coins.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT ){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            coins.add(0, new Coin(obstacleHeight, color, xStart, coins.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap));
            coins.remove(coins.size() - 1 );
        }




    }

    public void draw(Canvas canvas){
        for (Coin ob : coins){
            ob.draw(canvas);
            animManager.drawCoin(canvas, ob);
            int state = 0;
            animManager.playAnim(state);
            animManager.update();

            // use paint to draw stuff and get canvas to draw on it
            Paint paint = new Paint();
            paint.setTextSize(100);
            canvas.drawText(score + "Coins", 50, 200 + paint.descent() - paint.ascent(), paint);
        }
    }
}
