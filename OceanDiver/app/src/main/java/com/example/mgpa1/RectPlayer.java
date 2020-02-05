package com.example.mgpa1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Code Written By : Yanson , 180500N

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animManager;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(currentFirebaseUser.getUid()).child("Color");

    Bitmap idleImg;
    Bitmap walk1;
    Bitmap walk2;
    BitmapFactory bf = new BitmapFactory();

    public Rect getRectangle() {
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;


        idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
        walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);
        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

        // Check from Firebase which player clothes to chose from
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("TAG + " , (String) dataSnapshot.getValue());
                if (dataSnapshot.getValue().equals("Blue")){
                    idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
                    walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
                    walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);
                }else if (dataSnapshot.getValue().equals("Purple") ) {
                    idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.purplealien);
                    walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.purplealien);
                    walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.purplealien);
                }
                else if (dataSnapshot.getValue().equals("Red") ) {
                    idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.redalien);
                    walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.redalien);
                    walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.redalien);
                }

                idle = new Animation(new Bitmap[]{idleImg}, 2);
                walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

                Matrix m = new Matrix();
                m.preScale(-1, 1);
                walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
                walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

                walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

                animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    @Override
    public void draw(Canvas canvas) {
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {




        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;
        if(rectangle.left - oldLeft > 5)
            state = 1;
        else if(rectangle.left - oldLeft < -5)
            state = 2;

        animManager.playAnim(state);
        animManager.update();
    }
}