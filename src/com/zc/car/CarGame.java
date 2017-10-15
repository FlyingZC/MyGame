package com.zc.car;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class CarGame extends JFrame
{
    private final int WIDTH = 900, HEIGHT = 650;
    // new Rectangle(x, y, width, hight)
    Rectangle left = new Rectangle(0, 0, WIDTH / 9, HEIGHT);

    Rectangle right = new Rectangle((WIDTH / 9) * 9, 0, WIDTH / 9, HEIGHT);

    Rectangle top = new Rectangle(0, 0, WIDTH, HEIGHT / 9);

    Rectangle bottom = new Rectangle(0, (HEIGHT / 9) * 9, (WIDTH / 9) * 10, HEIGHT / 9);

    Rectangle center = new Rectangle((int) ((WIDTH / 9) * 2.5), (int) ((HEIGHT / 9) * 2.5), (int) ((WIDTH / 9) * 5),
            (HEIGHT / 9) * 4);

    // 下 面 的 矩 形 用 于 绘 制 跑 道 上 的 障 碍 物 ,以增加赛车的难度
    Rectangle obstacle1 = new Rectangle(WIDTH / 2, (int) ((HEIGHT / 9) * 7), WIDTH / 10, HEIGHT / 9);

    Rectangle obstacle2 = new Rectangle(WIDTH / 3, (int) ((HEIGHT / 9) * 5), WIDTH / 10, HEIGHT / 4);

    Rectangle obstacle3 = new Rectangle(2 * (WIDTH / 3), (int) ((HEIGHT / 9) * 5), WIDTH / 10, HEIGHT / 4);

    Rectangle obstacle4 = new Rectangle(WIDTH / 3, HEIGHT / 9, WIDTH / 30, HEIGHT / 9);

    Rectangle obstacle5 = new Rectangle(WIDTH / 2, (int) ((HEIGHT / 9) * 1.5), WIDTH / 30, HEIGHT / 4);

    // 下 面 的 矩 形 用 于 绘 制 内 外 跑 道 的 终 点 线
    Rectangle finishLine = new Rectangle(WIDTH / 9, (HEIGHT / 2) - HEIGHT / 9, (int) ((WIDTH / 9) * 1.5), HEIGHT / 70);

    // 下 面 的 矩 形 用 于 绘 制 外 跑 道 的 起 跑 线
    Rectangle outLine = new Rectangle(WIDTH / 9, HEIGHT / 2, (int) ((WIDTH / 9) * 1.5) / 2, HEIGHT / 140);

    // 下 而 的 矩 形 用 于 绘 制 内 跑 道 的 起 跑 线
    Rectangle inLine = new Rectangle(((WIDTH / 9) + ((int) ((WIDTH / 9) * 1.5) / 2)), (HEIGHT / 2) + (HEIGHT / 10),
            (int) ((WIDTH / 9) * 1.5) / 2, HEIGHT / 140);

    //所有障碍物的数组,用于碰撞检测
    Rectangle[] zhangAiWuArray = new Rectangle[] {left, right, top, bottom, center, obstacle1, obstacle2, obstacle3,
            obstacle4, obstacle5};

    // 赛车
    double car1Speed = 0.5, car2Speed = 0.5;

    // 下 面 的 矩 形 代 表 第 一 辆 赛 车
    Rectangle car1 = new Rectangle(WIDTH / 9, HEIGHT / 2, WIDTH / 30, WIDTH / 30);

    // 下 面 的 矩 形 代 表 第 二 辆 赛 车
    Rectangle car2 = new Rectangle(((WIDTH / 9) + ((int) ((WIDTH / 9) * 1.5) / 2)), (HEIGHT / 2) + (HEIGHT / 10),
            WIDTH / 30, WIDTH / 30);

    //设置赛车方向
    private final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    //赛车默认向上行驶
    int car1Direction = UP;

    int car2Direction = UP;

    //构造方法
    public CarGame()
    {
        super("竞速游戏");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 初 始 化 内 部 类 （ 两 辆 赛 车 同 时 行 驶 ， 通 过 线 程 实 现 该 ）
        Movel ml = new Movel();
        Move2 m2 = new Move2();
        ml.start();
        m2.start();
    }

    public static void main(String[] args)
    {
        CarGame g = new CarGame();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        // 绘 制赛 场 背 景
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // 绘 制 时 , 先 将 场 地 设 置 为 绿 色
        g.setColor(Color.GREEN);
        // 现 在 绘 制 各 个 矩 形
        g.fillRect(left.x, left.y, left.width, left.height);
        g.fillRect(right.x, right.y, right.width, right.height);
        g.fillRect(top.x, top.y, top.width, top.height);
        g.fillRect(bottom.x, bottom.y, bottom.width, bottom.height);
        g.fillRect(center.x, center.y, center.width, center.height);
        g.fillRect(obstacle1.x, obstacle1.y, obstacle1.width, obstacle1.height);
        g.fillRect(obstacle2.x, obstacle2.y, obstacle2.width, obstacle2.height);
        g.fillRect(obstacle3.x, obstacle3.y, obstacle3.width, obstacle3.height);
        g.fillRect(obstacle4.x, obstacle4.y, obstacle4.width, obstacle4.height);
        g.fillRect(obstacle5.x, obstacle5.y, obstacle5.width, obstacle5.height);
        // 设置起跑线为白色
        g.setColor(Color.WHITE);
        // 绘制起跑线
        g.fillRect(outLine.x, outLine.y, outLine.width, outLine.height);
        g.fillRect(inLine.x, inLine.y, inLine.width, inLine.height);
        // 设 置 终 点 线 为 黄 色
        g.setColor(Color.YELLOW);
        // 绘制终点线
        g.fillRect(finishLine.x, finishLine.y, finishLine.width, finishLine.height);
        //设 置 赛 车 1 为蓝色
        g.setColor(Color.BLUE);
        //绘 制 赛 车 1
        g.fill3DRect(car1.x, car1.y, car1.width, car1.height, true);
        //设 置 赛 车 2 为红色
        g.setColor(Color.RED);
        //绘 制 赛 车 2
        g.fill3DRect(car2.x, car2.y, car2.width, car2.height, true);
    }

    // 回执跑道
    public void paintPaoDao(Graphics g)
    {

    }

    private class Movel extends Thread implements KeyListener
    {
        public void run()
        {
            //!注册键盘监听器
            addKeyListener(this);
            // 通 过 永 久 循 环 语 句 让 赛 车 1 不 断 向 前 行 驶
            while (true)
            {
                try
                {
                    // 刷新游戏屏幕
                    repaint();
                    //碰撞检测.intersects为  矩形类中提供的方法,检测 相交.碰撞减速
                    for (int i = 0; i < zhangAiWuArray.length; i++)
                    {
                        if (car1.intersects(zhangAiWuArray[i]))
                        {
                            car1Speed = -4;
                        }
                    }
                    if (car1.intersects(center))
                    {
                        car1Speed = -2.5;
                    }
                    //赛车行驶过程中不断加速,max为5
                    if (car1Speed <= 5)
                    {
                        car1Speed += 0.2;
                    }

                    //通过键盘控制赛车方向
                    if (car1Direction == UP)
                    {
                        car1.y -= (int) car1Speed;
                    }
                    if (car1Direction == DOWN)
                    {
                        car1.y += (int) car1Speed;
                    }
                    if (car1Direction == LEFT)
                    {
                        car1.x -= (int) car1Speed;
                    }
                    if (car1Direction == RIGHT)
                    {
                        car1.x += (int) car1Speed;
                    }
                    // 设 置 每 次 加 速 的 间 隔 时 间,线程每过75ms执行一次此循环
                    Thread.sleep(75);
                }
                catch (Exception e)
                {
                    // 如 果 程 序 运 行 出 错 ， 那 么 退 出 循 环
                    break;
                }
            }
        }

        //实现自KeyListener中的方法,该方法在键入某个键时调用
        @Override
        public void keyTyped(KeyEvent e)
        {
            if (e.getKeyChar() == 'w')
            {
                car1Direction = UP;
            }
            if (e.getKeyChar() == 'a')
            {
                car1Direction = LEFT;
            }
            if (e.getKeyChar() == 's')
            {
                car1Direction = DOWN;
            }
            if (e.getKeyChar() == 'd')
            {
                car1Direction = RIGHT;
            }
        }

        //该方法在按下某个键时调用
        @Override
        public void keyPressed(KeyEvent e)
        {

        }

        //该方法在释放某个键时调用
        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }

    private class Move2 extends Thread
    {
        public void run()
        {
            // 通 过 永 久 循 环 语 句 让 赛 车 1 不 断 向 前 行 驶
            while (true)
            {
                try
                {
                    // 刷新游戏屏幕
                    repaint();
                    // 赛 车 行 驶 中 加 速
                    if (car2Speed <= 5)
                    {
                        car2Speed += 0.2;
                    }
                    car2.y -= car2Speed;
                    // 设 置 每 次 加 速 的 间 隔 时 间
                    Thread.sleep(75);
                }
                catch (Exception e)
                {
                    // 如 果 程 序 运 行 出 错 ， 那 么 退 出 循 环
                    break;
                }
            }
        }
    }
}
