package com.pack.controller;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import bb.star.parser.ParserHandler;

import com.pack.networkConnection.Connect;

public class XMLActivity extends Activity {
	
	private InputStream inputStream;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
        	inputStream = Connect.INSTANCE.getXmlFromUrl("http://usafootball.com/taxonomy/term/4/all/feed");
			ParserHandler.INSTANCE.parseMainResponse(inputStream);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}