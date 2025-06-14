package jeu;
import java.sql.PreparedStatement;

import java.sql.*;

    public class DatabaseConnection {
        private static final String JDBC_URL = "jdbc:h2:./xoudouqiDB";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        private Connection conn;

        // Constructeur : ouvre la connexion et crée les tables si besoin
        public DatabaseConnection() throws SQLException {
            connect();
            createTables();
            creerTablePartiesSiPasExiste();

        }
        public Connection getConnection() {
            return conn;
        }

        // Connexion à la base
        private void connect() throws SQLException {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }

        private void createTables() throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS joueurs (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(255), " +
                    "motdepasse VARCHAR(255), " +
                    "score INT, " +
                    "datejeu TIMESTAMP)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }


        public ResultSet getAllPlayers() throws SQLException {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT * FROM joueurs");
        }

        public void close() throws SQLException {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        // Vérifier si un joueur existe (par nom)
        public boolean joueurExiste(String nom) throws SQLException {
            String sql = "SELECT COUNT(*) FROM joueurs WHERE nom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }

        // Vérifier la connexion (nom + mdp)
        public boolean verifierConnexion(String nom, String mdp) throws SQLException {
            String sql = "SELECT COUNT(*) FROM joueurs WHERE nom = ? AND motdepasse = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, mdp);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }

        public void insertPlayer(String nom, String motdepasse, int score, Timestamp date) throws SQLException {
            String sql = "INSERT INTO joueurs (nom, motdepasse, score, datejeu) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, motdepasse);
            stmt.setInt(3, score);
            stmt.setTimestamp(4, date);
            stmt.executeUpdate();
        }

        public int getJoueurId(String nom) throws SQLException {
            String sql = "SELECT id FROM joueurs WHERE nom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        }
        public ResultSet getHistoriqueEntreJoueurs(String joueur1, String joueur2) throws SQLException {
            String query = "SELECT * FROM parties WHERE " +
                    "(joueur1 = ? AND joueur2 = ?) OR (joueur1 = ? AND joueur2 = ?) " +
                    "ORDER BY datejeu DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, joueur1);
            stmt.setString(2, joueur2);
            stmt.setString(3, joueur2);
            stmt.setString(4, joueur1);
            return stmt.executeQuery();
        }

        public void creerTablePartiesSiPasExiste() throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS parties (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "joueur1 VARCHAR(255), " +
                    "joueur2 VARCHAR(255), " +
                    "gagnant VARCHAR(255), " +
                    "score INT, " +
                    "datejeu TIMESTAMP)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        }

        // Récupérer le score actuel
        public int getScore(String nom) throws SQLException {
            String sql = "SELECT score FROM joueurs WHERE nom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
            return 0;
        }

        // Mettre à jour le score
        public void mettreAJourScore(String nom, int nouveauScore) throws SQLException {
            String sql = "UPDATE joueurs SET score = ? WHERE nom = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nouveauScore);
            stmt.setString(2, nom);
            stmt.executeUpdate();
        }

    }



