import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class GameEngine extends JPanel implements KeyListener {
    private static GameEngine instance = null;
    private int x = 0; // Position initiale sur l'axe X
    private int y = 0; // Position initiale sur l'axe Y
    private int vitesse = 10; // Vitesse de déplacement
    private Image image; // Image du personnage
    private GameEngine() {
        JFrame fenetre = new JFrame();
        fenetre.addKeyListener(this);
        fenetre.add(this);
        fenetre.setSize(400, 400);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);

        // Charge l'image du personnage depuis un fichier local
        try {
            image = ImageIO.read(getClass().getResourceAsStream("knight1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, null); // Dessine l'image du personnage à la position (x, y)
    }

    public void deplacer(int dx, int dy) {
        x += dx;
        y += dy;
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            deplacer(-vitesse, 0); // Déplace le personnage vers la gauche
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            deplacer(vitesse, 0); // Déplace le personnage vers la droite
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            deplacer(0, -vitesse); // Déplace le personnage vers le haut
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            deplacer(0, vitesse); // Déplace le personnage vers le bas
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}
