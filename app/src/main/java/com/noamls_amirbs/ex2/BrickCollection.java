package com.noamls_amirbs.ex2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.AttributedCharacterIterator;

public class BrickCollection
{
    final int ROW = 4,COL = 7,INTEVAL = 3;
    float startPositionX = 0,startPositionY = 200,endPositionX = 256,endPositionY = 240;
    final int SIZE_X = 256, SIZE_Y = 40;

    Brick bricks[][] = null;



    public BrickCollection()
    {
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
            endPositionX = 256;
            startPositionY += (SIZE_Y + INTEVAL);
            endPositionY += (SIZE_Y + INTEVAL);

        }

    }


    public void setBricks()
    {
        for(int i = 0; i<ROW; i++)
        {
            for(int j = 0; j<COL; j++)
            {

            }

            startPositionY += (endPositionY + INTEVAL);
            endPositionY = endPositionY * (i+1) + INTEVAL;
        }


    }



}
