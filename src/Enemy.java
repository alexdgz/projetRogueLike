import java.awt.*;

public abstract class Enemy {
    private int health;
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    public abstract void attack();
}