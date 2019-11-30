package com.noamls_amirbs.ex2;

public class Paddle
{
    protected float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;


    public Paddle(float leftUpCornerX, float leftUpCornerY, float rightDownCornerX, float rightDownCornerY)
    {
        this.leftUpCornerX = leftUpCornerX;
        this.leftUpCornerY = leftUpCornerY;
        this.rightDownCornerX = rightDownCornerX;
        this.rightDownCornerY = rightDownCornerY;
    }

    public void setLeftUpCornerX(float leftUpCornerX){this.leftUpCornerX = leftUpCornerX;}
    public void setLeftUpCornerY(float leftUpCornerY){this.leftUpCornerY = leftUpCornerY;}
    public void setRightDownCornerX(float rightDownCornerX){this.rightDownCornerX = rightDownCornerX;}
    public void setRightDownCornerY(float rightDownCornerY){this.rightDownCornerY = rightDownCornerY;}

    public float getLeftUpCornerX(){return this.leftUpCornerX;}
    public float getLeftUpCornerY(){return this.leftUpCornerY;}
    public float getRightDownCornerX(){return this.rightDownCornerX;}
    public float getRightDownCornerY(){return this.rightDownCornerY;}
}
