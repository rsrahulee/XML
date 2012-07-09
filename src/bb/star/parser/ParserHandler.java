package bb.star.parser;

import java.io.InputStream;
import java.util.ArrayList;

import com.pack.model.XmlModel;

public class ParserHandler {
	public static ParserHandler INSTANCE = new ParserHandler();

	ArrayList<Object> xmlDetailArray = new ArrayList<Object>();

	public void parseMainResponse(InputStream xmlPath) throws Exception {
		try {

			xmlDetailArray = XMLParser.INSTANCE.parseMain(xmlPath);

			XmlModel xmlMod = new XmlModel();
			
			for (int i = 0; i < xmlDetailArray.size(); i++) {
				
				xmlMod = (XmlModel) xmlDetailArray.get(i);
				System.out.println("title" + i + "------------" + xmlMod.getTitle());
				System.out.println("Desc" + i + "------------" + xmlMod.getDescription());
				System.out.println("DescLink" + i + "------------" + xmlMod.getDescriptionLink());
				System.out.println("Date" + i + "------------" + xmlMod.getDate());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
