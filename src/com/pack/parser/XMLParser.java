package com.pack.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import com.pack.model.XmlDetails;
import com.pack.model.XmlTags;

public class XMLParser extends XmlTags{

	public static XMLParser INSTANCE = new XMLParser();

	ArrayList<Object> xmlDetailArray = new ArrayList<Object>();

	public ArrayList<Object> parseResponse(InputStream stream) throws Exception {

		try {

			final XmlDomNode rootElement = XmlDomParser.parseTree(stream);

			if (rootElement.getName().equalsIgnoreCase(RSS)) {
				Enumeration rootChildren = rootElement.getChildren();

				while (rootChildren.hasMoreElements()) {
					XmlDomNode rootChildNode = (XmlDomNode) rootChildren.nextElement();
					String rootNodeName = rootChildNode.getName();

					if (rootNodeName.equalsIgnoreCase(CHANNEL)) {

						Enumeration mainChild = rootChildNode.getChildren();

						while (mainChild.hasMoreElements()) {

							XmlDomNode subChildNode = (XmlDomNode) mainChild.nextElement();
							String subChildNodeName = subChildNode.getName();

							if (subChildNodeName.equalsIgnoreCase(ITEM)) {

								String descriptionLink = "";
								XmlDetails details = new XmlDetails();

								Enumeration children = subChildNode.getChildren();
								while (children.hasMoreElements()) {

									XmlDomNode childrenElement = (XmlDomNode) children.nextElement();
									String childrenName = childrenElement.getName();

									if (childrenName.equalsIgnoreCase(TITLE)) {
										details.setTitle(childrenElement.getText());
									} else if (childrenName.equalsIgnoreCase(DESCRIPTION)) {
										String description = childrenElement.getText();

										String descriptionDetail = description.replaceAll("<(.|\n)*?>", "");
										details.setDescription(descriptionDetail);

										if(description.contains("<a href=")){
											int start = description.indexOf('=');
											int end = description.lastIndexOf('"');
											descriptionLink = description.substring(start + 2, end);
											
											if (descriptionLink.contains("target") && !(descriptionLink.indexOf(">") != -1)) {
												int firstIndex = descriptionLink.indexOf('"');
												descriptionLink = descriptionLink.substring(0, firstIndex);
												details.setDescriptionLink(descriptionLink);
											} else {
												details.setDescriptionLink(descriptionLink);
											}

											if (descriptionLink.indexOf(">") != -1) {
												String sortout = descriptionLink.replaceAll("<(.|\n)*?>", "");
												int startindex = sortout.indexOf("<a");
												String sort = sortout.substring(startindex);

												int firstIndex = sort.indexOf('"');
												int startup = firstIndex + 1;
												int secondIndex = sort.indexOf('"', firstIndex + 1);
												descriptionLink = sort.substring(startup, secondIndex);
												details.setDescriptionLink(descriptionLink);
											}
										}else{
											details.setDescriptionLink("link not available");
										}
										
									} else if (childrenName.equalsIgnoreCase(PUB_DATE)) {
										details.setDate(childrenElement.getText());
									}
								}
								xmlDetailArray.add(details);
							}
						}
						System.out.println("");
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return xmlDetailArray;
	}
}
