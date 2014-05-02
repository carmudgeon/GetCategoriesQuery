package polymath.gcq.model;

public class Category {
	private int categoryID;
	private int categoryLevel;
	private String categoryName;
	private int categoryParentID;
	private Boolean lsd;
	
	public Category() {
		super();
	}

	public Category(int categoryID, int categoryLevel, String categoryName,int categoryParentID, Boolean lsd) {
		super();
		this.categoryID = categoryID;
		this.categoryLevel = categoryLevel;
		this.categoryName = categoryName;
		this.categoryParentID = categoryParentID;
		this.lsd = lsd;
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


//<Category>
//<BestOfferEnabled>true</BestOfferEnabled>
//<CategoryID>10542</CategoryID>
//<CategoryLevel>1</CategoryLevel>
//<CategoryName>Real Estate</CategoryName>
//<CategoryParentID>10542</CategoryParentID>
//<LSD>true</LSD>
//</Category>