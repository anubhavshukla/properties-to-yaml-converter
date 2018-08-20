package com.anubhavshukla.utils;

import com.anubhavshukla.exception.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());
  private static final String YML_EXTENSION = ".yml";

  public static String getYamlFileName(String propertiesFile) {
    File file = new File(propertiesFile);
    if (!file.exists()) {
      LOGGER.severe("File does not exist.");
      throw new FileNotFoundException("File does not exist.");
    }

    if (file.isFile()) {
      String filePath = file.getAbsolutePath();
      return filePath.subSequence(0, filePath.length() - 11) + YML_EXTENSION;
    } else {
      return file.getAbsolutePath() + File.separator + "combined" + YML_EXTENSION;
    }
  }

  /**
   * Writes content to given filePath.
   */
  public static void write(String filePath, String content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(content);
    }
  }

  /**
   * Creates YAML file for given properties file and writes content to it.
   */
  public static void writeToYamlFile(String propertiesFilePath, String content) throws IOException {
    String yamlFilePath = getYamlFileName(propertiesFilePath);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(yamlFilePath))) {
      writer.write(content);
    }
  }
}
