import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TheGame extends JPanel {

    private Dimension gameField = new Dimension(650, 650);
    private boolean isRunning = false;
    private boolean isPaused = false;
    private int positionM, targetX;
    private int blockHeight = 30;
    private int blockSpace = 5;
    private int blockPieces = 4;
    private int newX = 9, newY = 5;
    private int blockWidth = (gameField.width / blockPieces) - blockSpace * blockPieces;
    private ArrayBlockingQueue<Block> blocks = new ArrayBlockingQueue<Block>(blockPieces);
    private Random rand = new Random();
    private Block block;

    private Player player;

    private Ball ball;

    public TheGame(Frame container) {
        container.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isRunning) {
                    start();
                    positionM = e.getX();
                } else {
                    if (e.getX() < positionM) {
                        targetX = positionM - e.getX();
                        player.moveOnXAxis(-targetX);
                        positionM = e.getX();
                    }
                    if (e.getX() > positionM) {
                        targetX = e.getX() - positionM;
                        player.moveOnXAxis(targetX);
                        positionM = e.getX();
                    }
                }
            }
        });
//
//        container.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent e) {
//
//                if (!isRunning || isPaused) {
//                    if (e.getKeyCode() == KeyEvent.VK_ENTER) start();
//                } else {
//                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveOnXAxis(60);
//                    if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveOnXAxis(-60);
//                }
//            }
//        });

        player = new Player(this, (int) ((gameField.getWidth() - Player.standartPlayerWidth) / 2),
                gameField.height - Player.standartPlayerHeight, Player.standartPlayerWidth, Player.standartPlayerHeight);
        ball = new Ball(this, gameField.width / 2, gameField.height / 2, Ball.randomBallRadius);
        for (int i = blockPieces; i > 0; i--) {
            blocks.add(new Block(newX, newY, blockWidth, blockHeight));
            newX = newX + blockWidth + blockSpace;
        }

    }

    public void loseBall() {
//        stop();
        ball.setPosition(gameField.width / 2, gameField.height / 2);
        player.setX((int) ((gameField.getWidth() - Player.standartPlayerWidth) / 2));
        player.setY(gameField.height - Player.standartPlayerHeight);
        repaint();
    }

    public void start() {
        isPaused = false;
        if (!isRunning) {
            gameThread.start();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void stop() {
        isRunning = false;
        if (gameThread.isAlive())
        gameThread.interrupt();
    }

    public Dimension getGameDimension() {
        return gameField;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayBlockingQueue<Block> getBlock() {
        return blocks;
    }

    public void setSize(Dimension size) {
        super.setSize(size);
        if (!isRunning) {
            gameField = new Dimension(size.width - 200, size.height - 200);
            ball.setPosition(gameField.width / 2, gameField.height / 2);
            player.setX((int) ((gameField.getWidth() - Player.standartPlayerWidth) / 2));
            player.setY(gameField.height - Player.standartPlayerHeight);
        }

    }

    Thread gameThread = new Thread(new Runnable() {
        public void run() {
            isRunning = true;
            ball.setVector(rand.nextInt(20) + 7, rand.nextInt(20) + 7);
            while (isRunning && !Thread.interrupted()) {
                ball.tick();

                repaint();
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                }
            }
        }
    });

    public void paint(Graphics g) {
        super.paint(g);

        g.translate((getWidth() - gameField.width) / 2, (getHeight() - gameField.height) / 2);

        //g.setColor(new Color(23, 100, 165));
        //g.fillRect(100, 100, 10, 10);

        ball.render(g);
        player.render(g);
        for (Block bl : blocks) {
            if (bl.getBlockHits() < 3)
                bl.render(g);
        }


        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, gameField.width, gameField.height);
    }

}
