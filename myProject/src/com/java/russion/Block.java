package com.java.russion;

import java.awt.Graphics;
import java.awt.Image;

class Block implements Runnable// 方块类
{
   static final int type = 7, state = 4;

    static final int[][] patten = { // 16进制代表每种方块
    { 0x0f00, 0x4444, 0x0f00, 0x4444 },// 长条
            { 0x6600, 0x6600, 0x6600, 0x6600 },// 正方块           
            { 0x04e0, 0x0464, 0x00e4, 0x04c4 },// 三角
            { 0x08e0, 0x0644, 0x00e2, 0x044c },// 弯折一下，1、3，1左
            { 0x02e0, 0x0446, 0x00e8, 0x0c44 },// 弯折一下，1、3，1右
            { 0x0462, 0x006c, 0x0462, 0x006c },// 弯折两下，1、2、1，1左上；1右下
           { 0x0264, 0x00c6, 0x0264, 0x00c6 } // 弯折两下，1、2、1，1右上；1左下
    };

    private int blockType = -1; // 方块类型，7种，大小范围0-6

    private int blockState;// 方块状态，4种，大小范围0-3

    private int row, col; // 方块所在的行数，列数

    private int oldRow, oldCol; // 记录方块变化前所在的行数，列数

    private int oldType = -1, oldState; // 记录方块变化前的类型和状态

    private int isfall = 1; // 标记若画，画成什么颜色的，

    // 1表示可以下落，画为红色；0表示不可下落，画为蓝色

   private boolean end = false;// 结束标记，为true时表示结束

    LeftShowCanvas lsc;

    public Block(LeftShowCanvas lsc)
    {        this.lsc = lsc;
       row = 0;
       col = 3;
       oldRow = row;       
       oldCol = col;
    }

    public void reInit() // 一个方块无法下落后，重新初始化
    {
        blockType = -1;
        isfall = 1;
    }

      public void reInitRowCol() // 初始化方块起始点
    {
        row = 0;
        col = 3;    }

    public void run() // 下落线程
    {
        lsc.requestFocusInWindow(); // 获得焦点
        while (!end)
        {
            int blocktype = (int) (Math.random() * 100) % 7;
            drawBlock(blocktype);
           do
            {
                try
                {
                    Thread.sleep(500); // 控制下落速度
                } catch (InterruptedException e)
                {

                }
            } while (fallMove()); // 下落
            for (int j = 0; j < lsc.maxcols; j++)
                // 判断是否结束
                if (lsc.unitState[3][j] == 2)
                    end = true;
        }
   }
    
    public synchronized void drawBlock(int blockType) // 画方块
       {
		if (this.blockType != blockType)
           blockState = (int) (Math.random() * 100) % 4; // 状态
           this.blockType = blockType; // 样式
           if (!isMove(3)) // 判断是否能画
            {
                this.blockType = oldType;
                this.blockState = oldState;
                return;
            }
            int comIndex = 0x8000;
            if (this.oldType != -1)
            {
                for (int i = oldRow; i < oldRow + 4; i++)
                    for (int j = oldCol; j < oldCol + 4; j++)
                    {
                       if ((patten[oldType][oldState] & comIndex) != 0
                               && lsc.unitState[i][j] == 1)
                           //lsc.drawUnit(i, j, 0); // 先还原
                           lsc.unitState[i][j]=0;//将状态记录改变，用于画下张图
                      comIndex = comIndex >> 1;
                   }
           }
           comIndex = 0x8000;
           for (int i = row; i < row + 4; i++)
               for (int j = col; j < col + 4; j++)
               {
                   if ((patten[blockType][blockState] & comIndex) != 0)
                   {
                       if (isfall == 1)
                           //lsc.drawUnit(i, j, 1); // 再画，画为RED
                           lsc.unitState[i][j]=1; //将状态记录改变
                       else if (isfall == 0)
                       {
                           //lsc.drawUnit(i, j, 2); // 无法下落，画为BLUE
                           lsc.unitState[i][j]=2;//将状态记录改变，用于画下张图
                           lsc.deleteFullLine(i); // 判断此行是否可以消
                       }
                   }
                   comIndex = comIndex >> 1;
               }
          
           Image image; //创建缓冲图片,利用双缓冲消除闪烁，画的下个状态图
           image=lsc.createImage(lsc.getWidth(),lsc.getHeight());
           Graphics g=image.getGraphics();
           lsc.paint(g);
           g.drawImage(image, 0, 0, lsc);
               
           if (isfall == 0) // 无法下落，先判断是否能消行，再重新初始化
           {
               // lsc.deleteFullLine(row,col);
               reInit();
               reInitRowCol();
           }
           oldRow = row;
          oldCol = col;
           oldType = blockType;
           oldState = blockState;
       }
   
       public void leftTurn() // 旋转，左转
       {
           if (this.blockType != -1)
           {
               blockState = (blockState + 1) % 4;
               if (isMove(3))
                   drawBlock(blockType);
               else
                 blockState = (blockState + 3) % 4;
           }
       }
   
       public void leftMove() // 左移
       {
           if (this.blockType != -1 && isMove(0))
           {
               col -= 1;
               drawBlock(blockType);
           }
       }
   
       public void rightMove() // 右移
       {
           if (this.blockType != -1 && isMove(1))
           {
               col += 1;
               drawBlock(blockType);
           }
    }
   
     public boolean fallMove() // 下移
       {
           if (this.blockType != -1)
           {
              if (isMove(2))
            {
                  row += 1;
                   drawBlock(blockType);
                   return true;
               } else
               {
                   isfall = 0;
                  drawBlock(blockType);
                   return false;
               }
           }
           return false;
      }
   
       public synchronized boolean isMove(int tag) // 左 0 ，右 1 ，下 2 ,旋转 3
       {
           int comIndex = 0x8000;
           for (int i = row; i < row + 4; i++)
               for (int j = col; j < col + 4; j++)
               {
                   if ((patten[blockType][blockState] & comIndex) != 0)
                   {
                       if (tag == 0 && (j == 0 || lsc.unitState[i][j - 1] == 2))// 是否能左移
                           return false;
                       else if (tag == 1 && // 是否能右移
                               (j == lsc.maxcols - 1 || lsc.unitState[i][j + 1] == 2))
                          return false;
                       else if (tag == 2 && // 是否能下移
                               (i == lsc.maxrows - 1 || lsc.unitState[i + 1][j] == 2))
                           return false;
                       else if (tag == 3 && // 是否能旋转
                               (i > lsc.maxrows - 1 || j < 0
                                       || j > lsc.maxcols - 1 || lsc.unitState[i][j] == 2))
                           return false;
                   }
                   comIndex = comIndex >> 1;
               }
           return true;
       }
   
}