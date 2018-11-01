import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Entry {
    public static JFrame frame;
    public static TheGame game;

    public static void main(String[] args) {
        frame = new JFrame("ArkanoidK");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TheGame(frame);
        game.setSize(frame.getSize());
        frame.add(game);

        frame.setVisible(true);
    }

}
