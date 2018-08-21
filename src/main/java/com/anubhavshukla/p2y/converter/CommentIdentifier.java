package com.anubhavshukla.p2y.converter;

/**
 * Identifies if the given properties line is a comment or not. Currently supports only simple Java
 * properties file comments. i.e. lines beginning with character '#'
 */
public class CommentIdentifier {

  /**
   * A property string beginning with # is considered comment.
   */
  public boolean isComment(String propertyStr) {
    return propertyStr.startsWith("#");
  }
}
