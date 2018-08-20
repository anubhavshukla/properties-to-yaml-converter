package com.anubhavshukla;

import com.anubhavshukla.exception.InvalidRequestException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class to run from command line.
 */
public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String LOCATION_ARG_KEY = "-location=";

  public static void main(String[] args) {
    try {
      String filePath = getLocation(args);
      PropertiesToYamlConverter propertiesToYamlConverter = new PropertiesToYamlConverter();
      System.out.println(propertiesToYamlConverter.fileToYamlString(new File(filePath)));
    } catch (Exception e) {
      LOGGER.log(Level.FINE, e.getMessage(), e);
    }
  }

  private static String getLocation(String[] args) {
    for (String arg : args) {
      if (arg.startsWith(LOCATION_ARG_KEY)) {
        return arg.substring(LOCATION_ARG_KEY.length() + 1);
      }
    }
    LOGGER.log(Level.SEVERE, "Specify file location in format: -location=<path>");
    throw new InvalidRequestException("File location not specified.");
  }
}