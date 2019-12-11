package com.noamls_amirbs.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ball
{
    float x,y,radius,canW,canH,ballFirstPositionX,ballFirstPositionY;
    int plusX = 0, plusY = 0;
    Paint pen = null;

    Boolean hitRightCeiling = false, hitLeftCelling = false, hitDownLeftWall = false, hitUpLeftWall = false;
    Boolean  hitDownRightWall = false,hitUpRightWall = false,startGame = true;

    public Ball(float canW, float canH)
    {
        this.canW = canW;
        this.canH = canH;

        this.ballFirstPositionX = canW/2;
        this.ballFirstPositionY = canH-95;
        this.x = canW/2;
        this.y = canH - 95;
        this.radius = 15;

        pen = new Paint();
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setRadius(float r) { this.radius = radius; }

    public float getX() { return this.x; }

    public float getY() { return this.y; }

    public float getRadius() { return this.radius; }

    public void drawBall(Canvas canvas,boolean ballInposition)
    {
        if(ballInposition)
        {
            pen.setColor(Color.WHITE);// draw ball
            canvas.drawCircle(x,y,radius,pen);
        }

    }

}
