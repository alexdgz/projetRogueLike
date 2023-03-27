import java.awt.*;

public abstract class Enemy {
    private int speed;
    private int points;
    private int damage;
    private Rectangle hitbox;
    private Image sprite;

    private int xEnemy, yEnemy;

    public abstract Image getSprite();
    public abstract int attack();
    public abstract Rectangle getHitbox();

    public abstract int getSpeed();
    public abstract int getPoints();

    public abstract int getXEnemy();
    public abstract int getYEnemy();

    public abstract void setXEnemy(int xEnemy);
    public abstract void setYEnemy(int yEnemy);

}