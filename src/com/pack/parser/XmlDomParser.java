package com.pack.parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class XmlDomParser
{
	/**
	 * Parses an XML document and returns it's root element as an XmlDomNode.
	 * When the XML document has no root element, it will return an artificial root element that contains
	 * all root elelements of the document.
	 * 
	 * @param document the document.
	 * @return the root element of the document as XmlDomNode
	 * @throws RuntimeException when the parsing fails
	 */
	public static XmlDomNode parseTree(String document)
	{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.getBytes());
		return parseTree(byteArrayInputStream);
	}

	/**
	 * Parses an XML document and returns it's root element as an XmlDomNode.
	 * When the XML document has no root element, it will return an artificial root element that contains
	 * all root elements of the document.
	 * 
	 * @param in input stream of the document.
	 * @return the root element of the document as XmlDomNode
	 * @throws RuntimeException when the parsing fails
	 */
	public static XmlDomNode parseTree(InputStream in)
    {
    	XmlPullParser parser;
        InputStreamReader inputStreamReader = new InputStreamReader(in);

        try {
            parser = new XmlPullParser(inputStreamReader);
        } catch (IOException exception) {
            throw new RuntimeException("Could not create xml parser."+exception);
        }

        XmlDomNode root = new XmlDomNode(null, null, -1);
        XmlDomNode currentNode = root;
        String newName;
        int newType;
        
        try {
            while ((parser.next()) != SimplePullParser.END_DOCUMENT) {
                newName = parser.getName();
                newType = parser.getType();
                
                if(newType == SimplePullParser.START_TAG) {
                	Hashtable attributes = null;
                	int attributeCount = parser.getAttributeCount(); 

                	if (attributeCount > 0) {
                		attributes = new Hashtable();

                		for (int i = 0; i < attributeCount; i++) {
                			attributes.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                		}
                	}

                    XmlDomNode newNode = new XmlDomNode(currentNode, newName, attributes, newType);
                    currentNode = newNode;
                }
                
                else if(newType == SimplePullParser.END_TAG) {
                    currentNode = currentNode.getParent();
                }
                
                else if(newType == SimplePullParser.TEXT) {
                    String text = parser.getText();
                    currentNode.setText(text);
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException("parse error:"+exception);
        }
        if (root.getChildCount() == 1) {
        	return root.getChild(0);
        } else {
        	return root;
        }
    }
}

