package gui;

import main.Game;

import javax.swing.*;
import java.awt.*;

/*
 * Cửa số chính của Game
 * Tập hợp các thành phần như vùng chơi và bảng thông tin
 */

public class Frame extends JFrame{
    public GamePanel gamepanel;
    private JPanel containerpanel;
    private InfoPanel infopanel;
    private Game game;

    public Frame(){
        containerpanel = new JPanel(new BorderLayout());
        gamepanel = new GamePanel(this);
        infopanel = new InfoPanel(gamepanel.getGame());

        containerpanel.add(infopanel, BorderLayout.PAGE_START);
        containerpanel.add(gamepanel, BorderLayout.PAGE_END);

        game = gamepanel.getGame();

        add(containerpanel);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    public void setTime(int time){
        infopanel.setTime(time);
    }

    public void setPoint(int point){
        infopanel.setPoint(point);
    }
}