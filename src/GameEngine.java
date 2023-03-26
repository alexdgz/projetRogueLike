import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class GameEngine extends JPanel implements KeyListener, Runnable {
    private static GameEngine instance = null;
    private int x = 0; // Position initiale sur l'axe X
    private int y = 0; // Position initiale sur l'axe Y
    private int vitesse = 10; // Vitesse de déplacement
    private double vitesseGhost = 1;
    private Image personnageLeft;
    private Image personnageRight;
    private Image fond;
    private Image imageEnnemi;
    private Boolean changementCote = false;
    private Rectangle rectanglePersonnage, rectangleEnnemi;
    private int xPersonnage, yPersonnage, xEnnemi, yEnnemi;

    private GameEngine() {
        JFrame fenetre = new JFrame();
        fenetre.addKeyListener(this);
        fenetre.add(this);
        fenetre.setSize(715, 650);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
        xPersonnage = 100;
        yPersonnage = 100;
        xEnnemi = 200;
        yEnnemi = 200;

        // Charge l'image du personnage depuis un fichier local
        try {
            personnageLeft = ImageIO.read(getClass().getResourceAsStream("knight1.png"));
            personnageRight = ImageIO.read(getClass().getResourceAsStream("knight2.png"));
            fond = ImageIO.read(getClass().getResourceAsStream("fond.png"));
            imageEnnemi = ImageIO.read(getClass().getResourceAsStream("ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rectanglePersonnage = new Rectangle(xPersonnage, yPersonnage, personnageLeft.getWidth(null), personnageLeft.getHeight(null));
        rectangleEnnemi = new Rectangle(xEnnemi, yEnnemi, imageEnnemi.getWidth(null), imageEnnemi.getHeight(null));

        Thread t = new Thread(this);
        t.start();
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fond, 0, 0, this);
        if (changementCote == true) {
            g.drawImage(personnageRight, xPersonnage, yPersonnage, null); // Dessine l'image du personnage à la position (x, y)
        } else {
            g.drawImage(personnageLeft, xPersonnage, yPersonnage, null); // Dessine l'image du personnage à la position (x, y)
        }
        g.drawImage(imageEnnemi, xEnnemi, yEnnemi, null); // Dessine l'image de l'ennemi à la position (xEnnemi, yEnnemi)

    }

    public void detecterCollision() {
        if (rectanglePersonnage.intersects(rectangleEnnemi)) {
            int dx = xPersonnage - xEnnemi;
            int dy = yPersonnage - yEnnemi;

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    System.out.println("Collision côté gauche !");
                    xEnnemi -= 50;
                } else {
                    System.out.println("Collision côté droit !");
                    xEnnemi += 50;
                }
            } else {
                if (dy > 0) {
                    System.out.println("Collision côté haut !");
                    yEnnemi -= 50;
                } else {
                    System.out.println("Collision côté bas !");
                    yEnnemi += 50;
                }
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        // Déplace le personnage en fonction de la touche de clavier pressée
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP:
                yPersonnage -= vitesse;
                rectanglePersonnage.y -= vitesse;
                break;
            case KeyEvent.VK_DOWN:
                yPersonnage += vitesse;
                rectanglePersonnage.y += vitesse;
                break;
            case KeyEvent.VK_LEFT:
                xPersonnage -= vitesse;
                rectanglePersonnage.x -= vitesse;
                break;
            case KeyEvent.VK_RIGHT:
                xPersonnage += vitesse;
                rectanglePersonnage.x += vitesse;
                break;
        }

        // Met à jour le rectangle pour le personnage
        rectanglePersonnage.setBounds(xPersonnage, yPersonnage, personnageLeft.getWidth(null), personnageLeft.getHeight(null));

        // Détecte les collisions entre le personnage et l'ennemi
        detecterCollision();

        // Redessine le panneau
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            // Déplace l'ennemi en direction du personnage
            if (xEnnemi < xPersonnage) {
                xEnnemi += vitesseGhost;
            } else {
                xEnnemi -= vitesseGhost;
            }

            if (yEnnemi < yPersonnage) {
                yEnnemi += vitesseGhost;
            } else {
                yEnnemi -= vitesseGhost;
            }

            // Met à jour les rectangles pour le personnage et l'ennemi
            rectanglePersonnage.setBounds(xPersonnage, yPersonnage, personnageLeft.getWidth(null), personnageLeft.getHeight(null));
            rectangleEnnemi.setBounds(xEnnemi, yEnnemi, imageEnnemi.getWidth(null), imageEnnemi.getHeight(null));

            // Détecte les collisions entre le personnage et l'ennemi
            detecterCollision();

            // Redessine le panneau
            repaint();

            // Pause pour rafraîchir l'affichage
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {}
        }

    }
}
