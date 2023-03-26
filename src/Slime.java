import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Slime extends Enemy {
    private int health;
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    private Image sprite;

    public Slime() {
        this.health = 2;
        this.speed = 1;
        this.points = 2;
        this.damage = 2;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("slime1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hitbox = new Rectangle(0, 0, sprite.getWidth(null), sprite.getHeight(null));
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }
    public int getPoints() {
        return points;
    }
}