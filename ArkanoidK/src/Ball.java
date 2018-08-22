import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Random;

public class Ball {

    public static int standartBallRadius = 14;
    public static int randomBallRadius = new Random().nextInt(20) + 10;
    private TheGame instance;
    private Dimension vector = new Dimension(0, 0);
    private Point pos;
    private int radius;

    public Ball(TheGame inst, int x, int y, int radius) {
        instance = inst;
        pos = new Point(x, y);
        this.radius = radius;
    }

    public void setVector(int XMovement, int YMovement) {
        vector = new Dimension(XMovement, YMovement);
    }

    public Point getPosition() {
        return pos;
    }

    public void setPosition(int x, int y) {
        pos = new Point(x, y);
    }

    public void tick() {
        if (pos.x - radius <= 0 && vector.width < 0) vector.width = -vector.width;
        if (pos.x + radius >= instance.getGameDimension().width && vector.width > 0) vector.width = -vector.width;
        if (pos.y - radius <= 0 && vector.height < 0) vector.height = -vector.height;
        if (pos.y + radius >= instance.getGameDimension().height && vector.height > 0) instance.loseBall();

        if (instance.getPlayer() != null) {
            if (instance.getPlayer().collidesWith(new Rectangle(pos.x - radius, pos.y - radius, radius * 2, radius * 2))) {
                vector.height = -vector.height;
            }
        }
        for (Block bl : instance.getBlock()) {
            if (bl.collidesWithMy(pos.x - radius, pos.y - radius)) {
                bl.blockHit();
                if (bl.getBlockHits() < 3)
                    vector.height = -vector.height;
            }
        }

        pos.move(pos.x + vector.width, pos.y + vector.height);
    }

    public void render(Graphics g) {

        g.setColor(new Color(0, 156, 0));
        g.fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
//        ImageIcon ii = new ImageIcon("C:\\Users\\Женёк\\Desktop\\Main\\Java\\ArkanoidK\\src\\2.gif");
//        g.drawImage(ii.getImage(), pos.x, pos.y, 50, 50, null);

    }

}
