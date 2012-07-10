package com.pack.parser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * <p>
 * Holds information about an XML node.
 * </p>
 */
public class XmlDomNode {
	private XmlDomNode parent;
	private final Vector childList;
	private Hashtable attributes;
	private String name;
	private int type;
	private String text;

	/**
	 * Creates a new XmlDomNode
	 * 
	 * @param parent
	 *            the parent node
	 * @param name
	 *            the name of this element
	 * @param type
	 *            the type of the element, see SimplePullParser
	 * @see SimplePullParser
	 */
	public XmlDomNode(XmlDomNode parent, String name, int type) {
		this(parent, name, null, type);
	}

	/**
	 * Creates a new XmlDomNode
	 * 
	 * @param parent
	 *            the parent node
	 * @param name
	 *            the name of this element
	 * @param attributes
	 *            the attributes of this node
	 * @param type
	 *            the type of the element, see SimplePullParser
	 * @see SimplePullParser
	 */
	public XmlDomNode(XmlDomNode parent, String name, Hashtable attributes,
			int type) {
		this.parent = parent;

		if (this.parent != null) {
			this.parent.addChild(this);
		}

		this.name = name;
		this.attributes = attributes;
		this.type = type;
		this.childList = new Vector();
	}

	/**
	 * Retrieves the name child element
	 * 
	 * @param childName
	 *            the name of the child
	 * @return the first child with the given name or null if no child node is
	 *         found.
	 */
	public XmlDomNode getChild(String childName) {
		XmlDomNode child;

		for (int i = 0; i < this.childList.size(); i++) {
			child = (XmlDomNode) this.childList.elementAt(i);

			if (childName.equals(child.getName())) {
				return child;
			}
		}

		childName = childName.toLowerCase();

		for (int i = 0; i < this.childList.size(); i++) {
			child = (XmlDomNode) this.childList.elementAt(i);

			if (childName.equals(child.getName())) {
				return child;
			}
		}

		return null;
	}

	/**
	 * Retrieves the numbered child
	 * 
	 * @param index
	 *            the index of the child
	 * @return the associated child
	 * @throws ArrayIndexOutOfBoundsException
	 *             when the index is illegal
	 * @see #getChildCount() for getting the number of children
	 */
	public XmlDomNode getChild(int index) {
		return (XmlDomNode) this.childList.elementAt(index);
	}

	/**
	 * Adds a child node
	 * 
	 * @param childNode
	 *            the childNode
	 */
	public void addChild(XmlDomNode childNode) {
		this.childList.addElement(childNode);
	}

	/**
	 * Retrieves the number of children
	 * 
	 * @return number of Childs.
	 */
	public int getChildCount() {
		return this.childList.size();
	}

	/**
	 * Retrieves the text embedded in this element
	 * 
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text of the node
	 * 
	 * @param text
	 *            the text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Retrieves the name of this node
	 * 
	 * @return the name of this node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this node
	 * 
	 * @param name
	 *            the name of this node
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the parent of this node
	 * 
	 * @return the parent or null if there is no parent
	 */
	public XmlDomNode getParent() {
		return this.parent;
	}

	/**
	 * Sets the parent of this node
	 * 
	 * @param parent
	 *            the parent node
	 */
	public void setParent(XmlDomNode parent) {
		this.parent = parent;
	}

	/**
	 * Retrieves the type of this node
	 * 
	 * @return the type
	 * @see SimplePullParser
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type of this node
	 * 
	 * @param type
	 *            the type
	 * @see SimplePullParser
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Retrieves all attributes
	 * 
	 * @return all attributes, can be null
	 */
	public Hashtable getAttributes() {
		return this.attributes;
	}

	/**
	 * Sets all attributes
	 * 
	 * @param attributes
	 *            all attributes, can be null
	 */
	public void setAttributes(Hashtable attributes) {
		this.attributes = attributes;
	}

	/**
	 * Retrieves the specified attribute
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @return the attribute value, null if it is not known
	 */
	public String getAttribute(String attributeName) {
		if (this.attributes == null) {
			return null;
		}
		return (String) this.attributes.get(attributeName);
	}

	/**
	 * Retrieves the specified child's text
	 * 
	 * @param childName
	 *            the child's name
	 * @return either the child's text or null when the child is unknown
	 */
	public String getChildText(String childName) {
		XmlDomNode child = getChild(childName);
		return child != null ? child.getText() : null;
	}

	/**
	 * Retrieves all children of this node.
	 * 
	 * @return all children in an enumeration - may be empty but not null;
	 */
	public Enumeration getChildren() {
		return this.childList.elements();
	}

	/**
	 * Retrieves an XML representation of this XML node and its nested children.
	 * 
	 * @return the node as an XML string.
	 */
	public String toXmlString() {
		StringBuffer xml = new StringBuffer();
		appendXmlString("", xml);
		return xml.toString();
	}

	/**
	 * Appends this node's information in XML format to the given StringBuffer
	 * 
	 * @param indent
	 *            the current indentation
	 * @param xml
	 *            the buffer
	 */
	protected void appendXmlString(String indent, StringBuffer xml) {
		xml.append(indent).append('<').append(this.name);
		if (this.attributes != null) {
			Enumeration keys = this.attributes.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = (String) this.attributes.get(key);
				xml.append(' ').append(key).append("=\"").append(value).append(
						"\"");
			}
		}
		xml.append(">\n");
		String childIndent = ' ' + indent;
		if (this.text != null) {
			xml.append(childIndent).append(this.text).append('\n');
		}
		if (this.childList != null) {
			for (int i = 0; i < this.childList.size(); i++) {
				XmlDomNode node = (XmlDomNode) this.childList.elementAt(i);
				node.appendXmlString(childIndent, xml);
			}
		} 
		xml.append(indent).append("</").append(this.name).append(">\n");
	}
}
