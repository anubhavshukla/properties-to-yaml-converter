package com.anubhavshukla.p2y.exception;

/**
 * Thrown if provided properties file is not found.
 */
public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException(String message) {
    super(message);
  }

}
