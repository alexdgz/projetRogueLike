import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Fantome extends Enemy {
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    private Image sprite;
    private int xEnemy, yEnemy;

    public Fantome() {
        this.speed = 2;
        this.points = 1;
        this.damage = 1;
        this.xEnemy = (int) (Math.random() * (1200 - 500)) + 1200;;
        this.yEnemy = (int) (Math.random() * (1200 - 500)) + 1200;;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("ghost.png")); // On créé un objet Image a partir du sprite du fantome
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hitbox = new Rectangle(xEnemy, yEnemy, sprite.getWidth(null), sprite.getHeight(null)); // On créé la hitbox du fantome a partir du sprite
    }
    public int attack() {
        return damage;
    }
    public Image getSprite() {
        return sprite;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }



    public int getSpeed() {
        return speed;
    }
    public int getPoints() {
        return points;
    }

    public int getXEnemy() {
        return xEnemy;
    }
    public int getYEnemy() {
        return yEnemy;
    }

    public void setXEnemy(int xEnemy) {
        this.xEnemy = xEnemy;
    }
    public void setYEnemy(int yEnemy) {
        this.yEnemy = yEnemy;
    }

}
