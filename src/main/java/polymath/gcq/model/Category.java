package polymath.gcq.model;

/**
 * Class that represents an Ebay XML API Category
 * @author dham
 *
 */
public class Category {
	
	private String bestOfferEnabled;
	private int categoryID;
	private int categoryLevel;
	private String categoryName;
	private int categoryParentID;
	private String lsd;
	
	public Category() {
		super();
	}

	public String getBestOfferEnabled() {
		return bestOfferEnabled;
	}

	public void setBestOfferEnabled(String bestOfferEnabled) {
		this.bestOfferEnabled = bestOfferEnabled;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryParentID() {
		return categoryParentID;
	}

	public void setCategoryParentID(int categoryParentID) {
		this.categoryParentID = categoryParentID;
	}

	public String getLsd() {
		return lsd;
	}

	public void setLsd(String lsd) {
		this.lsd = lsd;
	}

	@Override
	public String toString() {
		return "Category [bestOfferEnabled=" + bestOfferEnabled
				+ ", categoryID=" + categoryID + ", categoryLevel="
				+ categoryLevel + ", categoryName=" + categoryName
				+ ", categoryParentID=" + categoryParentID + ", lsd=" + lsd
				+ "]";
	}
	
	

}
