package com.noamls_amirbs.ex2;

public class Ball
{
    protected float x,y,radius;

    public Ball(float x, float y, float radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setRadius(float r) { this.radius = radius; }

    public float getX() { return this.x; }

    public float getY() { return this.y; }

    public float getRadius() { return this.radius; }
}
