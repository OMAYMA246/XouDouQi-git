package jeu;

public abstract class Animal {
    protected String nom;
    protected int force;
    protected int x;  // ligne sur le plateau
    protected int y;  // colonne sur le plateau
    protected int joueur; // 1 ou 2

    public Animal(String nom, int force, int x, int y, int joueur) {
        this.nom = nom;
        this.force = force;
        this.x = x;
        this.y = y;
        this.joueur = joueur;
    }

    public String getNom() {
        return nom;
    }

    public int getForce() {
        return force;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getJoueur() {
        return joueur;
    }

    public void deplacer(int X, int Y) {
        this.x = X;
        this.y = Y;
    }

    // À implémenter dans les sous-classes selon les règles spécifiques
    public abstract boolean peutSeDeplacerVers(int X, int Y, String[][] plateau);
    public abstract boolean peutCapturer(Animal cible, String[][] plateau);
    // Classe Animal (ajoute ceci à la fin de ta classe)
    protected boolean deplacementClassique(int X, int Y, String[][] plateau) {
        int dx = Math.abs(X - x);
        int dy = Math.abs(Y - y);

        // Déplacement d'une case uniquement (pas diagonale)
        if (dx + dy != 1) return false;

        String destination = plateau[X][Y];

        // Interdiction d’entrer dans son propre sanctuaire
        if (joueur == 1 && destination.equals("@") && X == 0 && Y == 3) return false;
        if (joueur == 2 && destination.equals("@") && X == 8 && Y == 3) return false;

        // Par défaut, seules les rivières sont interdites (sauf pour le Rat)
        if (destination.equals("~")) return false;

        return true;
    }

    protected boolean captureClassique(Animal cible, String[][] plateau) {
        // On ne peut pas capturer un animal allié
        if (cible.getJoueur() == this.joueur) return false;

        String caseCible = plateau[cible.getX()][cible.getY()];

        // Si la cible est dans un piège adverse → elle est vulnérable
        if (caseCible.equals("#") && cible.getJoueur() != this.joueur) {
            return true; // peut capturer quelle que soit la force
        }

        // Sinon, on compare les forces
        return this.force >= cible.getForce();
    }

}
