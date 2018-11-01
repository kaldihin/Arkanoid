
import java.awt.*;
import java.util.ArrayList;

public class Block {
    private int x = 5, y = 5;
    private Rectangle block;
    private int blockHits = 0;
    private TheGame theGame;
    public Block(int x, int y, int width, int height) {
        this.block = new Rectangle(x, y, width, height);
    }

    public boolean collidesWithMy(int i1, int i2) {
        return (block.x + block.width > i1) && (block.y + block.height > i2);
    }

    public void blockHit() {
        blockHits++;
    }

    public int getBlockHits() {
        return blockHits;
    }

    public void render(Graphics g) {
        if (blockHits == 0)
        g.setColor(new Color(190,67,87));
        if (blockHits == 1)
            g.setColor(new Color(23,156,67));
        if (blockHits == 2)
            g.setColor(new Color(12,56,170));
        g.fillRect(block.x, block.y, block.width, block.height);
    }
}
