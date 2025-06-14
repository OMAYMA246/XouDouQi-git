package jeu;

public class Panthere extends Animal {
    public Panthere(int x, int y, int joueur) {
        super("PANTHERE", 5, x, y, joueur);
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        // Interdire l'entrée dans le sanctuaire de son propre joueur
        if ((joueur == 1 && X == 0 && Y == 3) || (joueur == 2 && X == 8 && Y == 3)) {
            return false;
        }
        return deplacementClassique(X, Y, plateau);
    }


    @Override
    public boolean peutCapturer(Animal cible, String[][] plateau) {
        return captureClassique(cible, plateau);
    }
}
