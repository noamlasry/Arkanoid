package com.noamls_amirbs.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.AttributedCharacterIterator;

public class BrickCollection
{

    float canW,canH;
    final int ROW = 5,COL = 7,INTEVAL = 3;
    float startPositionX = 0,startPositionY = 200,endPositionX,endPositionY = 240;
    float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    float SIZE_X, SIZE_Y = 40;
    private Paint pen;

    Brick bricks[][] = null;


    public BrickCollection(float canW, float canH)
    {

        this.canW = canW;
        this.canH = canH;
        pen = new Paint();

        endPositionX = canW/7; // to set the bricks board suitable for any type of device.
        SIZE_X = canW/7;

        bricks = new Brick[ROW][COL];
        for(int i = 0; i<ROW; i++)
        {
            for(int j = 0; j<COL; j++)
            {
                bricks[i][j] = new Brick();

                bricks[i][j] = new Brick();
                float a = bricks[i][j].leftUpCornerY;

                bricks[i][j].setLeftUpCornerX(startPositionX );
                bricks[i][j].setLeftUpCornerY(startPositionY );

                bricks[i][j].setRightDownCornerX(endPositionX);
                bricks[i][j].setRightDownCornerY(endPositionY);


                startPositionX += (SIZE_X + INTEVAL);
                endPositionX += (SIZE_X + INTEVAL);

            }
            startPositionX = 0;
            endPositionX = SIZE_X;
            startPositionY += (SIZE_Y + INTEVAL);
            endPositionY += (SIZE_Y + INTEVAL);
        }
    }

}