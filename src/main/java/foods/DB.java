package foods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

@Repository
public class DB {

	@Autowired
	private Environment env;

	Connection conn = null;

	@PostConstruct
	public void onStartUp() {
		String url = env.getProperty("spring.datasource.url");
		String userName = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");

		try {
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("A kapcsolat létrejött");
		} catch (SQLException ex) {
			System.out.println("Nincs kapcsolat az adatbázissal");
			System.out.println("" + ex);
		}
	}

	public ArrayList<ArrayList<String>> find(String query) {
		return print(query);
	}
	
	public ArrayList<ArrayList<String>> print(String query) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			result.add(new ArrayList<String>());
			for (int col = 1; col <= colCount; col++) {
				result.get(0).add(meta.getColumnName(col));
			}
			result.add(new ArrayList<String>());
			for (int col = 1; col <= colCount; col++) {
				result.get(1).add(meta.getColumnTypeName(col));
			}
			int k = 2;
			while (rs.next()) {
				result.add(new ArrayList<String>());
				for (int col = 1; col <= colCount; col++) {
					result.get(k).add(rs.getString(col));
				}
				k++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
		}
//		for(int i = 0; i < result.size(); i++) {
//			System.out.print("|");
//			for(int j = 0; j < result.get(0).size(); j++) {
//				System.out.print(result.get(i).get(j) + "|");
//			}
//			System.out.println();
//		}
		return result;
	}
}
