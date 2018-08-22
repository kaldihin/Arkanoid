package player;

import game.Game;

import java.awt.*;

public class Paddle {
    private static int defaultWidth = 100;
    private static int defaultHeight = 10;
    private int width;
    private int height;
    private Rectangle hitBox;
    private Game instance;

    public Paddle(int x, int y, int width, int height , Game instance) {
        hitBox = new Rectangle(x, y, width, height);
        this.instance = instance;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g){
        g.setColor(new Color(200,200,100));
        g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void moveOnXAxis(int speed){
        hitBox.x += speed;
        if(hitBox.x < 0) hitBox.x = 0;
        else if (hitBox.x >= instance.getGameField().width-width) hitBox.x = instance.getGameField().width-width;

    }

    public void setPosition(int x){
        hitBox.x = x;
    }

    public boolean collidesWith(Rectangle object){
        return hitBox.intersects(object);
    }

    public int getX() {
        return hitBox.x;
    }
    public int getY() {
        return hitBox.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int getDefaultWidth() {
        return defaultWidth;
    }

    public static int getDefaultHeight() {
        return defaultHeight;
    }
}
