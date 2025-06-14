package jeu;

public class Plateau {
    private String[][] grille;
    private final int LIGNES = 9;
    private final int COLONNES = 7;
    private String[][] fondGrille;
    public static final String RESET = "[0m";
    public static final String BLUE = "[34m";
    public static final String GREEN = "[32m";
    public static final String RED = "[31m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BROWN = "\u001B[33m";  // Jaune foncé / marron

    public Plateau() {
        grille = new String[LIGNES][COLONNES];
        fondGrille = new String[LIGNES][COLONNES];
        initialiserGrille();
        // Copier la grille initiale dans fondGrille
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                fondGrille[i][j] = grille[i][j];
            }
        }
    }

    public void restaurerCase(int x, int y) {
        grille[x][y] = fondGrille[x][y];
    }

    private void initialiserGrille() {
        // Initialiser toutes les cases à vide
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                grille[i][j] = ".";
            }
        }
        // Rivières
        for (int i = 3; i <= 5; i++) {
            grille[i][1] = "~";
            grille[i][2] = "~";
            grille[i][4] = "~";
            grille[i][5] = "~";
        }
        // Sanctuaires
        grille[0][3] = "@";
        grille[8][3] = "@";
        // Pièges
        grille[0][2] = "#";
        grille[0][4] = "#";
        grille[1][3] = "#";
        grille[7][3] = "#";
        grille[8][2] = "#";
        grille[8][4] = "#";
    }

    public void afficher() {
        // En-têtes de colonnes
        System.out.print("	");
        for (int col = 0; col < COLONNES; col++) {
            System.out.print(col + "	");
        }
        System.out.println();

        for (int i = 0; i < LIGNES; i++) {
            System.out.print(i + "	");
            for (int j = 0; j < COLONNES; j++) {
                String cell = grille[i][j];
                String colored;
                if ("~".equals(cell)) {
                    colored = BLUE + cell + RESET;
                }
                // Couleur pièce joueur 1
                else if (cell.endsWith("1")) {
                    colored = BROWN + cell + RESET;
                }
                // Couleur pièce joueur 2
                else if (cell.endsWith("2")) {
                    colored = PURPLE + cell + RESET;
                }
                // Autres cases
                else {
                    colored = cell;
                }
                System.out.print(colored + "	");
            }
            System.out.println();
        }
    }

    public String[][] getGrille() {
        return grille;
    }

    public void setCase(int x, int y, String symbole) {
        grille[x][y] = symbole;
    }

    public String getCase(int x, int y) {
        return grille[x][y];
    }
}
