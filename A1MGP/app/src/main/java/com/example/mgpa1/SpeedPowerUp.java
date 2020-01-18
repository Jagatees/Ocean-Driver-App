package com.example.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

public class SpeedPowerUp implements GameObject{

    private Rect rectangele1;
    final int random = new Random().nextInt(500) + 100; // [0, 60] + 20 => [20, 80]
    private int color;

    private Animation idle;
    private AnimationManager animManager;


    public Rect getRectangle(){
        return rectangele1;
    }

    public void IncreamentY(float y){
        rectangele1.top += y;
        rectangele1.bottom += y;
        rectangele1.top += y;
        rectangele1.bottom += y;
    }


    public SpeedPowerUp(int recHeight, int color, int startX, int startY , int playerGap){
        this.color = color;

        rectangele1 = new Rect(random, startY, startX, startY + recHeight);

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coint);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animManager = new AnimationManager(new Animation[]{idle});

    }


    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangele1, player.getRectangle() );
    }



    @Override
    public void draw(Canvas canvas) {

        animManager.draw(canvas, rectangele1);
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangele1.left;

        rectangele1.set(point.x - rectangele1.width()/2, point.y - rectangele1.height()/2, point.x + rectangele1.width()/2, point.y + rectangele1.height()/2);

        animManager.playAnim(0);
        animManager.update();
    }
}
