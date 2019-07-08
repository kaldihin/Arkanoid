import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TheGame extends JPanel {

    private Dimension gameField = new Dimension(650, 650);
    private boolean isRunning = false;
    private boolean isPaused = false;
    private int positionM, targetX;
    private int blockHeight = 30;
    private int blockSpace = 5;
    private static int blockPieces = 4;
    private int newX = 9, newY = 5;
    private int blockWidth = (gameField.width / blockPieces) - blockSpace * blockPieces;
    private Block[] blocks = new Block[blockPieces];
//    private static List<Block> blocks = Collections.synchronizedList(new ArrayList<>(blockPieces));
    private Random rand = new Random();

    private Plate plate;

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
                        plate.moveOnXAxis(-targetX);
                        positionM = e.getX();
                    }
                    if (e.getX() > positionM) {
                        targetX = e.getX() - positionM;
                        plate.moveOnXAxis(targetX);
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
//                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) plate.moveOnXAxis(60);
//                    if (e.getKeyCode() == KeyEvent.VK_LEFT) plate.moveOnXAxis(-60);
//                }
//            }
//        });

        plate = new Plate(this, (int) ((gameField.getWidth() - Plate.standardPlateWidth) / 2),
                gameField.height - Plate.standardPlateHeight, Plate.standardPlateWidth, Plate.standardPlateHeight);
        ball = new Ball(this, gameField.width / 2, gameField.height / 2, Ball.randomBallRadius);
        for (int i = 0; i < blockPieces; i++) {
            blocks[i] = (new Block(newX, newY, blockWidth, blockHeight));
            newX = newX + blockWidth + blockSpace;
        }

    }

    public void loseBall() {
//        stop();
        ball.setPosition(gameField.width / 2, gameField.height / 2);
        plate.setX((int) ((gameField.getWidth() - Plate.standardPlateWidth) / 2));
        plate.setY(gameField.height - Plate.standardPlateHeight);
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

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Plate getPlate() {
        return this.plate;
    }

    public Block[] getBlock() {
        return blocks;
    }

    public void setSize(Dimension size) {
        super.setSize(size);
        if (!isRunning) {
            gameField = new Dimension(size.width - 200, size.height - 200);
            ball.setPosition(gameField.width / 2, gameField.height / 2);
            plate.setX((int) ((gameField.getWidth() - Plate.standardPlateWidth) / 2));
            plate.setY(gameField.height - Plate.standardPlateHeight);
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

        g.translate((getWidth() - gameField.width) / 2, (getHeight() - gameField.height) / 2);          //From which corner to begin drawing


        ball.render(g);
        plate.render(g);

        for (int i = 0; i < 4; i++) {
            if (blocks[i].getBlockHits() < 3)
                blocks[i].render(g);
        }


        g.setColor(new Color(0, 0, 0));         //Border for the playing field
        g.drawRect(0, 0, gameField.width, gameField.height);
    }

}
