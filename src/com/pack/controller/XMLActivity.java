package com.pack.controller;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import bb.star.parser.ParserHandler;

import com.pack.model.XmlID;
import com.pack.model.XmlUrl;
import com.pack.networkConnection.Connect;

public class XMLActivity extends Activity {
	
	private InputStream inputStream;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
        	String xml_url = XmlUrl.getXmlUrl(XmlID.mainpage_xml);
        	inputStream = Connect.INSTANCE.getXmlFromUrl(xml_url);
			ParserHandler.INSTANCE.parseXMLResponse(inputStream);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}