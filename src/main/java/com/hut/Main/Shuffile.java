package com.hut.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by 燃烧杯 on 2018/5/12.
 */
public class Shuffile {

    private static Random rand = new Random();

    public static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for (int i = length; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"R1","R1","R1","R1","R1","R2","R2","R3","R3","R4","R4","R5","R5","R6","R6","R7",
                "B1","B1","B1","B1","B1","B2","B2","B3","B3","B4","B4","B5","B5","B6","B6","B7"};
        shuffle(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static class chess {
        private final int wide=120;
        private final int high=145;
        private static final int space_x=123;
        private static final int space_y=143;
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private String player;//R为红方 B为黑方

        public String getPlayer() {
            return player;
        }

        private String suffix=".png";
        private int x,y;//坐标
        protected Point p;//网格坐标

        private  Point initp;

        //保存每个棋子的索引位置
        private  int index;

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }


        public void setP(Point p) {
            this.p = (Point)p.clone() ;
            if(initp==null){
                initp = this.p;
            }
            cal_xy();
        }

        public Point getP() {
            return p;
        }

        /**
         * 是否在棋盘范围内
         * @param
         * @param
         */
        public  boolean isChessboard(Point tp){
            if ((tp.x<1 || tp.x >8) || (tp.y<1 || tp.y >4)){
                return false;
            }
            return true;
        }

        /**
         * 计算起点到目标点的距离
         * @param tp
         * @return
         */
        public  int getStep(Point tp){
            int line=line(tp);
            if (line==3){
                //x
                return Math.abs(p.x-tp.x);
            }else if (line==2 || line==1){
                //y
                return Math.abs(p.y-tp.y);
            }
            return 0;
        }

        /**
         * 计算起点到目标点之间是否有棋子，不包括起点和目标点。
         * @param tp
         * @return
         */
        public int getCount(Point tp, GamePanel gamePanel){
            int start=0, end=0;
            int count=0; //统计棋子
            int line = line(tp);

            Point np = new Point();
            if(line == 2){
                //y
                np.x = tp.x;
                if (tp.y > p.y){
                    //从上往下
                    start = p.y+1;
                    end = tp.y;
                }else {
                    //从下往上
                    start = tp.y+1;//-1
                    end = p.y;
                }
                for (int i = start;i < end ;i++){
                    np.y = i;
                    if (gamePanel.getChessByp(np) != null){
                        count++;
                    }

                }
            }else if (line == 3){
                //x
                np.y = tp.y;
                if (tp.x > p.x){
                    //从左往右
                    start = p.x+1;
                    end = tp.x;

                }else {
                    //从右往左
                    start = tp.x+1;//-1
                    end = p.x;

                }
                for (int i = start;i < end ;i++){
                    np.x = i;
                    if (gamePanel.getChessByp(np) != null){
                        count++;
                    }

                }
            }
            return count;
        }

        /**
         * 判断走直线还是斜线
         * 3：x直线；2：y直线；1：斜线；0：都不是；
         * @param tp
         * @return
         */
        public  int line(Point tp){
            if (p.y == tp.y){
               //横着走
                return 3;
            }else if (p.x == tp.x){
                //竖着走
                return 2;
            }else if (Math.abs(p.x-tp.x)==Math.abs(p.y-tp.y)){
                //走斜线
                return 1;
            }
            return 0;
        }

        /**
         * 棋子的绘制方法
         * @param g
         * @param panel
         */
        public void draw(Graphics g, JPanel panel){
            String chessBgPath = "Image" + File.separator  + name + suffix;

            //      获取toolkit的实例     获取图片
            Image chessImage = Toolkit.getDefaultToolkit().getImage(chessBgPath);

            //将获取的图片画到背景上 x=120 y=145
            g.drawImage(chessImage,x,y,wide,high,panel);
        }

        /**
         * 移动规则
         * @param tp
         * @param gamePanel
         * @return
         */
        public boolean isAbleMove(Point tp, GamePanel gamePanel, String tpChess){

            //将
            if ("R7".equals(name) || "B7".equals(name)){

                return line(tp) > 1 && isChessboard(tp) && getStep(tp)==1 && isEatChess(tpChess);

            //士
            }else if ("R6".equals(name) || "B6".equals(name)){

                return line(tp) > 1 && isChessboard(tp) && getStep(tp)==1 && isEatChess(tpChess);
                //象
            }else if ("R5".equals(name) || "B5".equals(name)){

                return line(tp) > 1 && isChessboard(tp) && getStep(tp)==1 && isEatChess(tpChess);

                //车
            }else if ("R4".equals(name) || "B4".equals(name)){

                return line(tp)>1 && getCount(tp,gamePanel)== 0;


                //马
            }else if ("R3".equals(name) || "B3".equals(name)){

                return line(tp) == 1 && isChessboard(tp) && getStep(tp)== 1;

                //炮
            }else if ("R2".equals(name) || "B2".equals(name)){

                chess c = gamePanel.getChessByp(tp);
                if (c != null){
                    if (Character.compare(c.getInitial(),this.getInitial())!=0){
                        //吃子
                        return line(tp)> 1 && getCount(tp,gamePanel)== 1;
                    }
                }else {
                    //移动
                    return line(tp)> 1 && getCount(tp,gamePanel)== 0 && isEatChess(tpChess);
                }


                //兵
            }else if ("R1".equals(name) || "B1".equals(name)){

                return line(tp) > 1 && isChessboard(tp) && getStep(tp)==1;

            }
            return false;
        }

        //
        private boolean isEatChess(String tpChess){
            //将
            if("R7".equals(name) || "B7".equals(name)){
                if("R1".equals(tpChess) || "B1".equals(tpChess)){
                    return false;
                }

            }else if ("R6".equals(name) || "B6".equals(name)){
                if("R7".equals(tpChess) || "B7".equals(tpChess)){
                    return false;
                }
            }else if ("R5".equals(name) || "B5".equals(name)){
                if ("R7".equals(tpChess) || "B7".equals(tpChess) ||
                        "R6".equals(tpChess) || "B6".equals(tpChess)){
                    return false;
                }
            }else if ("R1".equals(name) || "B1".equals(name)){
                if ("R7".equals(tpChess) || "B7".equals(tpChess) ||
                        "R1".equals(tpChess) || "B1".equals(tpChess)){
                    return true;
                }else {
                    return false;
                }
            }
            return true;
        }

        /**
         * 为选中的棋子增加边框
         * @param g
         */
        public void drawRect(Graphics g) {
            g.drawRect(x,y,wide,high);

        }

        /**
         * 计算绘制坐标
         */
        public void cal_xy(){
            x=10+space_x*(p.x-1);
            y=10+space_y*(p.y-1);
        }

        /**
         * 根据xy坐标推算网格坐标
         * @param x
         * @param y
         * @return
         */
        public static Point getPointFromXY(int x,int y){
            Point p = new Point();
            p.x=(x-10)/space_x+1;
            p.y=(y-10)/space_y+1;
            if(p.x < 1 || p.x > 8 || p.y < 1 || p.y > 4){
                return null;
            }
            return  p;
        }

        /**
         * 获取当前棋子的名的首字母判断阵营
         * @return
         */
        public char getInitial (){
            char c = name.charAt(0);
            return c;
        }
    }
}
