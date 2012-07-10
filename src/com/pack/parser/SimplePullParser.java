package com.pack.parser;
/**
 * This interface represents a simple pull parser which can be used in many applications.
 * 
 */
public interface SimplePullParser
{
  /**
   * Return value of getType before first call to next()
   */
  int START_DOCUMENT = 0;

  /**
   * Signal logical end of xml document
   */
  int END_DOCUMENT = 1;

  /**
   * Start tag was just read
   */
  int START_TAG = 2;

  /**
   * End tag was just read
   */
  int END_TAG = 3;

  /**
   * Text was just read
   */
  int TEXT = 4;

  int CDSECT = 5;

  int ENTITY_REF = 6;

  int LEGACY = 999;

  int getType();

  /**
   * Retrieves the text at the current position in the data the parser acts on.
   * 
   * @return the text
   */
  String getText();

  /**
   * Retrieves the name of a tag.
   * 
   * @return the name
   */
  String getName();

  /**
   * Retrieves the number of attributes associated with the current tag.
   * 
   * @return the number of attributes
   */
  int getAttributeCount();

  /**
   * Retrieves the name of an attribute associated with the current tag.
   * 
   * @param index the index of the attribute
   * @return the name
   */
  String getAttributeName(int index);

  /**
   * Retrieves the value of an attribute associated with the current tag.
   * 
   * @param index the index of the attribute
   * @return the value
   */
  String getAttributeValue(int index);

  /**
   * Retrieves the value of an attribute associated with the current tag.
   * 
   * @param name the name of the attribute
   * @return the value, or null of attribute doesn't exist
   */
  String getAttributeValue(String name);

  /**
   * Reads the input data up to the next tag or text section. It returns
   * the type of data the next block represents.
   * 
   * @return the data type
   */
  int next();

}
