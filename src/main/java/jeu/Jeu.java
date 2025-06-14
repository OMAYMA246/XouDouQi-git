package jeu;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {
    private Plateau plateau;
    private ArrayList<Animal> animaux;
    private int joueurActuel;
    private String nomJoueur1;
    private String nomJoueur2;
    private DatabaseConnection db;

    public static final String RESET = "\u001B[0m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String GREEN   = "\u001B[32m";
    public static final String RED     = "\u001B[31m";
    public static final String PURPLE  = "\u001B[35m";  // Violet (magenta)
    public static final String BROWN   = "\u001B[33m";  // Jaune foncé / marron
    public Jeu(String nomJoueur1, String nomJoueur2, DatabaseConnection db) {
        this.nomJoueur1 = nomJoueur1;
        this.nomJoueur2 = nomJoueur2;
        this.db = db;  // Sauvegarde la connexion DB
        plateau = new Plateau();
        animaux = new ArrayList<>();
        joueurActuel = 1;
        initialiserAnimaux();
    }

    private void initialiserAnimaux() {
        // Joueur 1
        animaux.add(new Lion(0, 0, 1));       plateau.setCase(0, 0, "L1");
        animaux.add(new Tigre(0, 6, 1));      plateau.setCase(0, 6, "T1");
        animaux.add(new Chien(1, 1, 1));      plateau.setCase(1, 1, "C1");
        animaux.add(new Chat(1, 5, 1));       plateau.setCase(1, 5, "A1");
        animaux.add(new Rat(2, 0, 1));        plateau.setCase(2, 0, "R1");
        animaux.add(new Panthere(2, 2, 1));   plateau.setCase(2, 2, "P1");
        animaux.add(new Loup(2, 4, 1));       plateau.setCase(2, 4, "W1");
        animaux.add(new Elephant(2, 6, 1));   plateau.setCase(2, 6, "E1");

        // Joueur 2
        animaux.add(new Lion(8, 6, 2));       plateau.setCase(8, 6, "L2");
        animaux.add(new Tigre(8, 0, 2));      plateau.setCase(8, 0, "T2");
        animaux.add(new Chien(7, 5, 2));      plateau.setCase(7, 5, "C2");
        animaux.add(new Chat(7, 1, 2));       plateau.setCase(7, 1, "A2");
        animaux.add(new Rat(6, 6, 2));        plateau.setCase(6, 6, "R2");
        animaux.add(new Panthere(6, 4, 2));   plateau.setCase(6, 4, "P2");
        animaux.add(new Loup(6, 2, 2));       plateau.setCase(6, 2, "W2");
        animaux.add(new Elephant(6, 0, 2));   plateau.setCase(6, 0, "E2");
    }

    private boolean peutCapturerSelonForce(Animal attaquant, Animal cible) {
        // Règle spéciale Rat peut capturer Elephant
        if (attaquant instanceof Rat && cible instanceof Elephant) {
            return true;
        }
        // Elephant ne peut pas capturer Rat (règle du jeu)
        if (attaquant instanceof Elephant && cible instanceof Rat) {
            return false;
        }
        // Capture classique : attaquant doit avoir force >= cible
        return attaquant.getForce() >= cible.getForce();
    }

    public void jouer() {
        boolean jeuActif = true;
        Scanner scanner = new Scanner(System.in);

        while (jeuActif) {
            plateau.afficher();

            String nomCourant = (joueurActuel == 1) ? nomJoueur1 : nomJoueur2;
            String couleur = (joueurActuel == 1) ? BROWN : PURPLE;
            System.out.println("\n" + couleur + nomCourant + RESET + ", entrez vos coordonnées :");

            System.out.print("Animal x y : ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            Animal aDeplacer = trouverAnimal(x, y);

            if (aDeplacer == null || aDeplacer.getJoueur() != joueurActuel) {
                System.out.println(RED + "Aucun animal à cette position ou ce n'est pas votre animal." + RESET);
                continue;
            }

            System.out.print("Nouvelle position x y : ");
            int nx = scanner.nextInt();
            int ny = scanner.nextInt();

            Animal cible = trouverAnimal(nx, ny);

            if (aDeplacer.peutSeDeplacerVers(nx, ny, plateau.getGrille())) {
                if (cible != null) {
                    if (cible.getJoueur() == joueurActuel) {
                        System.out.println(RED+"Vous ne pouvez pas capturer un de vos propres animaux !"+RESET);
                        continue;
                    }
                    if (!peutCapturerSelonForce(aDeplacer, cible)) {
                        System.out.println(RED+"Capture invalide selon la hiérarchie des forces."+RESET);
                        continue;
                    }
                    System.out.println(GREEN+"Capture réussie !"+RESET);
                    animaux.remove(cible);
                }

                plateau.restaurerCase(aDeplacer.getX(), aDeplacer.getY());
                aDeplacer.deplacer(nx, ny);
                plateau.setCase(nx, ny, getSymbole(aDeplacer));

                if (estDansSanctuaireAdverse(aDeplacer)) {
                    String msg = " " + nomCourant + " a gagné en atteignant le sanctuaire ennemi ! ";
                    String ligne = "═".repeat(msg.length());

                    String cadre = MAGENTA
                            + "╔" + ligne + "╗\n"
                            + "║" + msg + "║\n"
                            + "╚" + ligne + "╝"
                            + RESET;

                    System.out.println(cadre);
                    enregistrerPartie(nomCourant, 1);
                    try {
                        int ancienScore = db.getScore(nomCourant);
                        db.mettreAJourScore(nomCourant, ancienScore + 1);
                    } catch (SQLException e) {
                        System.out.println(RED+"Erreur lors de la mise à jour du score : " + e.getMessage()+RESET);
                    }

                    jeuActif = false;
                    break;
                }

                if (plusDAnimauxPour(3 - joueurActuel)) {
                    String msg = " " + nomCourant + " a gagné car l'adversaire n'a plus d'animaux ! ";
                    String ligne = "═".repeat(msg.length());

                    String cadre = MAGENTA
                            + "╔" + ligne + "╗\n"
                            + "║" + msg + "║\n"
                            + "╚" + ligne + "╝"
                            + RESET;

                    System.out.println(cadre);
                    enregistrerPartie(nomCourant, 1);
                    try {
                        int ancienScore = db.getScore(nomCourant);
                        db.mettreAJourScore(nomCourant, ancienScore + 1);
                    } catch (SQLException e) {
                        System.out.println(RED+"Erreur lors de la mise à jour du score : " + e.getMessage()+RESET);
                    }

                    jeuActif = false;
                    break;
                }

                joueurActuel = 3 - joueurActuel;
            } else {
                System.out.println(RED+"Déplacement invalide."+RESET);
            }
        }
    }

    private Animal trouverAnimal(int x, int y) {
        for (Animal a : animaux) {
            if (a.getX() == x && a.getY() == y) {
                return a;
            }
        }
        return null;
    }

    private String getSymbole(Animal a) {
        String nomCourt = a.getNom().substring(0, 1).toUpperCase();
        return nomCourt + a.getJoueur();
    }

    private boolean estDansSanctuaireAdverse(Animal a) {
        int x = a.getX();
        int y = a.getY();
        int joueur = a.getJoueur();

        if (joueur == 1 && x == 8 && y == 3) {
            return true;
        }
        if (joueur == 2 && x == 0 && y == 3) {
            return true;
        }
        return false;
    }

    private boolean plusDAnimauxPour(int joueur) {
        for (Animal a : animaux) {
            if (a.getJoueur() == joueur) {
                return false;
            }
        }
        return true;
    }
    private void enregistrerPartie(String gagnant, int score) {
        try {
            String sql = "INSERT INTO parties (joueur1, joueur2, gagnant, score, datejeu) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP())";
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, nomJoueur1);
            stmt.setString(2, nomJoueur2);
            stmt.setString(3, gagnant);
            stmt.setInt(4, score);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
