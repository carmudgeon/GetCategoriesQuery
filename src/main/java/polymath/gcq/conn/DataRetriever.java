package polymath.gcq.conn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import polymath.gcq.model.Category;

public class DataRetriever {

	public final static String EBAYAPI_URL = "https://api.sandbox.ebay.com/ws/api.dll";
	public final static String DATA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<GetCategoriesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
			+ "<CategoryParent>10542</CategoryParent>"
			+ "<CategorySiteID>0</CategorySiteID>"
			+ "<ViewAllNodes>True</ViewAllNodes>"
			+ "<DetailLevel>ReturnAll</DetailLevel>"
			+ "<RequesterCredentials>"
			+ "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**t2XTUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpaCpQWdj6x9nY+seQ**L0MCAA**AAMAAA**pZOn+3Cb/dnuil4E90EEeGpHlaBVP0VpLebK58TPQ210Sn33HEvjGYoC9UYVqfbhxte6wp8/fPL795uVh9/4X00HC3wAfzcV+wobN2NfReqWAXFdfuj4CbTHEzIHVLJ8tApLPlI8Nxq6oCa5KsZf5L+An85i2BnohCfscJtl9OcZYnyWnD0oA4R3zdnH3dQeKRTxws/SbVCTgWcMXBqL9TUr4CrnOFyt0BdYp4lxg0HbMv1akuz+U7wQ3aLxJeFoUow20kUtVoTIDhnpfZ40Jcl/1a2ui0ha3rl9D3oA66PUhHSnHJTznwtp1pFLANWn9I49l9rrYbzzobB6SGf18LK/5cqSwse3AWMXJkFVbgFM7e5DZBv59S1sCRdEjzw8GciKYSxGDqu8UJQHgL/QPiTFhtj2Ad/vjZ/6PLBVA9rhOxJnlhCvLXmWZIf1NNcckN8uEEIqK7Wn0DdDi8p44ozIWNaIQ319HjYYOBp4a5FLUjwXCamoqfSjYli5ikqe0jwn+LxnOWblY47TFhruRQpYaBAro4VbgirwNYT7RlEGz+u7ol9A873dnqEZgdXWfrWkyxyKGeXHnHjiynfL/JDCdl2U2s+s5iOd8hp6QklHevPOlOtZgW+K/eFIv53UATQi4vMptUKEeD6QxFzvxP7wRAkKIQZUq+LKB8lZBP/Epjni47HXKpwQdgbTWbyfHpSF3A52u8koUY9chiBk1FCpqjBM/BT5tjhIlrQUVeWUUyGeQ49sJJvaeVnaavo9</eBayAuthToken>"
			+ "</RequesterCredentials>" 
			+ "</GetCategoriesRequest>";

	public static List<Category> retrieveCategories() {
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			URL obj = new URL(EBAYAPI_URL);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");

			con.setRequestProperty("X-EBAY-API-COMPATIBILITY-LEVEL", "861");
			con.setRequestProperty("X-EBAY-API-DEV-NAME",
					"16a26b1b-26cf-442d-906d-597b60c41c19");
			con.setRequestProperty("X-EBAY-API-APP-NAME",
					"EchoBay62-5538-466c-b43b-662768d6841");
			con.setRequestProperty("X-EBAY-API-CERT-NAME",
					"00dd08ab-2082-4e3c-9518-5f4298f296db");
			con.setRequestProperty("X-EBAY-API-CALL-NAME", "GetCategories");
			con.setRequestProperty("X-EBAY-API-SITEID", "0");
			con.setRequestProperty("Content-Type", "text/xml");

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(DATA);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseXMLData(response.toString());
	}

	/**
	 * <CategoryID>10542</CategoryID> <CategoryLevel>1</CategoryLevel>
	 * <CategoryName>Real Estate</CategoryName>
	 * <CategoryParentID>10542</CategoryParentID> <LSD>true</LSD>
	 * 
	 * @param xml
	 * @return
	 * @throws XMLStreamException 
	 * @throws NumberFormatException 
	 */
	public static List<Category> parseXMLData(String xml) {
		List<Category> categories = null;
		Category currentCat = null;
		String tagContent = null;

		Reader reader = new StringReader(xml);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader = null;
		try {
			xmlReader = factory.createXMLStreamReader(reader);
		} catch (XMLStreamException e1) {
			e1.printStackTrace();
		}

		try {
			while (xmlReader.hasNext()) {
				int event = xmlReader.next();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if ("Category".equals(xmlReader.getLocalName())) {
						currentCat = new Category();
					}
					if ("CategoryArray".equals(xmlReader.getLocalName())) {
						categories = new ArrayList<Category>();
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					tagContent = xmlReader.getText().trim();
					break;

				case XMLStreamConstants.END_ELEMENT:
					switch (xmlReader.getLocalName()) {
					case "BestOfferEnabled":
						currentCat.setBestOfferEnabled(Boolean.parseBoolean(tagContent));
						break;
					case "Category":
						categories.add(currentCat);
						break;
					case "CategoryID":
						currentCat.setCategoryID(Integer.parseInt(tagContent));
						break;
					case "CategoryLevel":
						currentCat.setCategoryLevel(Integer.parseInt(tagContent));
						break;
					case "CategoryName":
						currentCat.setCategoryName(tagContent);
						break;
					case "CategoryParentID":
						currentCat
								.setCategoryParentID(Integer.parseInt(tagContent));
						break;
					case "LSD":
						currentCat.setLsd(Boolean.parseBoolean(tagContent));
						break;
					}
					break;
				}			
			}
		} catch (NumberFormatException | XMLStreamException e) {
			e.printStackTrace();
		}
		return categories;
	}
}
