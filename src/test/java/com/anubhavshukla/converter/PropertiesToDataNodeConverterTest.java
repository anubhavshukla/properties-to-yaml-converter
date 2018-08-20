package com.anubhavshukla.converter;

import static org.junit.Assert.assertEquals;

import com.anubhavshukla.dataobject.DataNode;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Test;

public class PropertiesToDataNodeConverterTest {

  private PropertiesToDataNodeConverter propertiesToDataNodeConverter;

  @Before
  public void setup() {
    this.propertiesToDataNodeConverter = new PropertiesToDataNodeConverter();
  }

  @Test
  public void singlePropertySingleNode() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-single-node.properties").toURI());
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }

  @Test
  public void singlePropertyTwoNode() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/single-property-two-node.properties").toURI());
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
    assertEquals("thisIsValue", dataNode.getNode("keyPart1").getNode("keyPart2").getValue());
  }

  @Test
  public void singlePropertyMultilevel() throws URISyntaxException {
    File file = new File(
        PropertiesToYamlConverterTest.class.getResource("/single-property-multilevel.properties")
            .toURI());
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
    assertEquals("value1", dataNode.getNode("keyPart1").getValue());
    assertEquals("value2", dataNode.getNode("keyPart1").getNode("keyPart2").getValue());
  }

  @Test
  public void multiPropertyMultilevel() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel.properties").toURI());
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
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
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
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
    DataNode dataNode = propertiesToDataNodeConverter.fileToDataNode(file);
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
    DataNode dataNode = propertiesToDataNodeConverter.directoryToDataNode(file);
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }

  @Test
  public void testFileString() throws URISyntaxException {
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel-with-invalidproperty.properties")
            .toURI());
    DataNode dataNode = propertiesToDataNodeConverter.toDataNode(file.getAbsolutePath());
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
    DataNode dataNode = propertiesToDataNodeConverter.toDataNode(file.getAbsolutePath());
    assertEquals("value11", dataNode.getNode("keyPart11").getValue());
    assertEquals("value12", dataNode.getNode("keyPart11").getNode("keyPart12").getValue());
    assertEquals("value21", dataNode.getNode("keyPart21").getValue());
    assertEquals("value22", dataNode.getNode("keyPart21").getNode("keyPart22").getValue());
    assertEquals("value23",
        dataNode.getNode("keyPart21").getNode("keyPart22").getNode("keyPart23").getValue());
    assertEquals("thisIsValue", dataNode.getNode("thisIsKey").getValue());
  }
}