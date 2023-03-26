import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player {
    private static Player instance = null;
    private int score = 0;
    private int health = 3;
    private int vitesse = 10; // Vitesse de déplacement

    private int xPersonnage, yPersonnage;

    private Image personnageLeft, personnageRight, personnageAttackLeft, personnageAttackRight;

    private Rectangle rectanglePersonnage,rectangleEpee;
    private Player() {
        this.xPersonnage = 100;
        this.yPersonnage = 100;
        this.rectangleEpee = new Rectangle(1000,1000, 50, 50);

        try {
            personnageLeft = ImageIO.read(getClass().getResourceAsStream("knight1.png"));
            personnageRight = ImageIO.read(getClass().getResourceAsStream("knight2.png"));
            personnageAttackLeft = ImageIO.read(getClass().getResourceAsStream("knight3.png"));
            personnageAttackRight = ImageIO.read(getClass().getResourceAsStream("knight4.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        rectanglePersonnage = new Rectangle(xPersonnage, yPersonnage, personnageLeft.getWidth(null), personnageLeft.getHeight(null));

    }


    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void moveLeft() {
        xPersonnage -= vitesse;
        rectanglePersonnage.setLocation(xPersonnage, yPersonnage);
    }

    public void moveRight() {
        xPersonnage += vitesse;
        rectanglePersonnage.setLocation(xPersonnage, yPersonnage);
    }
    public void moveUp() {
        yPersonnage -= vitesse;
        rectanglePersonnage.setLocation(xPersonnage, yPersonnage);
    }
    public void moveDown() {
        yPersonnage += vitesse;
        rectanglePersonnage.setLocation(xPersonnage, yPersonnage);
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getVitesse() {
        return vitesse;
    }
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public int getxPersonnage() {
        return xPersonnage;
    }
    public void setxPersonnage(int xPersonnage) {
        this.xPersonnage = xPersonnage;
    }
    public int getyPersonnage() {
        return yPersonnage;
    }
    public void setyPersonnage(int yPersonnage) {
        this.yPersonnage = yPersonnage;
    }
    public Image getPersonnageLeft() {
        return personnageLeft;
    }
    public void setPersonnageLeft(Image personnageLeft) {
        this.personnageLeft = personnageLeft;
    }
    public Image getPersonnageRight() {
        return personnageRight;
    }
    public void setPersonnageRight(Image personnageRight) {
        this.personnageRight = personnageRight;
    }
    public Image getPersonnageAttackLeft() {
        return personnageAttackLeft;
    }
    public void setPersonnageAttackLeft(Image personnageAttackLeft) {
        this.personnageAttackLeft = personnageAttackLeft;
    }
    public Image getPersonnageAttackRight() {
        return personnageAttackRight;
    }
    public void setPersonnageAttackRight(Image personnageAttackRight) {
        this.personnageAttackRight = personnageAttackRight;
    }
    public Rectangle getRectanglePersonnage() {
        return rectanglePersonnage;
    }
    public void setRectanglePersonnage(Rectangle rectanglePersonnage) {
        this.rectanglePersonnage = rectanglePersonnage;
    }


    public void attack(Boolean changeDirection){
        if(changeDirection){
            rectangleEpee.setLocation(xPersonnage - 55, yPersonnage + 33);

        }else{
            rectangleEpee.setLocation(xPersonnage +55, yPersonnage + 33);

        }
    }
    public void supprimerAttack(){
        rectangleEpee.setLocation(1000,1000);
    }

    public Rectangle getRectangleEpee() {
        return rectangleEpee;
    }
    public void setRectangleEpee(Rectangle rectangleEpee) {
        this.rectangleEpee = rectangleEpee;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

}
