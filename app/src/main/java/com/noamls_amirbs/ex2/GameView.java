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
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class GameView extends View
{
    private float ballcx = 0, ballcy = 0;
    Boolean leftMovePaddle = false,rightMovePaddle = false,stop = false,screenTouch = false, canMove = false,ballInposition = true;
    Boolean hitRightCeiling = false, hitLeftCelling = false, hitDownLeftWall = false, hitUpLeftWall = false;
    Boolean  hitDownRightWall = false,hitUpRightWall = false,startGame = true;
    final int ROW = 5,COL = 7;
    float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    private Paint pen;
    protected float canvasW, canvasH;
    Paddle paddle = null;
    BrickCollection br = null;
    Ball ball = null;
    float xUp = 0,xDown = 0,screenX,screenY;
    final float paddleSpeed = (float) 3.5,ballSpeed = (float) 2.5;
    int plusX = 0, plusY = 0;


    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        pen = new Paint();
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(2);
        pen.setTextSize(50);


    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        pen.setColor(Color.BLUE);
        pen.setTextSize(80);
        canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);
        if(screenTouch)
        {
            canMove = true;
            pen.setColor(Color.GRAY);
            canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);
        }
        br = new BrickCollection(canvasW,canvasH);
        paddle = new Paddle(canvasW,canvasH);
        ball = new Ball(canvasW,canvasH);

        pen.setColor(Color.GREEN);

        pen.setTextSize(50);
        canvas.drawText("Score: 0",50,100,pen);
        canvas.drawText("Lives: 0",canvasW - 200,100,pen);



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
                Log.v("cordinate","rightDownCornerY:=========== "+rightDownCornerY);

                canvas.drawRect(leftUpCornerX , leftUpCornerY , rightDownCornerX ,rightDownCornerY , pen);
            }
        }

        pen.setColor(Color.BLUE);//draw Paddle
        canvas.drawRect(paddle.leftUpCornerX + xUp,paddle.leftUpCornerY,paddle.rightDownCornerX + xDown,paddle.rightDownCornerY,pen);

        if(ballInposition)
        {
            pen.setColor(Color.WHITE);// draw ball
            canvas.drawCircle(ball.x,ball.y,ball.radius,pen);

        }

        // ================== make the ball move ========================================================//


        if(canMove )// the player click to start the game
        {
            ballInposition = false;
            int sign = -1;

                plusX += 5;
                plusY -= 5;


            ball.setY(ball.getY()+plusY);
            if(ball.getY() < 0 )
            {
                hitLeftCelling = true;


             //   String str = Float.toString(ball.getX());
                Toast.makeText(getContext(),"00000000000000000000",Toast.LENGTH_SHORT).show();

            }

            if(hitLeftCelling)
            {
                plusY = -plusY;
                ball.setY(ball.getY()+plusY);
                pen.setColor(Color.WHITE);// draw ball

                canvas.drawCircle(ball.x,ball.y,ball.radius,pen);
            }
             else
            {
                pen.setColor(Color.WHITE);// draw ball
                Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
                canvas.drawCircle(ball.x,ball.y,ball.radius,pen);
            }


        }

        // ===================   make the paddle move by the motion sensor  ============================//
        if(leftMovePaddle && paddle.leftUpCornerX + xDown > 0)
        {
            if(!stop && canMove)
            {
                xUp -= paddleSpeed;
                xDown -= paddleSpeed;
            }

        }
        else if(rightMovePaddle && paddle.rightDownCornerX + xUp < canvasW)
        {
            if(!stop && canMove)
            {
                xUp += paddleSpeed;
                xDown += paddleSpeed;
            }

        }

// ==================================================================================================//
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
            stop = true;
        }

        invalidate(); // call onDraw()
    }
    public void moveBall()
    {
        Random rnd = new Random();
        ballcx = (float)rnd.nextInt(5);
        ballcy = ballcx;
        invalidate(); // call onDraw()
    }
    public boolean onTouchEvent(MotionEvent event)
    {
        screenTouch = true;
        screenX = event.getX();
        screenY = event.getY();
        return true;
    }

}
