package com.anubhavshukla.p2y.converter;

import static org.junit.Assert.assertEquals;

import com.anubhavshukla.p2y.exception.FileNotFoundException;
import com.anubhavshukla.p2y.exception.InvalidRequestException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class PropertiesToYamlConverterTest {

  private PropertiesToYamlConverter propertiesToYamlConverter;

  @Before
  public void setup() {
    this.propertiesToYamlConverter = new PropertiesToYamlConverter();
  }

  @Test
  public void toYamlStringWithValidPath() {
    //Given
    String filePath = this.getClass()
        .getResource("/multi-property-multilevel-with-invalidproperty.properties").getFile();

    //When
    String result = propertiesToYamlConverter.toYamlString(filePath);

    //Then
    assertEquals(expectedResult("/multi-property-multilevel-with-invalidproperty.yml"), result);
  }

  @Test(expected = FileNotFoundException.class)
  public void toYamlStringWithInvalidPath() {
    //Given
    String filePath = "/invalid-file-path.properties";

    //When
    propertiesToYamlConverter.toYamlString(filePath);
  }

  @Test
  public void fileToYamlStringWithValidPath() {
    //Given
    File file = new File(this.getClass()
        .getResource("/multi-property-multilevel-with-invalidproperty.properties").getFile());

    //When
    String result = propertiesToYamlConverter.fileToYamlString(file);

    //Then
    assertEquals(expectedResult("/multi-property-multilevel-with-invalidproperty.yml"), result);
  }

  @Test(expected = FileNotFoundException.class)
  public void fileToYamlStringWithFileNotFound() {
    //Given
    File file = new File("/invalid-file-path.properties");

    //When
    propertiesToYamlConverter.fileToYamlString(file);
  }

  @Test(expected = InvalidRequestException.class)
  public void fileToYamlStringWithInvalidDirectoryFile() {
    //Given
    File file = new File(this.getClass().getResource("/directoryOne").getFile());

    //When
    propertiesToYamlConverter.fileToYamlString(file);
  }

  @Test
  public void directoryToYamlStringWithValidPath() {
    //Given
    File file = new File(this.getClass()
        .getResource("/directoryOne").getFile());

    //When
    String result = propertiesToYamlConverter.directoryToYamlString(file);

    //Then
    assertEquals(expectedResult("/directoryOne/result.yml"), result);
  }

  @Test(expected = FileNotFoundException.class)
  public void directoryToYamlStringWithFileNotFound() {
    //Given
    File file = new File("/invalid-file-path.properties");

    //When
    propertiesToYamlConverter.directoryToYamlString(file);
  }

  @Test(expected = InvalidRequestException.class)
  public void directoryToYamlStringWithInvalidDirectoryFile() {
    //Given
    File file = new File(
        this.getClass().getResource("/multi-property-multilevel.properties").getFile());

    //When
    propertiesToYamlConverter.directoryToYamlString(file);
  }

  private String expectedResult(String fileName) {
    try (InputStream is = this.getClass().getResourceAsStream(fileName)) {
      Scanner s = new Scanner(is).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}