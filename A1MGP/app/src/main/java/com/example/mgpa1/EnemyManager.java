package com.example.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Vibrator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EnemyManager {

    // higer index = lower on screen = higer y value
    private ArrayList<Enemy> enemies; // store all the obstacles
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long initTime;
    private long startTime;
    private int score = 0;


    private Animation idle;
    private AnimationManager animManager;

    public EnemyManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = initTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        enemies = new ArrayList<>();
        populateObstacles();
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shark2);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animManager = new AnimationManager(new Animation[]{idle});
    }

    int getScore(){
        return this.score;
    }

    // when the player hit a obstacle
    public boolean playerCollide(RectPlayer player){
        for (Enemy ob : enemies){
            if ( ob.playerCollide(player) ){
                score = score + 1;

                int xStart = (int)(Math.random() );
                enemies.add(0, new Enemy(obstacleHeight, color, xStart, enemies.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap));
                enemies.remove(enemies.size() - 1 );

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
            enemies.add(new Enemy(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }


    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime =  System.currentTimeMillis();

        // this is going to move the game over time and make it speed up

        // the number of sec then it will speed up the 2000 value
        float speed = (float) ((Math.sqrt (1 + ( startTime - initTime ) / 5000.0))* Constants.SCREEN_HEIGHT / ( 10000.0f ));


        for (Enemy ob : enemies){
            ob.IncreamentY(speed * elapsedTime);
        }

        if ( enemies.get(enemies.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT ){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            enemies.add(0, new Enemy(obstacleHeight, color, xStart, enemies.get(0).getRectangle().top - obstacleHeight - obstacleGap , playerGap));
            enemies.remove(enemies.size() - 1 );
        }

    }

    public void draw(Canvas canvas){
        for (Enemy ob : enemies){
            ob.draw(canvas);
            animManager.drawEnemy(canvas, ob);
            int state = 0;
            animManager.playAnim(state);
            animManager.update();

        }
    }



}
