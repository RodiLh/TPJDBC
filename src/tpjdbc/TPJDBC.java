package tpjdbc;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author dmr
 */
public class TPJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Properties conf = new Properties();
            conf.load(new FileReader("src\\tpjdbc\\mysql.properties"));
            
            String driver = conf.getProperty("driver");
            String url = conf.getProperty("url");
            String user = conf.getProperty("user");
            String password = conf.getProperty("password");
            
            Class.forName(driver).newInstance();
            
            conn = DriverManager.getConnection(url,  user, password);
            
//            /* Création de l'objet gérant les requêtes */
//            stmt = conn.createStatement();
//            /* Execution des requêtes */
//            rs = stmt.executeQuery("SELECT * FROM pizzas");
            
            /* Création de l'objet gérant la requête préparée définie */
            ps = conn.prepareStatement("SELECT * FROM pizzas");
            /* Execution de la requête préparée */
            rs = ps.executeQuery();
            System.out.println("---------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomPizza = rs.getString("nom");
                float prixPizza = rs.getFloat("prix");
                System.out.println("Id : " + id + ", nom : "  + nomPizza + ", prix : " + prixPizza);
                System.out.println("---------------------------------------");
            }
            
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur : " + e);            
            e.printStackTrace();
        } finally {
            
            // On ferme le ResultSet
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { } // ignore

                rs = null;
            }
            
            // On ferme le Statement
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { } // ignore

                stmt = null;
            }
            
            // On ferme la Connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { }
                
                conn = null;
            }
        }
        
    }
    
}
