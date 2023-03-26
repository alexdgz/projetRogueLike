import java.awt.*;

public abstract class Enemy {
    private int health;
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    private Image sprite;

    private int xEnemy, yEnemy;

    public abstract Image getSprite();
    public abstract int attack();
    public abstract Rectangle getHitbox();
    public abstract int getHealth();
    public abstract void setHealth(int health);
    public abstract int getSpeed();
    public abstract int getPoints();

}