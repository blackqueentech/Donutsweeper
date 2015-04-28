package com.example.budapest.donutsweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;


public class GameView extends View {

    private Paint paintBorder;
    private Paint paintBackGround;
    private Paint paintText;
    private Paint paintBitmap;
    private Paint paintBackGroundVisited;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintText = new Paint();
        paintText.setColor(Color.rgb(214, 133, 220));
        paintText.setTextSize(100);
        paintText.setStyle(Paint.Style.STROKE);

        paintBorder = new Paint();
        paintBorder.setColor(Color.WHITE);
        paintBorder.setStrokeWidth(5);
        paintBorder.setStyle(Paint.Style.STROKE);

        paintBackGround = new Paint();
        paintBackGround.setColor(Color.DKGRAY);
        paintBackGround.setStyle(Paint.Style.FILL);

        paintBackGroundVisited = new Paint();
        paintBackGroundVisited.setColor(Color.LTGRAY);
        paintBackGroundVisited.setStyle(Paint.Style.FILL);

        paintBitmap = new Paint();
        paintBitmap.setAntiAlias(true);
        paintBitmap.setFilterBitmap(true);
        paintBitmap.setDither(true);

    }

    public Bitmap getImage (int id, int width, int height) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
        Bitmap img = Bitmap.createScaledBitmap(bmp, width, height, true);
        bmp.recycle();
        return img;
    }

    private Bitmap bitmapDonut = null;
    private Bitmap bitmapSprout = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBackGround);

        drawGameArea(canvas);
        drawSquares(canvas);

    }

    private void drawSquares(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                float centerX = i * getWidth() / 5 + getWidth() / 10;
                float centerY = j * getHeight() / 5 + getHeight() / 10;
                int radius = getHeight() / 10 - 2;
                if (!DonutSweeperBoard.getInstance().getFieldContent(i, j).isHidden &&
                        DonutSweeperBoard.getInstance().getFieldContent(i, j).isDonut) {

                    // draw a circle at the center of the field
                    // X coordinate: left side of the square + half width of the square
                    //canvas.drawCircle(centerX, centerY, radius, paintBorder);
                    if (bitmapDonut == null) {
                        bitmapDonut = getImage(R.drawable.donut, getWidth() / 5, getHeight() / 5);
                    }
                    canvas.drawBitmap(bitmapDonut, centerX - 45, centerY - 45, paintBitmap);

                } else if (!DonutSweeperBoard.getInstance().getFieldContent(i, j).isHidden &&
                        DonutSweeperBoard.getInstance().getFieldContent(i, j).isSprout) {
                    // draw an X
//                    canvas.drawLine(i * getWidth() / 5, j * getHeight() / 5,
//                            (i + 1) * getWidth() / 5,
//                            (j + 1) * getHeight() / 5, paintBorder);
//
//                    canvas.drawLine((i + 1) * getWidth() / 5, j * getHeight() / 5,
//                            i * getWidth() / 5, (j + 1) * getHeight() / 5, paintBorder);
                    if (bitmapSprout == null) {
                        bitmapSprout = getImage(R.drawable.sprout, getWidth() / 5, getHeight() / 5);
                    }
                    canvas.drawBitmap(bitmapSprout, centerX - 45, centerY - 45, paintBitmap);
                } else if (!DonutSweeperBoard.getInstance().getFieldContent(i, j).isHidden &&
                        !DonutSweeperBoard.getInstance().getFieldContent(i, j).isSprout) {

                        canvas.drawText(
                                String.valueOf(DonutSweeperBoard.getInstance().findSprouts(i, j)),
                                centerX - 25, centerY + 35, paintText
                        );
//                    } else {
//                        canvas.drawText(
//                                String.valueOf(DonutSweeperBoard.getInstance().findSprouts(i, j)),
//                                centerX - 25, centerY + 35, paintText
//                        );
                        //canvas.drawRect(i + 60, j + 60, getWidth() / 5, getHeight() / 5, paintBackGroundVisited);

                }
            }
        }
    }

    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBorder);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintBorder);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintBorder);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintBorder);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintBorder);

        // two vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintBorder);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintBorder);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintBorder);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintBorder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int)event.getX()) / (getWidth() / 5);
            int tY = ((int)event.getY()) / (getHeight() / 5);

            if (tX < 5 && tY < 5) {

                if (!DonutSweeperBoard.getInstance().isFlagging) { // in reveal mode
                    if (DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden &&
                            !DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isSprout) {
                        DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden = false; // display a number
                    } else if (DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden &&
                            DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isSprout){ // uncover a sprout, you lose
                        DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden = false;
                        DonutSweeperBoard.getInstance().uncoverAll();
                        ((MainActivity)this.getContext()).showMessage(getContext().getString(R.string.hitSproutString));
                    }
                } else {
                    if (DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden &&
                            DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isSprout) { // flag a sprout
                        DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isDonut = true;
                        DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden = false;
                        if (DonutSweeperBoard.getInstance().totalFlags() ==
                                DonutSweeperBoard.getInstance().totalSprouts()) {
                            DonutSweeperBoard.getInstance().uncoverAll();
                            ((MainActivity)this.getContext()).showMessage(getContext().getString(R.string.winningString));
                        }
                    } else if (DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isHidden &&
                            !DonutSweeperBoard.getInstance().getFieldContent(tX, tY).isSprout) { // flag a non-sprout
                        DonutSweeperBoard.getInstance().uncoverAll();
                        ((MainActivity)this.getContext()).showMessage(getContext().getString(R.string.falseFlagString));
                    }
                }
                invalidate();
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        DonutSweeperBoard.getInstance().resetModel();
        invalidate();
    }

}