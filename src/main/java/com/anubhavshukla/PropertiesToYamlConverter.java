package com.anubhavshukla;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import com.anubhavshukla.converter.CommentIdentifier;
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
import java.util.stream.IntStream;

public class PropertiesToYamlConverter {

  private static final Logger LOGGER = Logger.getLogger(PropertiesToYamlConverter.class.getName());

  private final CommentIdentifier commentIdentifier;

  public PropertiesToYamlConverter(CommentIdentifier commentIdentifier) {
    this.commentIdentifier = commentIdentifier;
  }

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
              fileToPropertiesList(toPropertiesFile(propertiesFolder, file)));
        });
    return propertiesListToYaml(propertiesList);
  }

  private File toPropertiesFile(File propertiesFile, String file) {
    return new File(propertiesFile.getAbsolutePath() + File.separator + file);
  }

  public String propertiesFileToYaml(File propertiesFile) {
    DataNode dataNode = fileToDataNode(propertiesFile);
    return dataNodeToYamlString(dataNode);
  }

  public DataNode fileToDataNode(File propertiesFile) {
    if (!propertiesFile.exists()) {
      LOGGER.log(Level.SEVERE, "File does not exist");
      throw new FileNotFoundException("File not found.");
    }

    return propertiesListToYaml(fileToPropertiesList(propertiesFile));
  }

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

  private DataNode propertiesListToYaml(List<String> propertiesList) {
    DataNode rootNode = DataNode.getInstance();
    propertiesList.forEach(s -> processProperty(s, rootNode));
    System.out.println(rootNode);
    return rootNode;
  }

  private DataNode processProperty(String propertyLine, DataNode rootNode) {
    if (!propertyLine.contains("=")) {
      LOGGER.log(Level.FINE, "Invalid property line: " + propertyLine);
      return rootNode;
    }
    String key = propertyLine.substring(0, propertyLine.indexOf('='));
    String value = propertyLine.substring(propertyLine.indexOf('=') + 1);
    return processProperty(key, value, rootNode);
  }

  private DataNode processProperty(String key, String value, DataNode dataNode) {
    String[] keyParts = key.split("\\.");
    DataNode currentDataNode = dataNode;
    for (int i = 0; i < keyParts.length; i++) {
      currentDataNode = currentDataNode.getOrPut(keyParts[i], i);
    }
    currentDataNode.setValue(value);
    return dataNode;
  }

  private String dataNodeToYamlString(DataNode startNode) {
    StringBuilder stringBuffer = new StringBuilder();
    startNode.getSubMap().forEach((s, dataNode) -> {
      stringBuffer.append(getSpaceString(dataNode.getTabIndex())).append(s).append(":");
      if (dataNode.getValue() != null) {
        stringBuffer.append(" ").append(dataNode.getValue()).append(System.lineSeparator());
      } else {
        stringBuffer.append(System.lineSeparator());
      }
      stringBuffer.append(dataNodeToYamlString(dataNode));
    });
    return stringBuffer.toString();
  }

  private StringBuffer getSpaceString(int tabIndex) {
    StringBuffer stringBuffer = new StringBuffer();
    IntStream.range(0, tabIndex * 2).forEach(value -> stringBuffer.append(" "));
    return stringBuffer;
  }
}
