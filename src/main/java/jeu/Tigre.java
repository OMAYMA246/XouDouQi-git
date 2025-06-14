package jeu;

public class Tigre extends Animal {
    public Tigre(int x, int y, int joueur) {
        super("TIGRE", 6, x, y, joueur);
    }
    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        // Interdire l'entrée dans son propre sanctuaire
        if ((joueur == 1 && X == 0 && Y == 3) || (joueur == 2 && X == 8 && Y == 3)) {
            return false;
        }

        // Autoriser le saut s'il est possible
        if (sautPossible(X, Y, plateau)) return true;

        // Sinon, vérifier le déplacement classique
        return deplacementClassique(X, Y, plateau);
    }


    @Override
    public boolean peutCapturer(Animal cible, String[][] plateau) {
        return captureClassique(cible, plateau);
    }

    private boolean sautPossible(int X, int Y, String[][] plateau) {
        if (x == X && y != Y && (Math.abs(Y - y) > 1)) {
            int min = Math.min(y, Y), max = Math.max(y, Y);
            for (int j = min + 1; j < max; j++)
                if (!plateau[x][j].equals("~") || animalSurCase(x, j)) return false;
            return true;
        }
        if (y == Y && x != X && (Math.abs(X - x) > 1)) {
            int min = Math.min(x, X), max = Math.max(x, X);
            for (int i = min + 1; i < max; i++)
                if (!plateau[i][y].equals("~") || animalSurCase(i, y)) return false;
            return true;
        }
        return false;
    }

    private boolean animalSurCase(int x, int y) {
        // à implémenter dans le contrôleur global du jeu si nécessaire
        return false;
    }
}
