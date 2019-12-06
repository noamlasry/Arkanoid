package com.noamls_amirbs.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
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
    boolean leftMovePaddle = false,rightMovePaddle = false,stop = false,screenTouch = false, canMove = false,ballInposition = true;
    Boolean hitRightCeiling = false, hitLeftCelling = false, hitDownLeftWall = false, hitUpLeftWall = false;
    Boolean  hitDownRightWall = false,hitUpWall = false,hitUpRightWall = false,startGame = true;
    final int ROW = 5,COL = 7;

    private Paint pen;
    protected float canvasW, canvasH;
    Paddle paddle = null;
    BrickCollection bricks = null;
    Ball ball = null;
    float xUp = 0,xDown = 0,screenX,screenY,temp;
    final float paddleSpeed = (float) 10,ballSpeed = (float) 2.5;
    int plusX = 0, plusY = 0,x,y,SIGN;
    int signX = -1,signY = -1;
    private Handler handler;
    //=========== give the ball random deriction in the first time ===========================================//



//========================================================================================================//



    public GameView(Context context, @Nullable AttributeSet attrs)
    {

        super(context, attrs);
        pen = new Paint();
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(2);
        pen.setTextSize(50);
        handler = new Handler();

        Random rand = new Random();
        x = rand.nextInt(10);
        y = rand.nextInt(6);
        SIGN = rand.nextInt(3);
        if(SIGN == 0)
            signX = 1;
        else if(SIGN == 1)
            signX = -1;
        else if(SIGN == 2)
           signX = 0;

    }


    @Override
    protected void onDraw(final Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        pen.setColor(Color.BLUE);
        pen.setTextSize(80);

        canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);// first draw on the canvas
        if(screenTouch)// once the player touch the screen the game will began.
        {
            canMove = true;
            pen.setColor(Color.GRAY);
            canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);
        }

// ========== create the necessary objects ============//
        bricks = new BrickCollection(canvasW,canvasH);
        paddle = new Paddle(canvasW,canvasH);
        ball = new Ball(canvasW,canvasH);
        temp = paddle.leftUpCornerX;
// =================================================//
// ========= draw the bricks  ==================== //
        bricks.drawBricks(canvas);
// ===============================================//
// ======== draw the ball =======================//
        ball.drawBall(canvas,ballInposition);
// =============================================//
// ======== draw the paddle and active according the sensor ==================================================================================//
        pen.setColor(Color.BLUE);//draw Paddle
        canvas.drawRect(paddle.leftUpCornerX + xUp,paddle.leftUpCornerY,paddle.rightDownCornerX + xDown,paddle.rightDownCornerY,pen);
// =============================================================================================================================================//// ================== make the ball move ========================================================//
   //   ball.bouncingBall(canvas,canMove,ballInposition);

    if(canMove )// the player click to start the game
        {
                movePaddle();
                moveBall(canvas);

                if(ball.getY() == 200.0)
               {
                    Toast.makeText(getContext(), "hit up",
                            Toast.LENGTH_LONG).show();
                    signY = 1;
                    invalidate();
                }
               // ========== hit the left side of the paddle ========== //
                if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp && ball.getX() < paddle.rightDownCornerX+xDown - ((paddle.paddleSize/2) - 50) )
               {
                    Toast.makeText(getContext(), "hit left side of the paddle",
                            Toast.LENGTH_LONG).show();
                   signY = -1;
                   signX = -1;
                   invalidate();
               }
            // ========== hit the right side of the paddle ========== //
               if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp+(paddle.paddleSize/2) +50 && ball.getX() < paddle.rightDownCornerX+xDown)
               {
                   Toast.makeText(getContext(), "hit right side of the paddle",
                          Toast.LENGTH_LONG).show();
                   signY = -1;
                   signX = 1;
                   invalidate();
               }
            // ========== hit the middle side of the paddle ========== //
                if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp+paddle.paddleSize -50 && ball.getX() < paddle.rightDownCornerX+xDown -paddle.paddleSize +50)
                {
                    Toast.makeText(getContext(), "hit middle paddle",
                          Toast.LENGTH_LONG).show();
                    signY = -1;
                    invalidate();
                }

                if(ball.getX() < 3.0)
                {
                    Toast.makeText(getContext(), "hit left wall",
                            Toast.LENGTH_LONG).show();
                    signX *= -1;
                }
               if(ball.getX() > canvasW - 3)
               {
                  Toast.makeText(getContext(), "hit right wall",
                        Toast.LENGTH_LONG).show();
                  signX *= -1;
               }



        }
        // ===================   make the paddle move by the motion sensor  ============================//
// ==================================================================================================//
        invalidate();
    }

    public void movePaddle()
    {
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
    }
    public void moveBall(Canvas canvas)
    {

        pen.setColor(Color.WHITE);// draw ball
        plusX += 5*signX;
        plusY += 5*signY;

        ball.setY(ball.getY()+plusY);
        ball.setX(ball.getX()+plusX);


        canvas.drawCircle(ball.x,ball.y,ball.radius,pen);
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

    }


    public boolean onTouchEvent(MotionEvent event)
    {

        screenTouch = true;
        screenX = event.getX();
        screenY = event.getY();
        return true;
    }


}
