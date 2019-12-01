package com.noamls_amirbs.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View
{

    Context context;
    AttributeSet attrs;
    Boolean leftMovePaddle = false,rightMovePaddle = false,stop = false;
    final int ROW = 5,COL = 7;
    float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    private Paint pen;
    protected int canvasW, canvasH;
    Paddle paddle = null;
    BrickCollection br = null;
    Ball ball = null;
    float xUp = 0,xDown = 0;
    final float paddleSpeed = (float) 3.5;


    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        pen = new Paint();
        pen.setColor(Color.GREEN);
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(2);
        pen.setTextSize(50);


    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        br = new BrickCollection(canvasW,canvasH);
        paddle = new Paddle(canvasW,canvasH);
        ball = new Ball(canvasW,canvasH);


        canvas.drawText("Score: 0",50,100,pen);
        canvas.drawText("Lives: 0",canvasW - 200,100,pen);

        Log.d("width","width/brick"+canvasW/256);

        pen.setColor(Color.GREEN);
        for(int i = 0; i<ROW; i++)//draw bricks
        {
            for(int j = 0; j<COL; j++)
            {
                leftUpCornerX = br.bricks[i][j].leftUpCornerX;
                leftUpCornerY = br.bricks[i][j].leftUpCornerY;
                rightDownCornerX = br.bricks[i][j].rightDownCornerX;
                rightDownCornerY = br.bricks[i][j].rightDownCornerY;
                Log.v("cordinate","leftUpCornerX: "+leftUpCornerX);
                Log.v("cordinate","leftUpCornerY: "+leftUpCornerY);
                Log.v("cordinate","rightDownCornerX: "+rightDownCornerX);
                Log.v("cordinate","rightDownCornerY: "+rightDownCornerY);

                canvas.drawRect(leftUpCornerX , leftUpCornerY , rightDownCornerX ,rightDownCornerY , pen);
            }
        }

        pen.setColor(Color.BLUE);//draw Paddle
        canvas.drawRect(paddle.leftUpCornerX + xUp,paddle.leftUpCornerY,paddle.rightDownCornerX + xDown,paddle.rightDownCornerY,pen);

        pen.setColor(Color.WHITE);// draw ball
        canvas.drawCircle(ball.x,ball.y,ball.radius,pen);



        float paddleSize = paddle.getRightDownCornerX() - paddle.getLeftUpCornerX();
        if(leftMovePaddle && paddle.rightDownCornerX + xDown < canvasW)
        {
            if(!stop)
            {
                xUp += paddleSpeed;
                xDown += paddleSpeed;
            }

        }
        else if(rightMovePaddle && paddle.leftUpCornerX + xUp > 0)
        {
            if(!stop)
            {
                xUp -= paddleSpeed;
                xDown -= paddleSpeed;
            }

        }

        invalidate();

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasW = w;
        canvasH = h;


    }
    public void movePaddle(int val)
    {
        if(val == 0)
        {
            stop = false;
            rightMovePaddle = false;
            leftMovePaddle = true;
        }

        else if(val == 1)
        {
            stop = false;
            leftMovePaddle = false;
            rightMovePaddle = true;
        }
        else if(val == 2)
        {
            Log.d("t","stop!");
            //leftMovePaddle = false;
           // rightMovePaddle = false;
            stop = true;
        }

        invalidate(); // call onDraw()
    }

}
