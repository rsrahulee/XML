package com.pack.model;

public class XmlUrl {

	public static String getXmlUrl(String url_id) {
		String xmlURL = "http://usafootball.com/taxonomy/term/" + url_id + "/all/feed";		
		return xmlURL;
	}
}
