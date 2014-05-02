package polymath.gcq.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import polymath.gcq.conn.DataRetriever;
import polymath.gcq.model.Category;

public class CategoryManager {

	public static void rebuildDataBase() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists categories;");
			stat.executeUpdate("create table categories (category_id int primary key, category_level int, category_name varchar(100), category_parent_id int, lsd boolean);");
			
			ResultSet rs = stat.executeQuery("select count(1) from categories;");
			rs.next();
			
			System.out.println("Initial Recount: " + rs.getInt(1));
			rs.close();
			conn.close();
		} catch (SQLException sqlex) {
			// TODO Auto-generated catch block
			sqlex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void populateDataBase(){
		insertCategories(DataRetriever.retrieveCategories());
	}
	/**
	 * private int categoryID;
	private int categoryLevel;
	private String categoryName;
	private int categoryParentID;
	private Boolean lsd;
	
	 * @param cat
	 */
	private static void insertCategories(List<Category> cat) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();
			PreparedStatement prep = conn.prepareStatement("insert into categories values (?, ?, ?, ?, ?);");

			for(Category ca : cat){
				prep.setInt(1,  ca.getCategoryID());
				prep.setInt(2,  ca.getCategoryLevel());
				prep.setString(3, ca.getCategoryName());
				prep.setInt(4,  ca.getCategoryID());
				prep.setBoolean(5,  ca.getLsd());
				prep.addBatch();
			}
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select * from categories;");
			while (rs.next()) {
				System.out.println("categoryID: " + rs.getInt("category_id") + " categoryLevel = " + rs.getInt("category_level")+ " categoryName = " + rs.getString("category_name")+ " categoryParentID = " + rs.getInt("category_parent_id")+ " lsd = " + rs.getBoolean("lsd"));				
			}
			rs = stat.executeQuery("select count(1) from categories;");
			rs.next();
			
			System.out.println("Final Recount" + rs.getInt(1));
			
			rs.close();
			conn.close();
		} catch (SQLException sqlex) {
			// TODO Auto-generated catch block
			sqlex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
