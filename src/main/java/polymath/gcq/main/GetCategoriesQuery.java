package polymath.gcq.main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import polymath.gcq.db.CategoryManager;

public class GetCategoriesQuery {

	@Option(name = "--rebuild", usage = "Rebuild ebay categories database", required = false)
	private boolean rebuild = false;

	@Option(name = "--info", usage = "--info <category_id> , Get information about the categorie", required = false)
	private String info;

	public static void main(String[] args) {
		// Instantiate application
		final GetCategoriesQuery instance = new GetCategoriesQuery();
		// Run application
		try {
			instance.doMain(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Something went wrong: " + e.getMessage());
		}
	}

	public void doMain(String[] args) {
		// Initialize argument parser
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(1024);
		try {
			parser.parseArgument(args);
			if (rebuild) {
				// execute rebuild process
				System.out.println("Rebuild: " + rebuild);
				CategoryManager.rebuildDataBase();
				CategoryManager.populateDataBase();
			} else if (info != null && !info.isEmpty()) {
				// print info about the given category
				System.out.println("Info: " + info);
//				CategoryFinder cf = new CategoryFinder();
//				cf.findAndPrint(info);	
				
			} else {
				System.exit(1);
			}
		} catch (CmdLineException e) {
			System.err.println("Oops, Something went wrong: " + e.getMessage());
			System.exit(2);
		}
	}
}
