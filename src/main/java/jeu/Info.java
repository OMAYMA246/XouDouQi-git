package jeu;

public class Info {
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String BROWN = "\u001B[33m";   // Marron (jaune foncé)
    public static final String PURPLE = "\u001B[35m";  // Violet (magenta)
    /**
     * Affiche les règles principales du jeu Xou Dou Qi (Jeu de la Jungle) avec couleurs.
     */
    public static void afficherProcedure() {
        System.out.println(BLUE + "============================" + RESET);
        System.out.println(BLUE + "===== Procédure du Jeu =====" + RESET);
        System.out.println(BLUE + "============================" + RESET);
        System.out.println(YELLOW + "1. Initialisation :" + RESET);
        System.out.println("   - Chaque joueur dispose de 8 pièces (Rat, Chat, Chien, Loup, Panthère, Tigre, Lion, Éléphant).\n"
                + "     Elles sont placées aux positions de départ définies sur le plateau.");
        System.out.println(YELLOW + "2. Tour de jeu :" + RESET);
        System.out.println("   - Le joueur 1 commence. À chaque tour, un joueur :\n"
                + "     a) Choisit une de ses pièces et sa position (x, y).\n"
                + "     b) Choisit une nouvelle position adjacente (orthogonalement) ou effectue un saut (Lion/Tigre).\n"
                + "     c) Si la nouvelle case contient une pièce ennemie et que la capture est valide, la pièce adverse est retirée.\n"
                + "     d) Déplacement interdit si la case est hors plateau, son propre sanctuaire, ou non accessible selon la pièce.");
        System.out.println(YELLOW + "3. Conditions de victoire :" + RESET);
        System.out.println("   - Un joueur gagne si :\n"
                + "     * L'une de ses pièces entre dans le sanctuaire adverse (case @ du camp opposé).\n"
                + "     * L'adversaire n'a plus de pièces sur le plateau.");
    }

    /**
     * Affiche la signification des symboles utilisés sur le plateau avec couleurs.
     */
    public static void afficherSymboles() {
        System.out.println(BLUE + "======================================" + RESET);
        System.out.println(BLUE + "===== Signification des Symboles =====" + RESET);
        System.out.println(BLUE + "======================================" + RESET);

        System.out.println(YELLOW + " .    : Case vide" + RESET);
        System.out.println(YELLOW + " ~    : Rivière (accessible uniquement par le Rat, saut au-dessus par Lion/Tigre)" + RESET);
        System.out.println(YELLOW + " @    : Sanctuaire (case de but)\n"
                + "        - (0,3) pour le joueur 1, (8,3) pour le joueur 2" + RESET);
        System.out.println(YELLOW + " #    : Piège (met la pièce vulnérable, n'importe quelle pièce peut capturer)" + RESET);
        System.out.println(BROWN + "L1, L2: Lion (force 7) - peut sauter par-dessus la rivière" + RESET);
        System.out.println(BROWN + "T1, T2: Tigre (force 6) - peut sauter par-dessus la rivière" + RESET);
        System.out.println(BROWN + "P1, P2: Panthère (force 5)" + RESET);
        System.out.println(BROWN + "W1, W2: Loup (force 3)" + RESET);
        System.out.println(BROWN + "C1, C2: Chien (force 4)" + RESET);
        System.out.println(BROWN + "A1, A2: Chat (force 2)" + RESET + "\n"
                + "        - Ne peut pas sauter ni nager dans la rivière");
        System.out.println(BROWN + "R1, R2: Rat (force 1) - unique à pouvoir nager dans la rivière,\n"
                + "        peut capturer l'Éléphant et bloque les sauts Lion/Tigre s'il nage" + RESET);
        System.out.println(BROWN + "E1, E2: Éléphant (force 8) - ne peut pas entrer dans l'eau ni capturer le Rat" + RESET);
    }

    /**
     * Point d'entrée pour afficher les informations de jeu.
     */
    public static void afficherInfosJeu() {
        afficherProcedure();
        System.out.println();
        afficherSymboles();
    }
}
