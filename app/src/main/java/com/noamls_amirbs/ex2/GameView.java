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
    boolean leftMovePaddle = false,rightMovePaddle = false,stop = false,screenTouch = false, canMove = false,ballInposition = true;
    Boolean hitRightCeiling = false, hitLeftCelling = false, hitDownLeftWall = false, hitUpLeftWall = false;
    Boolean  hitDownRightWall = false,hitUpRightWall = false,startGame = true;
    final int ROW = 5,COL = 7;

    private Paint pen;
    protected float canvasW, canvasH;
    Paddle paddle = null;
    BrickCollection bricks = null;
    Ball ball = null;
    float xUp = 0,xDown = 0,screenX,screenY;
    final float paddleSpeed = (float) 20,ballSpeed = (float) 2.5;
    int plusX = 0, plusY = 0;
    int count = 1;


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
      ball.bouncingBall(canvas,canMove,ballInposition);

    if(canMove )// the player click to start the game
        {


            count++;
            ballInposition = false;
            int sign = -1;

            plusX += 5;
            plusY -= 5;

            ball.setY(ball.getY()+plusY);
            pen.setColor(Color.WHITE);// draw ball
            canvas.drawCircle(ball.x,ball.y,ball.radius,pen);



        }

        // ===================   make the paddle move by the motion sensor  ============================//

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
