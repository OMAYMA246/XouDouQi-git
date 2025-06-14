package jeu;

public class Chat extends Animal {
    public Chat(int x, int y, int joueur) {
        super("CHAT", 2, x, y, joueur);
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        // Interdire l'entrée dans son propre sanctuaire
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
