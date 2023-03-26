import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Fantome extends Enemy {
    private int health;
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    private Image sprite;

    public Fantome() {
        this.health = 1;
        this.speed = 2;
        this.points = 1;
        this.damage = 1;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hitbox = new Rectangle(0, 0, sprite.getWidth(null), sprite.getHeight(null));
    }
    public Image getSprite() {
        return sprite;
    }

    public int attack() {
        // Attaque du fantome
        return damage;
    }

}
