package com.anubhavshukla.p2y;

import com.anubhavshukla.p2y.converter.PropertiesToYamlConverter;
import com.anubhavshukla.p2y.exception.InvalidRequestException;
import com.anubhavshukla.p2y.utils.FileUtil;
import java.util.logging.Logger;

/**
 * Main class to run from command line.
 */
public class Main {

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String LOCATION_ARG_KEY = "-location=";
  private static final String USAGE_MESSAGE = "USAGE: java -jar properties-to-yaml-converter-<version>.jar -location=<file-path>";

  public static void main(String[] args) {
    try {
      String filePath = getLocation(args);
      PropertiesToYamlConverter propertiesToYamlConverter = new PropertiesToYamlConverter();
      String yamlContent = propertiesToYamlConverter.toYamlString(filePath);
      FileUtil.writeToYamlFile(filePath, yamlContent);
    } catch (Exception e) {
      System.out.println(USAGE_MESSAGE);
    }
  }

  private static String getLocation(String[] args) {
    for (String arg : args) {
      if (arg.startsWith(LOCATION_ARG_KEY)) {
        return arg.substring(LOCATION_ARG_KEY.length());
      }
    }
    LOGGER.fine("Specify file location in format: -location=<path>");
    throw new InvalidRequestException("File location not specified.");
  }
}