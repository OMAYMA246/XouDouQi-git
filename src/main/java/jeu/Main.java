package jeu;

import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String MAGENTA = "\u001B[35m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            DatabaseConnection db = new DatabaseConnection();

            ResultSet rs = db.getAllPlayers();
            System.out.println(YELLOW + "\n=== Historique global des joueurs ===" + RESET);
            while (rs.next()) {
                System.out.println("Nom: " + rs.getString("nom")
                        + ", Score: " + rs.getInt("score")
                        + ", Date: " + rs.getTimestamp("datejeu"));
            }

            Joueur joueur1 = Authentification.authentifierJoueur(db, scanner, "Joueur 1");
            System.out.println(PURPLE + "Bienvenue " + joueur1.getNom() + " !" + RESET);

            Joueur joueur2 = Authentification.authentifierJoueur(db, scanner, "Joueur 2");
            System.out.println(GREEN + "Bienvenue " + joueur2.getNom() + " !" + RESET);

            ResultSet historique = db.getHistoriqueEntreJoueurs(joueur1.getNom(), joueur2.getNom());
            if (!historique.next()) {
                System.out.println(YELLOW + "C’est la première fois que " + joueur1.getNom() +
                        " et " + joueur2.getNom() + " jouent ensemble !" + RESET);
            } else {
                System.out.println(BLUE + "=== Historique des parties entre " +
                        joueur1.getNom() + " et " + joueur2.getNom() + " ===" + RESET);
                do {
                    System.out.println("Date: " + historique.getTimestamp("datejeu") +
                            ", Gagnant: " + historique.getString("gagnant") +
                            ", Score: " + historique.getInt("score"));
                } while (historique.next());
            }


            // Menu principal
            int choix = 0;

            while (true) {
                System.out.println(BLUE + "\n====================" + RESET);
                System.out.println(BLUE + "====   Menu    ====" + RESET);
                System.out.println(BLUE + "====================" + RESET);
                System.out.println(YELLOW + "1. Afficher les règles et symboles du jeu" + RESET);
                System.out.println(YELLOW + "2. Commencer à jouer" + RESET);
                System.out.print(BLUE + "Entrez votre choix (1 ou 2) : " + RESET);

                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    System.out.println(RED + "Entrée invalide, veuillez entrer un nombre." + RESET);
                    continue;
                }

                if (choix == 1) {
                    Info.afficherInfosJeu();
                    String rep;
                    do {
                        System.out.print(BLUE + "Voulez-vous commencer à jouer ? (O/N) : " + RESET);
                        rep = scanner.nextLine().trim().toUpperCase();
                    } while (!rep.equals("O") && !rep.equals("N"));

                    if (rep.equals("O")) break;
                    else continue;
                } else if (choix == 2) {
                    break;
                } else {
                    System.out.println(RED + "Choix invalide, veuillez entrer 1 ou 2." + RESET);
                }
            }

            Jeu jeu = new Jeu(joueur1.getNom(), joueur2.getNom(), db);
            jeu.jouer();

            db.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
