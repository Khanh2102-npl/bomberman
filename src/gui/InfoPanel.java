package gui;

import main.Game;

import javax.swing.*;
import java.awt.*;

/*
 * Lớp tạo 1 bảng chứ thông tin về thời gian và diểm số người chơi đạt được
 */

public class InfoPanel extends JPanel{
    private JLabel timeLabel;
    private JLabel pointLabel;

    public InfoPanel(Game game){
        setLayout(new GridBagLayout());

        timeLabel = new JLabel("Time: " + game.getBoard().getTime());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        pointLabel = new JLabel("Point: " + game.getBoard().getPoint());
        pointLabel.setForeground(Color.WHITE);
        pointLabel.setHorizontalAlignment(JLabel.CENTER);

        add(timeLabel);
        add(pointLabel);

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(0, 40));
    }

    public void setTime(int time) {
        timeLabel.setText("Time: " + time);
    }
    
    public void setPoint(int point) {
        pointLabel.setText("Point: " + point);
    }
    
     
}