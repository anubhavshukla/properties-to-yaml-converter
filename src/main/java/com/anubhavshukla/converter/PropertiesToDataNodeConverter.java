package com.anubhavshukla.converter;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import com.anubhavshukla.dataobject.DataNode;
import com.anubhavshukla.exception.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads properties file(s) and stores data in intermediate transient form as DataNode(s)
 */
public class PropertiesToDataNodeConverter {

  private static final Logger LOGGER = Logger
      .getLogger(PropertiesToDataNodeConverter.class.getName());
  public static final char PROPERTY_SEPARATOR = '=';

  private final CommentIdentifier commentIdentifier;

  public PropertiesToDataNodeConverter(CommentIdentifier commentIdentifier) {
    this.commentIdentifier = commentIdentifier;
  }

  /**
   * Accepts a file name and parses it to DataNode. Detection of file type i.e. file or folder is
   * done here.
   */
  public DataNode toDataNode(String propertiesFilePath) {
    File propertiesFile = new File(propertiesFilePath);
    if (!propertiesFile.exists()) {
      LOGGER.log(Level.SEVERE, "File does not exist: " + propertiesFilePath);
      throw new FileNotFoundException("File not found.");
    }

    if (propertiesFile.isDirectory()) {
      return directoryToDataNode(propertiesFile);
    } else {
      return fileToDataNode(propertiesFile);
    }
  }

  /**
   * Parses all the Properties files in a directory and converts them to a single DataNode.
   */
  public DataNode directoryToDataNode(File propertiesFolder) {
    if (!propertiesFolder.exists()) {
      LOGGER.log(Level.SEVERE, "File does not exist: ");
      throw new FileNotFoundException("File not found.");
    }

    List<String> propertiesList = new LinkedList<>();
    stream(requireNonNull(propertiesFolder.list()))
        .filter(s -> s.endsWith(".properties"))
        .forEach(file -> {
          propertiesList.addAll(
              fileToPropertiesList(getPropertiesFile(propertiesFolder, file)));
        });
    return propertiesListToDataNode(propertiesList);
  }

  /**
   * Generates File object for a Property file in the given directory.
   */
  private File getPropertiesFile(File propertiesFile, String file) {
    return new File(propertiesFile.getAbsolutePath() + File.separator + file);
  }

  /**
   * Converts a properties file into intermediate transient storage of DataNode.
   */
  public DataNode fileToDataNode(File propertiesFile) {
    if (!propertiesFile.exists()) {
      LOGGER.log(Level.SEVERE, "File does not exist");
      throw new FileNotFoundException("File not found.");
    }

    return propertiesListToDataNode(fileToPropertiesList(propertiesFile));
  }

  /**
   * Extracts all the valid property lines from a Properties file.
   */
  private List<String> fileToPropertiesList(File propertiesFile) {
    List<String> propertiesList = new LinkedList<>();
    try {
      BufferedReader b = new BufferedReader(new FileReader(propertiesFile));
      String readLine = "";

      while ((readLine = b.readLine()) != null) {
        if (commentIdentifier.isComment(readLine)) {
          LOGGER.log(Level.FINE, "Ignoring comment line: " + readLine);
        } else {
          propertiesList.add(readLine);
        }
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Error parsing file", e);
    }
    return propertiesList;
  }

  /**
   * Converts list of properties strings to DataNode.
   */
  private DataNode propertiesListToDataNode(List<String> propertiesList) {
    LOGGER.fine("Converting properties list to DataNode: " + propertiesList);
    DataNode rootNode = DataNode.getInstance();
    propertiesList.forEach(s -> processProperty(s, rootNode));
    LOGGER.fine("Generated DataNode: " + rootNode);
    return rootNode;
  }

  /**
   * Parses a given property string and extracts key-value to create DataNode.
   */
  private DataNode processProperty(String propertyLine, DataNode rootNode) {
    LOGGER.fine("Processing property string: " + propertyLine);
    if (!propertyLine.contains("=")) {
      LOGGER.log(Level.FINE, "Invalid property line: " + propertyLine);
      return rootNode;
    }
    int indexOfPropertySeparator = propertyLine.indexOf(PROPERTY_SEPARATOR);
    String key = propertyLine.substring(0, indexOfPropertySeparator);
    String value = propertyLine.substring(indexOfPropertySeparator + 1);
    return processProperty(key, value, rootNode);
  }

  /**
   * Add property to DataNode object.
   */
  private DataNode processProperty(String key, String value, DataNode dataNode) {
    String[] keyParts = key.split("\\.");
    DataNode currentDataNode = dataNode;
    for (int i = 0; i < keyParts.length; i++) {
      currentDataNode = currentDataNode.getOrPut(keyParts[i], i);
    }
    currentDataNode.setValue(value);
    return dataNode;
  }
}
