package com.anubhavshukla.converter;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

import com.anubhavshukla.PropertiesToYamlConverter;
import com.anubhavshukla.dataobject.DataNode;
import com.anubhavshukla.exception.FileNotFoundException;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Test;

public class PropertiesToYamlConverterTest {
  
  private PropertiesToYamlConverter propertiesToYamlConverter;

  @Before
  public void setup() {
    this.propertiesToYamlConverter = new PropertiesToYamlConverter(new CommentIdentifier());
  }

  @Test
  public void singlePropertySingleNode() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-single-node.properties").toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }

  @Test
  public void singlePropertySingleNodeToYaml() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-single-node.properties").toURI());
    String yamlStr = propertiesToYamlConverter.propertiesFileToYaml(file);
    assertEquals("thisIsKey: thisIsValue" + lineSeparator(), yamlStr);
  }

  @Test
  public void singlePropertyTwoNode() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-two-node.properties").toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("thisIsValue", dataNode.getNode("keyPart1").getNode("keyPart2").getValue());
  }

  @Test
  public void singlePropertyTwoNodeToYaml() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-two-node.properties").toURI());
    String yamlStr = propertiesToYamlConverter.propertiesFileToYaml(file);
    assertEquals("keyPart1:" + lineSeparator() + "  keyPart2: thisIsValue" + lineSeparator(),
        yamlStr);
  }

  @Test
  public void singlePropertyMultilevel() throws URISyntaxException {
    File file = new File(
        PropertiesToYamlConverterTest.class.getResource("/single-property-multilevel.properties")
            .toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("value1", dataNode.getNode("keyPart1").getValue());
    assertEquals("value2", dataNode.getNode("keyPart1").getNode("keyPart2").getValue());
  }

  @Test
  public void singlePropertyMultilevelToYaml() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-multilevel.properties").toURI());
    String yamlStr = propertiesToYamlConverter.propertiesFileToYaml(file);
    assertEquals("keyPart1: value1" + lineSeparator() + "  keyPart2: value2" + lineSeparator(),
        yamlStr);
  }

  @Test
  public void multiPropertyMultilevel() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel.properties").toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
  }

  @Test
  public void multiPropertyMultilevelWithComments() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel-with-comments.properties").toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
  }

  @Test
  public void multiPropertyMultilevelWithInvalidProperty() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel-with-invalidproperty.properties")
            .toURI());
    DataNode dataNode = propertiesToYamlConverter.fileToDataNode(file);
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
  }

  @Test
  public void directoryOneParser() throws URISyntaxException {
    File file = new File(this.getClass().getResource("/directoryOne").toURI());
    DataNode dataNode = propertiesToYamlConverter.directoryToDataNode(file);
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }

  @Test(expected = FileNotFoundException.class)
  public void testFileInvalidPath() throws URISyntaxException {
    File file = new File("/file_path");
    propertiesToYamlConverter.fileToDataNode(file);
  }

  @Test(expected = FileNotFoundException.class)
  public void testDirectoryInvalidPath() throws URISyntaxException {
    File file = new File("/file_path");
    propertiesToYamlConverter.directoryToDataNode(file);
  }

  @Test
  public void testFileString() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel-with-invalidproperty.properties")
            .toURI());
    DataNode dataNode = propertiesToYamlConverter.toDataNode(file.getAbsolutePath());
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
  }

  @Test
  public void testDirectoryFileString() throws URISyntaxException {
    File file = new File(this.getClass().getResource("/directoryOne").toURI());
    DataNode dataNode = propertiesToYamlConverter.toDataNode(file.getAbsolutePath());
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }

  @Test(expected = FileNotFoundException.class)
  public void testDirectoryInvalidFileString() {
    propertiesToYamlConverter.toDataNode("/invalid/file/path");
  }
}