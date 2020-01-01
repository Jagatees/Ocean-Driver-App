package com.example.mgpa1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.zip.CheckedOutputStream;

// Code Written By : Jagateesvaran , 180776N


// to move the object down the screen
public class ObstacleManager {

    // higer index = lower on screen = higer y value
    private ArrayList<Obstacle> obstacles; // store all the obstacles
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long initTime;
    private long startTime;
    private int score = 0;
    private Color colorRGB;


    private static final long START_TIME_IN_MILLIS = 600000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    int i ;


    private Animation idle;
    private AnimationManager animManager;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference P_ref = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("P_LeaderBoard").push();
    DatabaseReference G_ref = FirebaseDatabase.getInstance().getReference("G_LeaderBoard").push();

    // playergap == width of the gap the plauer have to fit throw
    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = initTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstacles();

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.dirt);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animManager = new AnimationManager(new Animation[]{idle});
        startTimer();
    }

    int getScore(){
        return this.score;
    }

    // when the player hit a obstacle
    public boolean playerCollide(RectPlayer player){
        for (Obstacle ob : obstacles){
            if ( ob.playerCollide(player) ){
                P_ref.setValue(i);
                G_ref.setValue(i);
                return true;
            }
        }
        return false;
    }

    private void populateObstacles(){
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        // while we genrete the rectangle
        while (currY < 0){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            // make the obsclae spawn above

            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }


    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime =  System.currentTimeMillis();

        // this is going to move the game over time and make it speed up

        // the number of sec then it will speed up the 2000 value
        float speed = (float) ((Math.sqrt (1 + ( startTime - initTime ) / 2000.0))*Constants.SCREEN_HEIGHT / (10000.0f ));

        for (Obstacle ob : obstacles){
            ob.IncreamentY(speed * elapsedTime);
        }

        if ( obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT ){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap));
            obstacles.remove(obstacles.size() - 1 );
            score++;
        }

    }

    public void draw(Canvas canvas){
        for (Obstacle ob : obstacles){
            ob.draw(canvas);
            animManager.drawObstacle(canvas, ob);
            int state = 0;
            animManager.playAnim(state);
            animManager.update();


            // use paint to draw stuff and get canvas to draw on it
            Paint paint = new Paint();
            paint.setTextSize(100);
            canvas.drawText(i + "sec", 50, 50 + paint.descent() - paint.ascent(), paint);

        }
    }



    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                i++;

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();

        mTimerRunning = true;

    }


}
