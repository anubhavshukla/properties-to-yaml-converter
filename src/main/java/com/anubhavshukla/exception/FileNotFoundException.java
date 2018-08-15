package com.anubhavshukla.exception;

/**
 * Thrown if provided properties file is not found.
 */
public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException(String message) {
    super(message);
  }

}
