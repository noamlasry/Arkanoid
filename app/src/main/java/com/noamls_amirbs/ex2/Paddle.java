package com.noamls_amirbs.ex2;

public class Paddle
{
    protected float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    float canW,canH;

    public Paddle(float canW, float canH)
    {
        this.canW = canW;
        this.canH = canH;

        this.leftUpCornerX = canW/2 - 150;
        this.leftUpCornerY = canH - 80;
        this.rightDownCornerX = canW/2 + 150;
        this.rightDownCornerY = canH - 60;
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
