package com.example.mgpa1;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationManager {
    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public void playAnim(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    public void drawEnemy(Canvas canvas, Enemy enemy) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].drawNoScale(canvas, enemy.getRectangle());
    }

    public void drawObstacle(Canvas canvas, Obstacle obstacle) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].drawNoScale(canvas, obstacle.getRectangle());
    }

    public void drawCoin(Canvas canvas, Coin coin) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, coin.getRectangle());
    }

    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}