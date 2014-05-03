package polymath.gcq.model;

public class Category {
	
	private Boolean bestOfferEnabled;
	private int categoryID;
	private int categoryLevel;
	private String categoryName;
	private int categoryParentID;
	private Boolean lsd;
	
	public Category() {
		super();
	}

	public Boolean getBestOfferEnabled() {
		return bestOfferEnabled;
	}

	public void setBestOfferEnabled(Boolean bestOfferEnabled) {
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

	public Boolean getLsd() {
		return lsd;
	}

	public void setLsd(Boolean lsd) {
		this.lsd = lsd;
	}

}
