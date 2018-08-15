package com.anubhavshukla.exception;

/**
 * Thrown in case of invalid invocation of tool from command line.
 */
public class InvalidRequestException extends RuntimeException {

  public InvalidRequestException(String message) {
    super(message);
  }

}
