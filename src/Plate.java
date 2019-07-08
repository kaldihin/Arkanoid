import java.awt.*;

public class Plate {

    public static int standardPlateWidth = 150;
    public static int standardPlateHeight = 20;
    private Rectangle hitBox;
    private TheGame instance;

    public Plate(TheGame inst, int x, int y, int width, int height) {
        instance = inst;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void moveOnXAxis(int speed) {
        hitBox.x += speed;
        if (hitBox.x < 0) hitBox.x = 0;
        if (hitBox.x > instance.getGameDimension().width - instance.getPlate().hitBox.width)
            hitBox.x = instance.getGameDimension().width - instance.getPlate().hitBox.width;

        //hitBox.x = ((hitBox.x < 0)? 0:((hitBox.x > instance.getGameDimension().width-instance.getPlate().hitBox.width)? instance.getGameDimension().width-instance.getPlate().hitBox.width:hitBox.x + speed));

    }

    public boolean collidesWith(Rectangle object) {
        return hitBox.intersects(object);
    }

    public void setX(int x) {
        hitBox.x = x;
    }

    public void setY(int y) {
        hitBox.y = y;
    }

    public int getX() {
        return hitBox.x;
    }

    public int getY() {
        return hitBox.y;
    }

    public int getWidth() {
        return this.hitBox.width;
    }

    public int getHeight() {
        return this.hitBox.height;
    }

    public void render(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

}
