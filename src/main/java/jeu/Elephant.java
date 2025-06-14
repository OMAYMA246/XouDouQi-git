package jeu;

public class Elephant extends Animal {
    public Elephant(int x, int y, int joueur) {
        super("ELEPHANT", 8, x, y, joueur); // force = 8
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        int dx = Math.abs(X - x), dy = Math.abs(Y - y);

        // Déplacement doit être d'une case horizontale ou verticale
        if (dx + dy != 1) return false;

        // L'éléphant ne peut pas entrer dans l'eau
        if (plateau[X][Y].equals("~")) return false;

        // L'éléphant ne peut pas entrer dans son propre sanctuaire
        if ((joueur == 1 && X == 0 && Y == 3) || (joueur == 2 && X == 8 && Y == 3)) {
            return false;
        }



        return true;
    }


    @Override
    public boolean peutCapturer(Animal cible, String[][] plateau) {
        if (cible.getJoueur() == this.joueur) return false;
        String caseCible = plateau[cible.getX()][cible.getY()];

        if (caseCible.startsWith("Piege") && cible.getJoueur() != this.joueur) {
            // Une pièce dans un piège adverse peut être capturée par n’importe qui
            return true;
        }

        // Exception : le Rat peut capturer l'éléphant, mais pas l'inverse
        if (cible instanceof Rat) return false;

        return this.force >= cible.getForce();
    }
}
