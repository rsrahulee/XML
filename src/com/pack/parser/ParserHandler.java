package com.pack.parser;

import java.io.InputStream;
import java.util.ArrayList;

import com.pack.model.XmlDetails;

public class ParserHandler {
	public static ParserHandler INSTANCE = new ParserHandler();

	ArrayList<Object> xmlDetailArray = new ArrayList<Object>();

	public void parseXMLResponse(InputStream xmlInputStream) throws Exception {
		try {

			xmlDetailArray = XMLParser.INSTANCE.parseResponse(xmlInputStream);

			XmlDetails details = new XmlDetails();
			
			for (int i = 0; i < xmlDetailArray.size(); i++) {
				
				details = (XmlDetails) xmlDetailArray.get(i);
				System.out.println("title" + i + "------------" + details.getTitle());
				System.out.println("Desc" + i + "------------" + details.getDescription());
				System.out.println("DescLink" + i + "------------" + details.getDescriptionLink());
				System.out.println("Date" + i + "------------" + details.getDate());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
