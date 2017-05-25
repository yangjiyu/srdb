package com.java.russion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

class LeftShowCanvas extends Canvas{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int maxrows, maxcols; // 画布最大行数，列数

    int unitSize; // 单元格的大小，小正方格

    int[][] unitState; // 每个小方格的状态 0、1、2表示

    RightPanel rp;

    int score;

    public LeftShowCanvas(RightPanel rp)
    {
        this.rp = rp;
        score = Integer.valueOf(rp.jtf.getText());
        maxrows = 20;
        maxcols = 10;
        unitSize = 20;
        unitState = new int[maxrows][maxcols];
        initCanvas();
    }

    public void initCanvas() // 初始化，画布方格
   {
       for (int i = 0; i < maxrows; i++)
            for (int j = 0; j < maxcols; j++)
                unitState[i][j] = 0;
    }

    public void paint(Graphics g)
    {
        for (int i = 0; i < maxrows; i++)
        {
            for (int j = 0; j < maxcols; j++)
                drawUnit(i, j, unitState[i][j]); // 画方格
            if (i == 3)
            	{   
            	g.setColor(Color.RED);
                g.drawLine(0, (i + 1) * (unitSize + 1) - 1, maxcols
                        * (unitSize + 1) - 1, (i + 1) * (unitSize + 1) - 1);
            }
       }
   }

    public void drawUnit(int row, int col, int tag) // 画方格
    {
        unitState[row][col] = tag; // 记录状态        Graphics g = getGraphics();
        Graphics g = getGraphics();
       switch (tag)
       {
        case 0: // 初始黑色
            g.setColor(Color.BLACK);
            break;
        case 1: // 方格黑色
            g.setColor(Color.RED);
            break;
        case 2:
            g.setColor(Color.BLUE);
            break;
        }
        g.fillRect(col * (unitSize + 1), row * (unitSize + 1), unitSize,
                unitSize);
    }

    public void deleteFullLine(int row) // 判断此行是否可以消，同时可消就消行
    {
         for (int j = 0; j < maxcols; j++)
            if (unitState[row][j] != 2)
                return;

        for (int i = row; i > 3; i--)
            // 到此即为可消，将上面的移下消此行
            for (int j = 0; j < maxcols; j++)                //drawUnit(i, j, unitState[i - 1][j]);
            unitState[i][j]=unitState[i-1][j];//将状态记录改变，用于画下张图
        score++;
        rp.jtf.setText(String.valueOf(score));
    }
}

