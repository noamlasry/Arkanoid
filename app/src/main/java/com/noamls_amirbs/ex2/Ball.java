package com.noamls_amirbs.ex2;

public class Ball
{
    protected float x,y,radius;
    float canW,canH;

    public Ball(float canW, float canH)
    {
        this.canW = canW;
        this.canH = canH;

        this.x = canW/2;
        this.y = canH - 95;
        this.radius = 15;
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setRadius(float r) { this.radius = radius; }

    public float getX() { return this.x; }

    public float getY() { return this.y; }

    public float getRadius() { return this.radius; }
}
