import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class GameEngine extends JPanel implements KeyListener, Runnable {
    private static GameEngine instance = null;
    private Image vie1, vie2, vie3, game_over, fond;
    private Boolean changementCote = false;
    private Boolean attack = false;
    private Rectangle rectangleFenetreGauche, rectangleFenetreDroite, rectangleFenetreHaut, rectangleFenetreBas;
    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Enemy slime_1, slime_2, fantome_1, fantome_2;

    private ScoreBoard scoreBoard;
    private GameEngine() {
        scoreBoard = new ScoreBoard(); // On créé le scoreBoard
        player = Player.getInstance(); // On créé le joueur
        JFrame fenetre = new JFrame(); // On créé la fenetre
        fenetre.addKeyListener(this); // On ajoute le listener
        fenetre.add(this); // On ajoute le panel
        fenetre.setSize(715, 605); // On définit la taille de la fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit a la fenetre de se fermer quand on clique sur la croix
        fenetre.setVisible(true); // On rend la fenetre visible


        slime_1 = EnemyFactory.createEnemy("slime"); // On créé un slime
        slime_2 = EnemyFactory.createEnemy("slime");
        fantome_1 = EnemyFactory.createEnemy("fantome");
        fantome_2 = EnemyFactory.createEnemy("fantome");


        enemies.add(slime_1); // On ajoute les slimes et les fantomes a la liste des ennemis
        enemies.add(slime_2);

        enemies.add(fantome_1);
        enemies.add(fantome_2);


        try {
            fond = ImageIO.read(getClass().getResourceAsStream("fond.png")); // On va récupérer les images
            vie1 = ImageIO.read(getClass().getResourceAsStream("coeur1.png"));
            vie2 = ImageIO.read(getClass().getResourceAsStream("coeur2.png"));
            vie3 = ImageIO.read(getClass().getResourceAsStream("coeur3.png"));
            game_over = ImageIO.read(getClass().getResourceAsStream("game_over.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        rectangleFenetreGauche = new Rectangle(0, 0, 1, 605); // On créé les rectangles qui vont servir a detecter les collisions avec les bords de la fenetre
        rectangleFenetreDroite = new Rectangle(714, 0, 1, 605);
        rectangleFenetreHaut = new Rectangle(0, 0, 715, 1);
        rectangleFenetreBas = new Rectangle(0, 575, 715, 1);


        Thread t = new Thread(this); // On créé un thread
        t.start(); // On lance le thread
    }

    public static GameEngine getInstance() { // On créé une instance de GameEngine
        if (instance == null) { // Si l'instance n'existe pas
            instance = new GameEngine(); // On créé une nouvelle instance
        } // Sinon on retourne l'instance existante
        return instance; // On retourne l'instance
    }

    public void paintComponent(Graphics g) { // On dessine les images
        super.paintComponent(g); // On appelle la méthode paintComponent de la classe mère
        if (player.getHealth() >0 ){ // Si le joueur a encore de la vie
            g.drawImage(fond, 0, 0, this); // Dessine l'image du fond à la position (0, 0)
            if (changementCote == true) { // Si le joueur se déplace vers la droite
                if (attack == true){ // Si le joueur attaque
                    g.drawImage(player.getPersonnageAttackRight(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }else {
                    g.drawImage(player.getPersonnageRight(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)

                }
            } else {
                if (attack == true) { // Si le joueur attaque
                    g.drawImage(player.getPersonnageAttackLeft(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }else {
                    g.drawImage(player.getPersonnageLeft(), player.getxPersonnage(), player.getyPersonnage(), null); // Dessine l'image du personnage à la position (x, y)
                }
            }

            for (Enemy enemy : enemies) { // Pour chaque ennemi dans la liste des ennemis
                g.drawImage(enemy.getSprite(), enemy.getXEnemy(), enemy.getYEnemy(), null); // Dessine l'image de l'ennemi à la position (xEnnemi, yEnnemi)
            }


            if (player.getHealth() == 3) { // Si le joueur a 3 coeurs
                g.drawImage(vie3, 0, 0, null);  // Dessine l'image du coeur à la position (0, 0)
            } else if (player.getHealth() == 2) {
                g.drawImage(vie2, 0, 0, null);
            } else if (player.getHealth() == 1) {
                g.drawImage(vie1, 0, 0, null);
            }
            // Dessine le score
            g.setColor(Color.WHITE  ); // On définit la couleur du texte
            g.setFont(new Font("Arial", Font.BOLD, 20)); // On définit la police du texte
            String scoreString = "Score: " + scoreBoard.getScore(); // On créé le texte
            g.drawString(scoreString, getWidth() - g.getFontMetrics().stringWidth(scoreString) - 10, 30); // On dessine le texte


        }else {
            g.drawImage(game_over, 0, 0, null); // Dessine l'image de game over à la position (0, 0)
        }


    }

    public void detecterCollision() { // On detecte les collisions
        for (Enemy ennemi : enemies) { // Pour chaque ennemi dans la liste des ennemis
            if (player.getRectanglePersonnage().intersects(ennemi.getHitbox())) { // Si le joueur entre en collision avec l'ennemi
                int dx = player.getxPersonnage() - ennemi.getXEnemy(); // On récupère la distance entre le joueur et l'ennemi sur l'axe des x
                int dy = player.getyPersonnage() - ennemi.getYEnemy(); // On récupère la distance entre le joueur et l'ennemi sur l'axe des y
                player.setHealth(player.getHealth() - ennemi.attack()); // On enlève de la vie au joueur
                if (Math.abs(dx) > Math.abs(dy)) { // Si la distance sur l'axe des x est plus grande que la distance sur l'axe des y
                    if (dx > 0) { // Si la distance sur l'axe des x est positive
                        ennemi.setXEnemy(ennemi.getXEnemy() - 50); // On déplace l'ennemi vers la gauche
                    } else {
                        ennemi.setXEnemy(ennemi.getXEnemy() + 50); // On déplace l'ennemi vers la droite
                    }
                } else {
                    if (dy > 0) { // Si la distance sur l'axe des y est positive
                        ennemi.setYEnemy(ennemi.getYEnemy() - 50); // On déplace l'ennemi vers le haut
                    } else {
                        ennemi.setYEnemy(ennemi.getYEnemy() + 50); // On déplace l'ennemi vers le bas
                    }
                }
            }

            if (player.getRectangleEpee().intersects(ennemi.getHitbox())) { // Si l'épée du joueur entre en collision avec l'ennemi
                ennemi.setXEnemy((int) (Math.random() * (1200 - 500)) + 1200); // On déplace l'ennemi hors de l'écran

                ennemi.setYEnemy((int) (Math.random() * (1200 - 500)) + 1200); // On déplace l'ennemi hors de l'écran
                scoreBoard.onEnemyKilled(ennemi); // On ajoute des points au score via l'observer scoreBoard
            }
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreGauche)) { // Si le joueur entre en collision avec la fenêtre de gauche
            player.setxPersonnage(player.getxPersonnage() + 20); // On déplace le joueur vers la droite
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreDroite)) { // Si le joueur entre en collision avec la fenêtre de droite
            player.setxPersonnage(player.getxPersonnage() - 20); // On déplace le joueur vers la gauche
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreHaut)) { // Si le joueur entre en collision avec la fenêtre du haut
            player.setyPersonnage(player.getyPersonnage() + 20); // On déplace le joueur vers le bas
        }
        if (player.getRectanglePersonnage().intersects(rectangleFenetreBas)) { // Si le joueur entre en collision avec la fenêtre du bas
            player.setyPersonnage(player.getyPersonnage() - 20); // On déplace le joueur vers le haut
        }

    }

    public void keyPressed(KeyEvent e) {
        // Déplace le personnage en fonction de la touche de clavier pressée
        int code = e.getKeyCode(); // On récupère le code de la touche pressée
        switch (code) {
            case KeyEvent.VK_SPACE:
                attack = true; // On définit l'attaque du joueur à true
                player.attack(changementCote); // On lance l'attaque du joueur
                break;
            case KeyEvent.VK_UP:
                player.moveUp(); // On déplace le joueur vers le haut
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown(); // On déplace le joueur vers le bas
                break;
            case KeyEvent.VK_LEFT:
                changementCote = true; // On définit le changement de côté à true
                player.moveLeft(); // On déplace le joueur vers la gauche
                break;
            case KeyEvent.VK_RIGHT:
                changementCote = false; // On définit le changement de côté à false
                player.moveRight(); // On déplace le joueur vers la droite
                break;
        }

        // Détecte les collisions entre le personnage et l'ennemi
        detecterCollision();

        // Redessine le panneau
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) { // Quand une touche est relâchée
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Si la touche relâchée est la touche espace
            attack = false; // On définit l'attaque du joueur à false
            player.supprimerAttack(); // On supprime l'attaque du joueur
        }
    }

    @Override
    public void run() { // On lance le thread
        while (true) {
            // Déplace l'ennemi en direction du personnage
            for (Enemy ennemi: enemies) { // Pour chaque ennemi dans la liste des ennemis
                if (ennemi.getXEnemy() < player.getxPersonnage()) { // Si l'ennemi est à gauche du joueur
                    ennemi.setXEnemy(ennemi.getXEnemy() + ennemi.getSpeed()); // On déplace l'ennemi vers la droite
                } else {
                    ennemi.setXEnemy(ennemi.getXEnemy() - ennemi.getSpeed()); // On déplace l'ennemi vers la gauche
                }

                if (ennemi.getYEnemy()< player.getyPersonnage()) {  // Si l'ennemi est en haut du joueur
                    ennemi.setYEnemy(ennemi.getYEnemy() + ennemi.getSpeed());  // On déplace l'ennemi vers le bas
                } else {
                    ennemi.setYEnemy(ennemi.getYEnemy() - ennemi.getSpeed()); // On déplace l'ennemi vers le haut
                }

            }



            // Met à jour les rectangles pour le personnage et l'ennemi
            player.getRectanglePersonnage().setBounds(player.getxPersonnage(), player.getyPersonnage(), player.getPersonnageLeft().getWidth(null), player.getPersonnageLeft().getHeight(null));
            for (Enemy ennemi: enemies) {
                ennemi.getHitbox().setBounds(ennemi.getXEnemy(), ennemi.getYEnemy(), ennemi.getSprite().getWidth(null), ennemi.getSprite().getHeight(null));
            }


            // Détecte les collisions entre le personnage et l'ennemi
            detecterCollision();

            // Redessine le panneau
            repaint();

            // Pause pour rafraîchir l'affichage
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {}

            if (player.getHealth() == 0) { // Si la vie du joueur est égale à 0
                try {
                    Thread.sleep(5000); // On attend 5 secondes
                    System.exit(0); // On ferme le jeu
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}
