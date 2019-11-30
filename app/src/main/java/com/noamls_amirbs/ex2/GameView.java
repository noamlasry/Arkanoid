package com.noamls_amirbs.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View
{
    final int ROW = 4,COL = 7;
    float leftUpCornerX,leftUpCornerY,rightDownCornerX,rightDownCornerY;
    private Paint pen;
    protected int canvasW, canvasH;
   // Brick bricks[][] = new Brick[ROW][COL];
    BrickCollection br = null;

    public GameView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        pen = new Paint();
        pen.setColor(Color.GREEN);
        pen.setStyle(Paint.Style.FILL);
        pen.setStrokeWidth(2);
        pen.setTextSize(50);

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        canvas.drawText("Score: 0",50,100,pen);
        canvas.drawText("Lives: 0",canvasW - 200,100,pen);

        br = new BrickCollection();


        for(int i = 0; i<ROW; i++)
        {
            for(int j = 0; j<COL; j++)
            {
                leftUpCornerX = br.bricks[i][j].leftUpCornerX;
                leftUpCornerY = br.bricks[i][j].leftUpCornerY;
                rightDownCornerX = br.bricks[i][j].rightDownCornerX;
                rightDownCornerY = br.bricks[i][j].rightDownCornerY;
                Log.v("cordinate","leftUpCornerX: "+leftUpCornerX);
                Log.v("cordinate","leftUpCornerY: "+leftUpCornerY);
                Log.v("cordinate","rightDownCornerX: "+rightDownCornerX);
                Log.v("cordinate","rightDownCornerY: "+rightDownCornerY);

                canvas.drawRect(leftUpCornerX , leftUpCornerY , rightDownCornerX ,rightDownCornerY , pen);
            }
        }





    }

    public void drawBricks()
    {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasW = w;
        canvasH = h;


    }

}
