package jeu;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Authentification {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static Joueur authentifierJoueur(DatabaseConnection db, Scanner scanner, String labelJoueur) throws SQLException {
        while (true) {
            System.out.print(BLUE+"[" + labelJoueur + "] Voulez-vous:\n \t(1)s'inscrire \n \t(2) se connecter ? : "+RESET);
            String choix = scanner.nextLine().trim();

            if (choix.equals("1")) {
                // Inscription
                System.out.print(BLUE+"Entrez votre nom : "+RESET);
                String nom = scanner.nextLine();
                System.out.print(BLUE+"Entrez votre mot de passe : "+RESET);
                String mdp = scanner.nextLine();

                if (db.joueurExiste(nom)) {
                    System.out.println(RED+"Ce nom est déjà pris, veuillez en choisir un autre."+RESET);
                } else {
                    db.insertPlayer(nom, mdp, 0, Timestamp.valueOf(LocalDateTime.now()));
                    int id = db.getJoueurId(nom);
                    System.out.println(GREEN+"Inscription réussie !"+RESET);
                    return new Joueur(id, nom, mdp);
                }

            } else if (choix.equals("2")) {
                // Connexion
                System.out.print(BLUE+"Entrez votre nom : "+RESET);
                String nom = scanner.nextLine();
                System.out.print(BLUE+"Entrez votre mot de passe : "+RESET);
                String mdp = scanner.nextLine();

                if (db.verifierConnexion(nom, mdp)) {
                    int id = db.getJoueurId(nom);
                    System.out.println(GREEN +"Connexion réussie !"+RESET);
                    return new Joueur(id, nom, mdp);
                } else {
                    System.out.println(RED+"Nom ou mot de passe incorrect."+RESET);
                }

            } else {
                System.out.println(RED+"Choix invalide, veuillez entrer 1 ou 2."+RESET);
            }
        }
    }
}
