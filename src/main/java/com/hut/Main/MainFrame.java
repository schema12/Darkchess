package com.hut.Main;

import javax.swing.*;

public class MainFrame {

    public static void main(String[] args) {
        JFrame frm= new JFrame();
        //设置窗口大小
        frm.setSize(1100,700);

        //设置窗口剧中
        frm.setLocationRelativeTo(null);

        //设置点击关闭按钮同时结束虚拟机
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //将游戏面板添加到窗口中
        frm.add(new GamePanel());

        //窗口可视化
        frm.setVisible(true);
    }
}
