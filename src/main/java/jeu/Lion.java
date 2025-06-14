package jeu;

public class Lion extends Animal {
    public Lion(int x, int y, int joueur) {
        super("LION", 7, x, y, joueur);
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        // Empêche d'entrer dans son propre sanctuaire
        if ((joueur == 1 && X == 0 && Y == 3) || (joueur == 2 && X == 8 && Y == 3)) {
            return false;
        }

        if (sautPossible(X, Y, plateau)) return true;
        return deplacementClassique(X, Y, plateau);
    }


    @Override
    public boolean peutCapturer(Animal cible, String[][] plateau) {
        return captureClassique(cible, plateau);
    }

    private boolean sautPossible(int X, int Y, String[][] plateau) {
        // même que Tigre
        return new Tigre(x, y, joueur).peutSeDeplacerVers(X, Y, plateau);
    }
}
