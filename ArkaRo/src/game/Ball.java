package game;

import player.Paddle;

import java.awt.*;
import java.util.*;

public class Ball {
    private static int DefaultBallRadius = 6;
    private Point position = new Point(0, 0);

    private int ballRadius;
    private Game instance;
    private Dimension vector;

    public Ball(int x, int y, int ballRadius, Game instance) {
        position = new Point(x, y);
        this.ballRadius = ballRadius;
        this.instance = instance;
    }

    public void render(Graphics g) {

        if (ballRadius < 10) {
            g.setColor(new Color(200, 100, 0));
        }
        if ((ballRadius > 10) && (ballRadius < 20))
            g.setColor(new Color(0,255,0));
        g.fillOval(position.x-ballRadius, position.y-ballRadius, ballRadius*2, ballRadius*2);
    }

    public void move(){
        if(position.x-ballRadius<=0 && vector.width<0) vector.width = -vector.width;
        if(position.x+ballRadius>=instance.getGameField().width && vector.width>0) vector.width = -vector.width;
        if(position.y-ballRadius<=0 && vector.height<0) vector.height = -vector.height;
        if(position.y+ballRadius>=instance.getGameField().height && vector.height>0) {
            instance.loseBall();
        }

        if (instance.getPaddle() != null){
            if (instance.getPaddle().collidesWith(new Rectangle(position.x-ballRadius+vector.width, position.y-ballRadius+vector.height,ballRadius*2, ballRadius*2))){

                Paddle p = instance.getPaddle();
                if (position.x >= p.getX() && position.x <= p.getX()+p.getWidth() || (position.x+ballRadius*2) >= p.getX() && (position.x+ballRadius*2) <= p.getX()+p.getWidth() ){
                    vector.height = -vector.height;
                } else if (position.y >= p.getY() && position.y <= p.getY()+p.getHeight() || (position.y+ballRadius*2) >= p.getY() && (position.y+ballRadius*2) <= p.getY()+p.getHeight() ){
                    vector.width = -vector.width;
                }
            }
        }
        position.move(position.x+vector.width, position.y+vector.height);


    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }

    public static int getDefaultBallRadius() {
        Random rand = new Random();
        return DefaultBallRadius = rand.nextInt(30) + 5;
    }

    public void setVector(int x, int y) {
        this.vector = new Dimension(x, y);
    }
}