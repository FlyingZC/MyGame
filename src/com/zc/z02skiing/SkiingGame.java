package com.zc.z02skiing;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

@SuppressWarnings({"serial", "unused"})
public class SkiingGame extends JFrame implements MouseListener
{

    /**
     * 存储所有绘制的线 
     */
    ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();

    /**
     * 记录线段的起始坐标
     */
    Point2D.Double holder;

    public SkiingGame()
    {
        super("Screen Skier");
        setSize(700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        //设置 所有 待绘制的线段 为黑色
        g.setColor(Color.black);

        //绘制 所有线段
        for (int i = 0; i < lines.size(); i++)
        {
            Line2D.Double temp = lines.get(i);
            int x1 = Integer.parseInt("" + Math.round(temp.getX1()));
            int y1 = Integer.parseInt("" + Math.round(temp.getY1()));
            int x2 = Integer.parseInt("" + Math.round(temp.getX2()));
            int y2 = Integer.parseInt("" + Math.round(temp.getY2()));
            //绘制线段
            g.drawLine(x1, y1, x2, y2);
        }
    }

    //该方法在 鼠标点击时调用
    public void mouseClicked(MouseEvent e)
    {
    }

    //鼠标进入游戏窗口时调用
    public void mouseEntered(MouseEvent e)
    {
    }

    //鼠标离开游戏窗口时调用
    public void mouseExited(MouseEvent e)
    {
    }

    //鼠标按下时调用
    public void mousePressed(MouseEvent e)
    {
        //绘制 线段起始位置
        holder = new Point2D.Double(e.getX(), e.getY());
    }

    //该方法在 鼠标释放时调用
    @SuppressWarnings("unchecked")
    public void mouseReleased(MouseEvent e)
    {
        //鼠标释放时 完成线段绘制,并存储到数组中
        Point2D.Double end = new Point2D.Double(e.getX(), e.getY());
        lines.add(new Line2D.Double(holder, end));
        //重新绘制游戏窗口
        repaint();
    }

    public static void main(String[] args)
    {
        new SkiingGame();
    }

}
