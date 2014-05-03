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
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists categories;");
			stat.executeUpdate("create table categories (best_offer_enabled boolean, category_id int primary key, category_level int, category_name varchar(100), category_parent_id int, lsd boolean);");

			ResultSet rs = stat
					.executeQuery("select count(1) from categories;");
			rs.next();

			System.out.println("Initial Recount: " + rs.getInt(1));
			rs.close();
			conn.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void populateDataBase() {
		insertCategories(DataRetriever.retrieveCategories());
	}

	/**
	 * private int categoryID; private int categoryLevel; private String
	 * categoryName; private int categoryParentID; private Boolean lsd;
	 * 
	 * @param cat
	 */
	private static void insertCategories(List<Category> cat) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();
			PreparedStatement prep = conn
					.prepareStatement("insert into categories values (?,?, ?, ?, ?, ?);");

			for (Category ca : cat) {
				prep.setBoolean(1, ca.getBestOfferEnabled());
				prep.setInt(2, ca.getCategoryID());
				prep.setInt(3, ca.getCategoryLevel());
				prep.setString(4, ca.getCategoryName());
				prep.setInt(5, ca.getCategoryID());
				prep.setBoolean(6, ca.getLsd());
				prep.addBatch();
			}
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);

			ResultSet rs = stat.executeQuery("select count(1) from categories;");
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

	public static void getCategoryInfo(String id) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from categories where category_id ="+ id +" ;");
			rs.next();
			
			System.out.println(" ");
			System.out.println("Category ID: " + rs.getInt("category_id"));
			System.out.println("Name: " + rs.getString("category_name"));
			System.out.println("Level: " + rs.getInt("category_level"));
			System.out.println("Best Offer Enabled: " + rs.getBoolean("best_offer_enabled"));
			System.out.println("Ancestors: ");
			rs.close();
			conn.close();
		} catch (SQLException sqlex) {
			System.out.println("We are sorry there's no category related with this id:" + id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

}
