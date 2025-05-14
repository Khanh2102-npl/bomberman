package gui;

import main.Game;

import javax.swing.*;
import java.awt.*;

/*
 * Cửa số chính của Game
 * Tập hợp các thành phần như vùng chơi và bảng thông tin
 */

public class Frame extends JFrame {
    private MainMenuPanel mainMenu;
    private Game game;

    public Frame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Bomberman Game");
        setLayout(new CardLayout());

        mainMenu = new MainMenuPanel(this);
        add(mainMenu, "Menu");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame() {
        if (game == null) {
            game = new Game(this);
            add(game, "Game");
            pack(); // cập nhật kích thước lại
        }

        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "Game");

        // GỌI FOCUS Ở ĐÂY
        SwingUtilities.invokeLater(() -> {
            game.requestFocusInWindow();  // đảm bảo gọi sau khi hiển thị
        });

        game.start();
    }



    public void setTime(int time) {
        // nếu bạn cần cập nhật thời gian
    }

    public void setPoint(int point) {
        // nếu bạn cần cập nhật điểm
    }
}