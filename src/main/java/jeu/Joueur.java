package jeu;

public class Joueur {
    private String nom;
    private int id;
    private int score;
    String motdepasse;

    public Joueur(int id, String nom, String motdepasse) {
        this.id = id;
        this.nom = nom;
        this.motdepasse = motdepasse;
        this.score = 0;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getNumero() {
        return motdepasse;
    }




    @Override
    public String toString() {
        return "Joueur " + motdepasse + " : " + nom + " (Score : " + score + ")";
    }
}
