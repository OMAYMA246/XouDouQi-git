package jeu;

public class Rat extends Animal {
    public Rat(int x, int y, int joueur) {
        super("RAT", 1, x, y, joueur);
    }

    @Override
    public boolean peutSeDeplacerVers(int X, int Y, String[][] plateau) {
        int dx = Math.abs(X - x), dy = Math.abs(Y - y);
        if (dx + dy != 1) return false;

        // Interdire d'entrer dans son propre sanctuaire
        if ((joueur == 1 && X == 0 && Y == 3) || (joueur == 2 && X == 8 && Y == 3)) {
            return false;
        }

        return true; // Le rat peut aller dans l’eau et dans le sanctuaire adverse
    }


    @Override
    public boolean peutCapturer(Animal cible, String[][] plateau) {
        if (cible.getJoueur() == joueur) return false;

        String caseCible = plateau[cible.getX()][cible.getY()];
        String caseActuelle = plateau[x][y];

        if (caseActuelle.equals("~") && !caseCible.equals("~"))
            return false; // Rat ne peut pas capturer directement en sortant de l’eau

        if (cible instanceof Elephant) return true;

        if (caseCible.equals("#") && cible.getJoueur() != joueur) return true;

        return force >= cible.getForce();
    }

    private boolean estSanctuaire(String s) {
        return (joueur == 1 && s.equals("@") && x < 5) ||
                (joueur == 2 && s.equals("@") && x > 4);
    }
}
