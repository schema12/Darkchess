package com.hut.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

//游戏面板
public class GamePanel extends JPanel {
    /*
       g.drawImage画图片
       g.drawChars画文字
       g.drawLine画直线
       g.drawOval画圆或椭圆
     */
    private Shuffile.chess[] chesses = new Shuffile.chess[32];//保存所有的棋子


    //当前选中的棋子
    private Shuffile.chess selectedChess;

    //记住当前阵营，默认红方
    private  char curPlayer = 'R';

    //构造方法
    public GamePanel() {
        createChesses();

        //添加点击事件
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                System.out.println("x="+e.getX()+",y="+e.getY());
                Point p = Shuffile.chess.getPointFromXY(e.getX(),e.getY());
                System.out.println(p);

                if (selectedChess==null){
                    //第一次选择
                    selectedChess = getChessByp(p);
                    if (selectedChess != null && selectedChess.getInitial() != curPlayer){
                        selectedChess = null;
                    }
                }else{
                    Shuffile.chess c =getChessByp(p);
                    if(c!=null){
                        //第n次点击的时候有棋子，重新选择或吃子
                        if (Character.compare(c.getInitial(),selectedChess.getInitial())==0){
                            //chongxingxuanze
                            System.out.println("重新选择");
                            selectedChess= c;
                        }else{
                            //
                            System.out.println("chizi");
                            if (selectedChess.isAbleMove(p,GamePanel.this,c.getName())){

                                chesses[c.getIndex()] = null;//从数组中删除被吃掉的棋子
                                selectedChess.setP(p);
                                overMyTurn();

                            }
                        }

                    }else{
                        //..............没有棋子，移动
                        System.out.println("移动");
                        if (selectedChess.isAbleMove(p,GamePanel.this,c.getName())){
                            selectedChess.setP(p);
                            overMyTurn();

                        }
                    }
                }
                System.out.println(selectedChess);

                //刷新棋盘
                repaint();
            }
        });
    }

    /**
     * 结束当前回合
     */
    private  void overMyTurn(){
        curPlayer = curPlayer == 'R' ? 'B' : 'R';
        selectedChess = null;
    }

    /**
     * 根据网格坐标p对象查找棋子对象
     */
    public Shuffile.chess getChessByp(Point p){
        for (Shuffile.chess item: chesses) {
            if (item !=null && item.getP().equals(p)) {
                return item;
            }
        }
        return null;
    }



    //创建棋子
    private void createChesses() {
        String[] arr = {"R1", "R1", "R1", "R1", "R1", "R2", "R2", "R3", "R3", "R4", "R4", "R5", "R5", "R6", "R6", "R7",
                "B1", "B1", "B1", "B1", "B1", "B2", "B2", "B3", "B3", "B4", "B4", "B5", "B5", "B6", "B6", "B7"};
        Shuffile.shuffle(arr);
        Point[] ps = {new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(1, 4),
                new Point(2, 1), new Point(2, 2), new Point(2, 3), new Point(2, 4),
                new Point(3, 1), new Point(3, 2), new Point(3, 3), new Point(3, 4),
                new Point(4, 1), new Point(4, 2), new Point(4, 3), new Point(4, 4),
                new Point(5, 1), new Point(5, 2), new Point(5, 3), new Point(5, 4),
                new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4),
                new Point(7, 1), new Point(7, 2), new Point(7, 3), new Point(7, 4),
                new Point(8, 1), new Point(8, 2), new Point(8, 3), new Point(8, 4),};
        int num = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                Shuffile.chess c = new Shuffile.chess();
                c.setName(arr[num]);
                c.setP(ps[num]);
                c.setIndex(num);
                chesses[num] = c;
                num += 1;
            }
        }
    }



    //绘制棋子
    private void drawChesses(Graphics g){
        for (Shuffile.chess item: chesses) {
            if (item != null){
                item.draw(g,this);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        //super.paint(g);

        //图片路径   File.separator路径分隔符
        String bgPath = "Image" + File.separator + "board.jpg";

        //      获取toolkit的实例     获取图片
        Image bgImage = Toolkit.getDefaultToolkit().getImage(bgPath);

        //将获取的图片画到背景上
        g.drawImage(bgImage,0,0,1000,600,this);

        //将棋子背面画到棋盘
       /* for (int i=0;i<8;i++){
            for (int j=0;j<4;j++){
                String chessBgPath = "Image" + File.separator + "Hide.png";

                //      获取toolkit的实例     获取图片
                Image chessBgImage = Toolkit.getDefaultToolkit().getImage(chessBgPath);

                //将获取的图片画到背景上 x=120 y=145
                g.drawImage(chessBgImage,10+123*i,10+143*j,120,145,this);
           }
        }*/

        drawChesses(g);

        if (selectedChess != null){
            selectedChess.drawRect(g);
        }


         //   System.out.println(Arrays.toString(chesses));
        }
    }

//    //通过坐标计算棋子的索引位置
//    public int getChessIndexByp(Point p){
//        for (int i = 0; i < chesses.length; i++) {
//            if (chesses[i].getP().equals(p)) {
//                return i;
//            }
//        }
//        return -1;
//    }

