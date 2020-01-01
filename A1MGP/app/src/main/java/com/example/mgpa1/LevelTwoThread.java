package com.example.mgpa1;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

// Code Written By : Jagateesvaran , 180776N

public class LevelTwoThread extends Thread {

    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private LevelTwoGame levelTwoThread;
    private boolean running;
    public static Canvas canvas;


    public LevelTwoThread(SurfaceHolder surfaceHolder, LevelTwoGame levelTwoThread){
        super();
        this.surfaceHolder = surfaceHolder;
        this.levelTwoThread = levelTwoThread;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int framecount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;


            try {
                canvas = this.surfaceHolder.lockCanvas();
                System.out.println(surfaceHolder);

                synchronized (surfaceHolder){
                    this.levelTwoThread.update();
                    this.levelTwoThread.draw(canvas);
                }

            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                if ( canvas != null ){

                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e) {e.printStackTrace();}


                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {

                if ( waitTime > 0 ){

                    this.sleep(waitTime);

                }

            }catch (Exception e) {e.printStackTrace();}


            totalTime += System.nanoTime() - startTime;
            framecount++;

            if (framecount == MAX_FPS  ){
                averageFPS = 1000/ ((totalTime / framecount) / 1000000);
                framecount = 0;
                totalTime = 0;
                System.out.println(averageFPS);

            }

        }

    }


    public void setRunning(boolean running) {
        this.running = running;
    }
}
