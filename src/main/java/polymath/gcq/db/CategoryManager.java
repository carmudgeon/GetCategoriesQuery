package polymath.gcq.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import polymath.gcq.model.Category;

/**
 * Class used to handle all connections/operations with the categories database
 * @author dham
 *
 */
public class CategoryManager {

	/**
	 * Method that builds the sqlite categories database.
	 * if the database exists the method will drop it and recreate a fresh new one
	 */
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

	/**
	 * Method that inserts the retrieved categories from the XML ebay developers source 
	 * @param cat - Array of catalog items
	 */
	public static void populateDataBase(List<Category> cat) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();
			PreparedStatement prep = conn
					.prepareStatement("insert into categories values (?,?, ?, ?, ?, ?);");

			for (Category ca : cat) {
				prep.setString(1, ca.getBestOfferEnabled());
				prep.setInt(2, ca.getCategoryID());
				prep.setInt(3, ca.getCategoryLevel());
				prep.setString(4, ca.getCategoryName());
				prep.setInt(5, ca.getCategoryParentID());
				prep.setString(6, ca.getLsd());
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
			sqlex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is in charge of printing the information of the ebay category
	 * @param id - id of the category
	 */
	public static void getCategoryInfo(String id) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:categories.db");
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from categories where category_id ="+ id +" ;");
			rs.next();
			
			Category cat = mapCategory(rs);
			
			System.out.println("Category ID: " + cat.getCategoryID());
			System.out.println("Name: " + cat.getCategoryName());
			System.out.println("Level: " + cat.getCategoryLevel());
			System.out.println("Best Offer Enabled: " + cat.getBestOfferEnabled());
			System.out.print("Ancestors: ");
			
			// this can be optimized with a better query
			if(cat.getCategoryID() != cat.getCategoryParentID()){
				while(cat.getCategoryParentID() != cat.getCategoryID()){
					rs = stat.executeQuery("select * from categories where category_id = "+ cat.getCategoryParentID());
					rs.next();
					cat = mapCategory(rs);
					System.out.print(cat.getCategoryID() + "(" + cat.getCategoryName() + "), ");
				}								
			}
			System.out.println(" ");
			rs.close();
			conn.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			System.out.println("We are sorry there's no category related with this id:" + id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Helper method used to map a result set with a category item
	 * @param rs result set wit the data retrieved from the sqlite database
	 * @return a mapped category
	 * @throws SQLException
	 */
	private static Category mapCategory(ResultSet rs) throws SQLException{
		Category cat = new Category();
		cat.setCategoryID(rs.getInt("category_id"));
		cat.setCategoryName(rs.getString("category_name"));
		cat.setCategoryLevel(rs.getInt("category_level"));
		cat.setBestOfferEnabled(rs.getString("best_offer_enabled"));
		cat.setCategoryParentID(rs.getInt("category_parent_id"));
		return cat;
	}

}
