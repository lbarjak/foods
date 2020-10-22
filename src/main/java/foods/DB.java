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
		// String validationQuery =
		// env.getProperty("spring.datasource.validation-query");

		try {
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("A kapcsolat létrejött");
			System.out.println("===============================================");
			findAccess();
			findCount();
			findTest();
			//findSourceId();
			//findMilk();
		} catch (SQLException ex) {
			System.out.println("Nincs kapcsolat az adatbázissal");
			System.out.println("" + ex);
		}

	}

	public void findAccess() {

		query = "SELECT 1 AS val";
		print(query);
	}

	public void findCount() {
		query = "SELECT COUNT(*) FROM contents";
		print(query);
	}

	public void findTest() {
		String column1 = "id", column2 = "source_type";
		query = "SELECT " + column1 + ", " + column2 + " FROM contents LIMIT 3";
		print(query);
	}

	public void findSourceId() {
			String column1 = "source_id", column2 = "orig_source_name";
			query = "SELECT DISTINCT " + column1 + ", " + column2 + " FROM contents " + "WHERE source_id IS NOT NULL "
					+ "AND orig_source_name IS NOT NULL " + "ORDER BY source_id " + "LIMIT 1000";
			print(query);
	}

	public void findMilk() {

		String column0 = "source_id", column1 = "orig_source_name", column2 = "orig_content", column3 = "orig_unit";

		query = "SELECT " + column0 + ", " + column1 + ", " + column2 + ", " + column3
				+ " FROM contents WHERE orig_food_common_name "
				+ "= \"Milk, whole, konventional (not organic), 3.5 % fat\" " + "AND orig_source_name IS NOT NULL";

		print(query);
	}

	public void print(String query) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			System.out.print("|");
			for (int col = 1; col <= colCount; col++) {
				System.out.print(meta.getColumnName(col) + "|");
			}
			System.out.print("\n|");
			for (int col = 1; col <= colCount; col++) {
				System.out.print(meta.getColumnTypeName(col) + "|");
			}
			System.out.println();
			while (rs.next()) {
				System.out.print("|");
				for (int col = 1; col <= colCount; col++) {
					String value = rs.getString(col);
					System.out.print(value + "|");
				}
				System.out.println();
			}
			System.out.println("===============================================");
		} catch (SQLException ex) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
