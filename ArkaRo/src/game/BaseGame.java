package game;

import javax.swing.*;

public class BaseGame{
    private JFrame jFrame;
    private Game game;

    public void windowCreate(){
        jFrame = new JFrame("Arcanoid");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setSize(620, 700);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    public void gameInitialize(){
        game = new Game(jFrame);
        game.setSize(jFrame.getSize());
        jFrame.add(game);
    }
}

