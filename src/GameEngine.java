import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class GameEngine extends JPanel implements KeyListener, Runnable {
    private static GameEngine instance = null;
    private Image vie1, vie2, vie3, game_over, fond;
    private double vitesseGhost = 1;
    private Image imageEnnemi;
    private Boolean changementCote = false;
    private Boolean attack = false;
    private Rectangle rectangleEnnemi, rectangleFenetreGauche, rectangleFenetreDroite, rectangleFenetreHaut, rectangleFenetreBas;
    private int xEnnemi, yEnnemi;
    private Player player;

    private Graphics graphics;


    private Enemy slime_1 ;
    private GameEngine() {
        player = Player.getInstance();
        JFrame fenetre = new JFrame();
        fenetre.addKeyListener(this);
        fenetre.add(this);
        fenetre.setSize(715, 605);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);


        slime_1 = EnemyFactory.createEnemy("slime");
        slime_1.getHitbox().setLocation(100, 100);

        try {
            fond = ImageIO.read(getClass().getResourceAsStream("fond.png"));
            imageEnnemi = ImageIO.read(getClass().getResourceAsStream("ghost.png"));
            vie1 = ImageIO.read(getClass().getResourceAsStream("coeur1.png"));
            vie2 = ImageIO.read(getClass().getResourceAsStream("coeur2.png"));
            vie3 = ImageIO.read(getClass().getResourceAsStream("coeur3.png"));
            game_over = ImageIO.read(getClass().getResourceAsStream("game_over.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        rectangleFenetreGauche = new Rectangle(0, 0, 1, 605);
        rectangleFenetreDroite = new Rectangle(714, 0, 1, 605);
        rectangleFenetreHaut = new Rectangle(0, 0, 715, 1);
        rectangleFenetreBas = new Rectangle(0, 575, 715, 1);
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
        if (player.getHealth() >0 ){
            g.drawImage(fond, 0, 0, this);
            if (changementCote == true) {
                if (attack == true){
                    g.drawImage(player.getPersonnageAttackRight(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }else {
                    g.drawImage(player.getPersonnageRight(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)

                }
            } else {
                if (attack == true) {
                    g.drawImage(player.getPersonnageAttackLeft(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }else {
                    g.drawImage(player.getPersonnageLeft(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }
            }

            g.drawImage(imageEnnemi, xEnnemi, yEnnemi, null); // Dessine l'image de l'ennemi à la position (xEnnemi, yEnnemi)


            if (player.getHealth() == 3) {
                g.drawImage(vie3, 0, 0, null);
            } else if (player.getHealth() == 2) {
                g.drawImage(vie2, 0, 0, null);
            } else if (player.getHealth() == 1) {
                g.drawImage(vie1, 0, 0, null);
            }
            // Dessine le score
            g.setColor(Color.WHITE  );
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String scoreString = "Score: " + player.getScore();
            g.drawString(scoreString, getWidth() - g.getFontMetrics().stringWidth(scoreString) - 10, 30);

            g.setColor(Color.BLUE);
            g.fillRect(player.getRectangleEpee().x, player.getRectangleEpee().y, player.getRectangleEpee().width, player.getRectangleEpee().height);
        }else {
            g.drawImage(game_over, 0, 0, null);
        }


    }

    public void detecterCollision() {
        if (player.getRectanglePersonnage().intersects(rectangleEnnemi)) {
            int dx = player.getxPersonnage() - xEnnemi;
            int dy = player.getyPersonnage() - yEnnemi;
            player.setHealth(player.getHealth() - 1);
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    xEnnemi -= 50;
                } else {
                    xEnnemi += 50;
                }
            } else {
                if (dy > 0) {
                    yEnnemi -= 50;
                } else {
                    yEnnemi += 50;
                }
            }
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreGauche)) {
            player.setxPersonnage(player.getxPersonnage() + 20);
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreDroite)) {
            player.setxPersonnage(player.getxPersonnage() - 20);
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreHaut)) {
            player.setyPersonnage(player.getyPersonnage() + 20);
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreBas)) {
            player.setyPersonnage(player.getyPersonnage() - 20);
        }

        if (player.getRectangleEpee().intersects(rectangleEnnemi)) {
            xEnnemi = 100;
            yEnnemi = 200;
            player.setScore(player.getScore() + 1);
        }



    }

    public void keyPressed(KeyEvent e) {
        // Déplace le personnage en fonction de la touche de clavier pressée
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SPACE:
                attack = true;
                player.attack(changementCote);
                break;
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                changementCote = true;
                player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                changementCote = false;
                player.moveRight();
                break;

        }


        //rectanglePersonnage.setBounds(xPersonnage, yPersonnage, personnageLeft.getWidth(null), personnageLeft.getHeight(null));

        // Détecte les collisions entre le personnage et l'ennemi
        detecterCollision();

        // Redessine le panneau
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            attack = false;
            player.supprimerAttack();
        }
    }

    @Override
    public void run() {
        while (true) {
            // Déplace l'ennemi en direction du personnage
            if (xEnnemi < player.getxPersonnage()) {
                xEnnemi += vitesseGhost;
            } else {
                xEnnemi -= vitesseGhost;
            }

            if (yEnnemi < player.getyPersonnage()) {
                yEnnemi += vitesseGhost;
            } else {
                yEnnemi -= vitesseGhost;
            }


            // Met à jour les rectangles pour le personnage et l'ennemi
            player.getRectanglePersonnage().setBounds(player.getxPersonnage(), player.getyPersonnage(), player.getPersonnageLeft().getWidth(null), player.getPersonnageLeft().getHeight(null));
            rectangleEnnemi.setBounds(xEnnemi, yEnnemi, imageEnnemi.getWidth(null), imageEnnemi.getHeight(null));

            // Détecte les collisions entre le personnage et l'ennemi
            detecterCollision();

            // Redessine le panneau
            repaint();

            // Pause pour rafraîchir l'affichage
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {}

            if (player.getHealth() == 0) {
                try {
                    Thread.sleep(5000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}
