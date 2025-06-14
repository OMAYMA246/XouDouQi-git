package jeu;

public class Chien extends Animal {
    public Chien(int x, int y, int joueur) {
        super("CHIEN", 4, x, y, joueur);
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        // Empêcher d’entrer dans son propre sanctuaire
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
