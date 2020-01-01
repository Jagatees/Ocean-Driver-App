package com.example.mgpa1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

// Code Written By : Jagateesvaran , 180776N


public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
    }
    public void update()
    {
        y+=dx;
        if(y<-GamePanel.HEIGHT){
            y=0;
        }
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        if(y<0)
        {
            canvas.drawBitmap(image, x, y+GamePanel.WIDTH, null);
        }
    }
    public void setVector(int dx)
    {
        this.dx = dx;
    }
}


