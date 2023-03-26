public class EnemyFactory {
    public static Enemy createEnemy(String type) {
        if (type.equalsIgnoreCase("fantome")) {
            return new Fantome();
        } else if (type.equalsIgnoreCase("slime")) {
            return new Slime();
        } else {
            throw new IllegalArgumentException("Type d'ennemi inconnu");
        }
    }
}
