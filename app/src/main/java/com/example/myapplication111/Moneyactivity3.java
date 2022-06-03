package com.example.myapplication111;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class Moneyactivity3 extends SurfaceView implements SurfaceHolder.Callback {
    DrawThread drawThread;

    public Moneyactivity3(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }

        }
    }
}

class DrawThread extends Thread {
    private final int REDRAW_TIME = 10;
    public Sprite money;
    public Sprite money2;
    public Sprite money3;
    private boolean runFlag = false;
    private final SurfaceHolder surfaceHolder;
    private int viewWidth;
    private int viewHeight;
    private long mStartTime;
    private long mPrevRedrawTime;


    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle); // <-- todo player not found
        int w = b.getWidth() / 5;
        int h = b.getHeight() / 3;
        Rect firstFrame = new Rect(0, 0, w, h);
        money = new Sprite(100, 2000, firstFrame, b);
        firstFrame = new Rect(4 * w, 0, 5 * w, h);
        money2 = new Sprite(2000, 250, firstFrame, b);
        firstFrame = new Rect(4 * w, 0, 5 * w, h);
        money3 = new Sprite(2000, 250, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                money.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                money2.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                money3.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }

    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewWidth = w;
        viewHeight = h;
    }

    public void setRunning(boolean run) {
        runFlag = run;
        mPrevRedrawTime = getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1_000_000;
    }

    @Override
    public void run() {
        Canvas canvas;
        mStartTime = getTime();

        while (runFlag) {
            long curTime = getTime();
            long elapsedTime = curTime - mPrevRedrawTime;
            if (elapsedTime < REDRAW_TIME)
                continue;

            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    update();
                    draw(canvas);
                }
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }

            mPrevRedrawTime = curTime;

        }
    }


    public void draw(Canvas canvas) {

        viewHeight = canvas.getHeight();
        viewWidth = canvas.getWidth();

        canvas.drawARGB(250, 127, 199, 255);
        money.draw(canvas);
        money2.draw(canvas);
        money3.draw(canvas);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);


    }

    protected void update() {
        money.update(REDRAW_TIME);
        money2.update(REDRAW_TIME);
        money3.update(REDRAW_TIME);


    }


}

class Sprite {
    private Bitmap bitmap;
    private final List<Rect> frames;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private final double x;
    private final double y;

    public Sprite(double x, double y, Rect initialFrame, Bitmap bitmap) {

        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.frames = new ArrayList<Rect>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();

    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame % frames.size();
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public int getFramesCount() {
        return frames.size();
    }

    public void addFrame(Rect frame) {
        frames.add(frame);
    }

    public void update(int ms) {

        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int) x, (int) y, (int) (x + frameWidth), (int) (y + frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);
    }


}