package com.noamls_amirbs.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.Random;

public class GameView extends View
{
    float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    boolean leftMovePaddle = false,rightMovePaddle = false,stop = false,screenTouch = false, canMove = false;
    final int ROW = 5,COL = 7;
    private Paint pen;
    protected float canvasW, canvasH;
    Paddle paddle = null;
    BrickCollection bricks = null;
    Ball ball = null;
    Brick brick = null;
    float xUp = 0,xDown = 0;
    float paddleSpeed = (float) 13,ballSpeed = (float) 6;
    int plusX = 0, plusY = 0,signX = -1,lives = 3,score = 0,signY = -1,x,SIGN;
    private Handler handler;
    boolean boolArray[][] = new boolean[ROW][COL];
    MainActivity mainActivity  = new MainActivity();

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        pen = new Paint();
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(2);
        pen.setTextSize(50);
        handler = new Handler();
        randomDirectionBall();
        randomSpeedBall();
    }

    protected void onDraw( Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        if(lives == 0)// the player lost the game
        {
            Toast.makeText(getContext(), "you lost!!",Toast.LENGTH_LONG).show();
            initGame(canvas);
        }
        pen.setColor(Color.BLUE);
        pen.setTextSize(80);


// ========== create the necessary objects ============//
        bricks = new BrickCollection(canvasW,canvasH);
        paddle = new Paddle(canvasW,canvasH);
        ball = new Ball(canvasW,canvasH);
        brick = new Brick();
// =================================================//

        canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);// first draw on the canvas
        if(screenTouch)// once the player touch the screen the game will began.
        {
            canMove = true;
            pen.setColor(Color.GRAY);
            canvas.drawText("Click to Play!",canvasW /2-200,canvasH/2+70,pen);

        }

// ========= draw the bricks  ==================== //
        drawBricks(canvas);
// ===============================================//
// ======== draw the ball =======================//

        ball.setX(canvasW/2);
        ball.setY(canvasH-90);
        pen.setColor(Color.WHITE);
        canvas.drawCircle(paddle.leftUpCornerX+paddle.paddleSize/2,canvasH-95,15,pen);

// =============================================//
// ======== draw the paddle and active according the sensor ==================================================================================//
        pen.setColor(Color.BLUE);//draw Paddle
        canvas.drawRect(paddle.leftUpCornerX + xUp,paddle.leftUpCornerY,paddle.rightDownCornerX + xDown,paddle.rightDownCornerY,pen);
// =============================================================================================================================================//// ================== make the ball move ========================================================//

        if(canMove )// the player click to start the game
        {

            pen.setColor(Color.GRAY);
            canvas.drawCircle(paddle.leftUpCornerX+paddle.paddleSize/2,canvasH-95,15,pen);

            movePaddle();
            moveBall(canvas);

            if(breakAllBrick())// finish the game , win!
            {
                Toast.makeText(getContext(), "you win!!",Toast.LENGTH_LONG).show();
                initGame(canvas);
            }


            if(hitTheBrick(ball.getX(),ball.getY(),canvas))
            {
                signY *= -1;
                score +=5*lives;
                mainActivity.activeMusic();
                canvas.drawText("Score: "+score,50,100,pen);
            }
            //===== hit the celling ========//
            if(ball.getY() == 120.0)
                signY = 1;

            // ========== hit the left side of the paddle ========== //
            if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp && ball.getX() < paddle.rightDownCornerX+xDown - ((paddle.paddleSize/2) - 50) )
            {
                signY = -1;
                signX = -1;
            }
            // ========== hit the right side of the paddle ========== //
            if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp+(paddle.paddleSize/2) +50 && ball.getX() < paddle.rightDownCornerX+xDown)
            {
                signY = -1;
                signX = 1;
            }
            // ========== hit the middle side of the paddle ========== //
            if(ball.getY() == paddle.getLeftUpCornerY() && ball.getX() > paddle.getLeftUpCornerX() +xUp+paddle.paddleSize -50 && ball.getX() < paddle.rightDownCornerX+xDown -paddle.paddleSize +50)
                signY = -1;
            //======= hit the right wall ==============//
            if(ball.getX() < 3.0)
                signX *= -1;

            //======= hit the right wall ==============//
            if(ball.getX() > canvasW - 3)
                signX *= -1;

            //======== the ball miss the paddle ===============//
            if((ball.getX() < paddle.getLeftUpCornerX() || ball.getX() > paddle.getRightDownCornerX() )&& ball.getY() > paddle.getRightDownCornerY() +3 )
                lostLives(canvas);

        }
        // ===================   make the paddle move by the motion sensor  ============================//
// ==================================================================================================//

        invalidate();
    }
    public void lostLives(Canvas canvas)
    {
        randomDirectionBall();
        randomSpeedBall();
        canMove = false;
        screenTouch = false;
        plusX =  plusY = 0;
        xUp = xDown = 0;
        lives--;
        canvas.drawText("Lives: "+lives,canvasW - 200,100,pen);
    }

    public void moveBall(Canvas canvas)
    {
        pen.setColor(Color.WHITE);// draw ball
        plusX += 5*signX;
        plusY += 5*signY;

        ball.setY(ball.getY()+plusY);
        ball.setX(ball.getX()+plusX);

        canvas.drawCircle(ball.getX(),ball.getY(),ball.getRadius(),pen);

    }
    @Override

    public boolean onTouchEvent(MotionEvent event)
    {
        screenTouch = true;
        return true;
    }

    public void drawBricks(Canvas canvas)
    {
        pen.setColor(Color.GREEN);

        pen.setTextSize(50);
        canvas.drawText("Score: "+score,50,100,pen);
        canvas.drawText("Lives: "+lives,canvasW - 200,100,pen);

        for(int i = 0; i<ROW; i++)//draw bricks
        {
            for(int j = 0; j<COL; j++)
            {
                leftUpCornerX = bricks.bricks[i][j].leftUpCornerX;
                leftUpCornerY = bricks.bricks[i][j].leftUpCornerY;
                rightDownCornerX = bricks.bricks[i][j].rightDownCornerX;
                rightDownCornerY = bricks.bricks[i][j].rightDownCornerY;
                if(boolArray[i][j] == true)
                    pen.setColor(Color.GRAY);
                else
                    pen.setColor(Color.GREEN);
                canvas.drawRect(leftUpCornerX , leftUpCornerY , rightDownCornerX ,rightDownCornerY , pen);
            }
        }

    }
    public boolean hitTheBrick(float x , float y, Canvas canvas )
    {

        for(int i = 0; i<ROW; i++)//draw bricks
        {
            for(int j = 0; j<COL; j++)
            {
                leftUpCornerX = bricks.bricks[i][j].leftUpCornerX;
                leftUpCornerY = bricks.bricks[i][j].leftUpCornerY;
                rightDownCornerX = bricks.bricks[i][j].rightDownCornerX;
                rightDownCornerY = bricks.bricks[i][j].rightDownCornerY;

                if(x > leftUpCornerX && x < rightDownCornerX && y > leftUpCornerY && y < rightDownCornerY && boolArray[i][j] == false)
                {
                    Log.v("hit","hit brick i:"+i+"j: "+j);

                    boolArray[i][j] = true;
                    pen.setColor(Color.GRAY);
                    canvas.drawRect(leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY,pen);
                    return true;

                }

            }
        }

        return false;

    }
    //======== set the width and the height of the canvas ==============//
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasW = w;
        canvasH = h;
    }
    // ======= moving the paddle by the sensor =========///
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
    //======================================================

    public void randomSpeedBall()
    {
        int min = 8,max = 13;
        Random rand = new Random();
        ballSpeed = rand.nextInt((max - min) + 1) + min;
    }
    public void randomDirectionBall()
    {
        Random rand = new Random();
        SIGN = rand.nextInt(3);
        if(SIGN == 0)
            signX = 1;
        else if(SIGN == 1)
            signX = 1;
        else if(SIGN == 2)
            signX = -1;
    }
    public boolean breakAllBrick()
    {
        for(int i = 0; i<ROW; i++)//draw bricks
        {
            for(int j = 0; j<COL; j++)
            {
                if(boolArray[i][j] == false)
                    return false;
            }
        }
        return true;
    }
    public void initGame(Canvas canvas)
    {
        canMove = false;
        screenTouch = false;
        plusX = 0;
        plusY = 0;
        score = lives = 0;
        lives = 3;

        pen.setColor(Color.GREEN);
        pen.setTextSize(50);
        canvas.drawText("Score: "+score,50,100,pen);
        canvas.drawText("Lives: "+lives,canvasW - 200,100,pen);

        for(int i = 0; i<ROW; i++)//draw bricks
        {
            for(int j = 0; j<COL; j++)
            {
                boolArray[i][j] = false;// set back the array to false to daw back the bricks in Green

                leftUpCornerX = bricks.bricks[i][j].leftUpCornerX;
                leftUpCornerY = bricks.bricks[i][j].leftUpCornerY;
                rightDownCornerX = bricks.bricks[i][j].rightDownCornerX;
                rightDownCornerY = bricks.bricks[i][j].rightDownCornerY;
                canvas.drawRect(leftUpCornerX , leftUpCornerY , rightDownCornerX ,rightDownCornerY , pen);
            }
        }
    }
}