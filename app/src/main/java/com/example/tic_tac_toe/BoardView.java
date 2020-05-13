package com.example.tic_tac_toe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BoardView extends  View{
    private static final int LINE_THICK = 5;
    private  static final int   ELEMENT_MARGIN = 20, ELT_STROKE_WIDTH = 15;
    int width, height , eltH , eltW ;
    Paint gPaint , oPaint , xPaint ;
     GameEngine gameEngine;
     MainActivity activity ;

    public BoardView(Context context) {
        super(context);
    }
    public  BoardView (Context context , @Nullable AttributeSet attrs){
        super(context,attrs);
        gPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.GREEN);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);

    }
    public  void setMainactivity(MainActivity a){
        activity = a;
    }
    public  void setGameEngine(GameEngine g){
        gameEngine = g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        eltH = (height - LINE_THICK)/3;
        eltW = (width - LINE_THICK)/3;

        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gPaint);
        }
    }
    private void drawBoard(Canvas canvas){
        for (int i = 0 ; i< 3 ; i++){
            for (int j= 0 ; j< 3 ; j++){

                drawElt(canvas ,gameEngine.getElement(i,j),i , j);
            }
        }

    }

    private void drawElt(Canvas canvas, char c, int x, int y){
        if (c == 'O'){
            float cx = ( eltW * x)+eltW/2;
            float cy = ( eltH * y)+ eltH/2;
            canvas.drawCircle(cx , cy,Math.min(eltH,eltW)/2 - ELEMENT_MARGIN * 2, oPaint);

        }
        else if (c =='X'){
            float startX = (eltW * x) + ELEMENT_MARGIN;
            float startY = (eltH * y )+ ELEMENT_MARGIN;
            float endX = startX + eltW - ELEMENT_MARGIN * 2;
            float endY = startY + eltH - ELEMENT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - ELEMENT_MARGIN;
            float startY2 = (eltH * y) + ELEMENT_MARGIN;
            float endX2 = startX2 - eltW + ELEMENT_MARGIN * 2;
            float endY2 = startY2 + eltH - ELEMENT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine.isEnded()  &&  event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / eltW);
            int y = (int) (event.getY() / eltH);
            char win = gameEngine.play(x, y);
            invalidate();

            if (win != ' ') {
                activity.gameEnded(win);
            } else {
                // computer plays ...
                win = gameEngine.computerPlay();
                invalidate();

                if (win != ' ') {
                    activity.gameEnded(win);
                }
            }
        }

        return super.onTouchEvent(event);
    }
}


