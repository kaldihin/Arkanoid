package game;

import player.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JPanel{
    private Dimension gameField = new Dimension(600, 600);
    private boolean isPaused;
    private boolean isRunning;

    private int life = 3;


    private Paddle paddle;
    private Ball ball, ball2, ball3;

    public Game(JFrame jFrame){


        /*jFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if (life == 0){
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0);
                } else if(!isRunning){
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) start();
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) paddle.moveOnXAxis(20);
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) paddle.moveOnXAxis(-20);
                }
            }
        });*/


        paddle = new Paddle((int)(gameField.getWidth()-Paddle.getDefaultWidth())/2 , (int)(gameField.getHeight()-Paddle.getDefaultHeight()-5), Paddle.getDefaultWidth(), Paddle.getDefaultHeight(), this);
        ball = new Ball(gameField.width/2, gameField.height/2, Ball.getDefaultBallRadius(), this);
        ball2 = new Ball(gameField.width/2, gameField.height/2, Ball.getDefaultBallRadius(), this);
        ball3 = new Ball(gameField.width/2, gameField.height/2, Ball.getDefaultBallRadius(), this);

    }

    private Thread gameThread = new Thread(new Runnable() {
        @Override
        public void run() {
            isRunning = true;
            ball.setVector(10,10);
            ball2.setVector(-10,-10);
            ball3.setVector(-5,5);

            while (isRunning){
                repaint();

                if (!isPaused) {
                    ball.move();
                    ball2.move();
                    ball3.move();

                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    public void loseBall(){

        if (life > 0) {
            life--;
        } else { isRunning = false; }

        /*paddle.setPosition((gameField.width-Paddle.getDefaultWidth())/2);
        ball.setPosition(gameField.width/2, gameField.height/2);
        ball2.setPosition(gameField.width/2, gameField.height/2);
        ball3.setPosition(gameField.width/2, gameField.height/2);}*/

    }


    public void start(){
        isPaused = false;
        if (!isRunning) gameThread.start();
    }

    public void pause(){
        isPaused = true;
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate((getWidth()-gameField.width)/2, (getHeight()-gameField.height)/2);
        g.fillRect(0,0, gameField.width, gameField.height);


        paddle.render(g);
        ball.render(g);
        ball2.render(g);
        ball3.render(g);


        g.drawRect(0,0 , gameField.width, gameField.height);


    }

    public Dimension getGameField() {
        return gameField;
    }

    public Paddle getPaddle() {
        return paddle;
    }

}
