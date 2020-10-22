package foods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DB {

    @Autowired
    private Environment env;

    Connection conn = null;
    String query;

    @PostConstruct
    public void onStartUp() {
        String url = env.getProperty("spring.datasource.url");
        String userName = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        //String validationQuery = env.getProperty("spring.datasource.validation-query");

        try {
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("A kapcsolat létrejött");
            System.out.println("===============================================");
            findSourceId();
        } catch (SQLException ex) {
            System.out.println("Nincs kapcsolat az adatbázissal");
            System.out.println("" + ex);
        }

    }

    public void findAccess() {

        try {
            query = "SELECT 1 AS val";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("val"));
            }
            System.out.println("===============================================");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findCount() {

        try {
            query = "SELECT COUNT(*) FROM contents";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("count(*)"));
            }
            System.out.println("===============================================");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findTest() {
        
        try {
            String column1 = "id", column2 = "source_type";
            query = "SELECT " + column1 + ", " + column2 + " FROM contents LIMIT 3";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(column1 + "|" + column2);
            while (rs.next()) {
                System.out.print(rs.getString(column1));
                System.out.print("|");
                System.out.println(rs.getString(column2));
            }
            System.out.println("===============================================");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findSourceId() {
        
        try {
            String column1 = "source_id", column2 = "orig_source_name";
            query = "SELECT DISTINCT " + column1 + ", " + column2 
            		+ " FROM contents "
            		+ "WHERE source_id IS NOT NULL "
            		+ "AND orig_source_name IS NOT NULL "
            		+ "ORDER BY source_id "
            		+ "LIMIT 1000";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(column1 + "|" + column2);
            while (rs.next()) {
                System.out.print(rs.getString(column1));
                System.out.print("|");
                System.out.println(rs.getString(column2));
            }
            System.out.println("===============================================");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findMilk() {

        try {
            String 
                    column0 = "source_id",
                    column1 = "orig_source_name",
                    column2 = "orig_content",
                    column3 = "orig_unit";
            
            query = "SELECT " + column0 + ", " + column1 + ", " + column2 + ", " + column3
                    + " FROM contents WHERE orig_food_common_name "
                    + "= \"Milk, whole, konventional (not organic), 3.5 % fat\" "
                    + "AND orig_source_name IS NOT NULL";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            System.out.print("|");
            for (int col = 1; col <= colCount; col++) {
                System.out.print(meta.getColumnName(col) + "|");
            }
            System.out.println();
            
                        System.out.print("|");
            for (int col = 1; col <= colCount; col++) {
                System.out.print(meta.getColumnTypeName(col) + "|");
            }
            System.out.println();
            
            while (rs.next()) {
                System.out.print("|");
                for (int col = 1; col <= colCount; col++) {
                    String value = rs.getString(col);
                    System.out.print(value);
                    System.out.print("|");
                }
                System.out.println();
            }
            System.out.println("===============================================");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

