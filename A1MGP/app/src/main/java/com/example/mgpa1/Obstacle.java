package com.example.mgpa1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.Random;

public class Obstacle implements GameObject{


    private Rect rectangle, rectangle2;
    private int color;



    public Rect getRectangle(){
        return rectangle;
    }



    public void IncreamentY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public void IncreamentX(float x){
        rectangle.top += x;
        rectangle.bottom += x;
        rectangle2.top += x;
        rectangle2.bottom += x;
    }




    public Obstacle(int recHeight, int color, int startX, int startY , int playerGap){
        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + recHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + recHeight );
    }



    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle, player.getRectangle() )|| Rect.intersects(rectangle2, player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update(){

    }














}
