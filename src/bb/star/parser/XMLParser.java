package bb.star.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import com.pack.model.XmlModel;

public class XMLParser {

	public static XMLParser INSTANCE = new XMLParser();

	ArrayList<Object> xmlDetailArray = new ArrayList<Object>();

	public ArrayList<Object> parseMain(InputStream stream) throws Exception {

		try {

			final XmlDomNode rootElement = XmlDomParser.parseTree(stream);

			if (rootElement.getName().equalsIgnoreCase("rss")) {
				Enumeration rootChildren = rootElement.getChildren();

				while (rootChildren.hasMoreElements()) {
					XmlDomNode rootChildNode = (XmlDomNode) rootChildren.nextElement();
					String rootNodeName = rootChildNode.getName();

					if (rootNodeName.equalsIgnoreCase("channel")) {

						Enumeration mainChild = rootChildNode.getChildren();

						while (mainChild.hasMoreElements()) {

							XmlDomNode subChildNode = (XmlDomNode) mainChild.nextElement();
							String subChildNodeName = subChildNode.getName();

							if (subChildNodeName.equalsIgnoreCase("item")) {

								String descriptionLink = "";
								XmlModel xmlModel = new XmlModel();

								Enumeration children = subChildNode.getChildren();
								while (children.hasMoreElements()) {

									XmlDomNode childrenElement = (XmlDomNode) children.nextElement();
									String childrenName = childrenElement.getName();

									if (childrenName.equalsIgnoreCase("title")) {
										System.out.println("title------------" + childrenElement.getText());
										xmlModel.setTitle(childrenElement.getText());
									} else if (childrenName.equalsIgnoreCase("description")) {
										System.out.println("desc------------" + childrenElement.getText());
										String description = childrenElement.getText();

										String descriptionDetail = description.replaceAll("<(.|\n)*?>", "");
										System.out.println("---------DescriptionDetail = " + descriptionDetail);
										xmlModel.setDescription(descriptionDetail);

										int start = description.indexOf('=');
										int end = description.lastIndexOf('"');
										descriptionLink = description.substring(start + 2, end);
										if (descriptionLink.contains("target") && !(descriptionLink.indexOf(">") != -1)) {
											int firstIndex = descriptionLink.indexOf('"');
											descriptionLink = descriptionLink.substring(0, firstIndex);
											xmlModel.setDescriptionLink(descriptionLink);
										} else {
											xmlModel.setDescriptionLink(descriptionLink);
										}

										System.out.println("---------descriptionLink = " + descriptionLink);

										if (descriptionLink.indexOf(">") != -1) {
											String sortout = descriptionLink.replaceAll("<(.|\n)*?>", "");
											int start1 = sortout.indexOf("<a");
											String sort = sortout.substring(start1);

											int firstIndex = sort.indexOf('"');
											int in = firstIndex + 1;
											int secondIndex = sort.indexOf('"', firstIndex + 1);
											descriptionLink = sort.substring(in, secondIndex);
											xmlModel.setDescriptionLink(descriptionLink);

											System.out.println("---------sortout = " + sortout);
										}
										System.out.println("---------- s= " + descriptionLink);
									} else if (childrenName.equalsIgnoreCase("pubDate")) {
										System.out.println("pubDate------------" + childrenElement.getText());
										xmlModel.setDate(childrenElement.getText());
									}
								}
								xmlDetailArray.add(xmlModel);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return xmlDetailArray;
	}
}
