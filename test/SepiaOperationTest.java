import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * A class that tests the SepiaOperation class via the ImageProcessor interface.
 */
public class SepiaOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testSepiaSimpleImage() {
    int[][][] pixels = {
            {{100, 150, 200}, {50, 75, 100}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = new int[1][2][3];
    for (int x = 0; x < 2; x++) {
      int[] rgb = pixels[0][x];
      int red = clamp((int) Math.round(
              rgb[0] * 0.393 + rgb[1] * 0.769 + rgb[2] * 0.189), 0, 255);
      int green = clamp((int) Math.round(
              rgb[0] * 0.349 + rgb[1] * 0.686 + rgb[2] * 0.168), 0, 255);
      int blue = clamp((int) Math.round(
              rgb[0] * 0.272 + rgb[1] * 0.534 + rgb[2] * 0.131), 0, 255);
      expectedPixels[0][x][0] = red;
      expectedPixels[0][x][1] = green;
      expectedPixels[0][x][2] = blue;
    }

    int[][][] actualPixels = result.getPixels();

    for (int y = 0; y < 1; y++) {
      for (int x = 0; x < 2; x++) {
        for (int i = 0; i < 3; i++) {
          assertEquals(expectedPixels[y][x][i], actualPixels[y][x][i], 1);
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaWithNullImage() {
    imageProcessor.sepia(null);
  }

  @Test
  public void testSepia1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[] rgb = pixels[0][0];
    int red = clamp((int) Math.round(
            rgb[0] * 0.393 + rgb[1] * 0.769 + rgb[2] * 0.189), 0, 255);
    int green = clamp((int) Math.round(
            rgb[0] * 0.349 + rgb[1] * 0.686 + rgb[2] * 0.168), 0, 255);
    int blue = clamp((int) Math.round(
            rgb[0] * 0.272 + rgb[1] * 0.534 + rgb[2] * 0.131), 0, 255);

    int[][][] expectedPixels = {{{red, green, blue}}};
    int[][][] actualPixels = result.getPixels();

    for (int i = 0; i < 3; i++) {
      assertEquals(expectedPixels[0][0][i], actualPixels[0][0][i], 1);
    }
  }

  @Test
  public void testSepiaPixelValueRanges() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] resultPixels = result.getPixels();
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        for (int i = 0; i < 3; i++) {
          int value = resultPixels[y][x][i];
          assertTrue("Pixel value should be >= 0", value >= 0);
          assertTrue("Pixel value should be <= 255", value <= 255);
        }
      }
    }
  }

  @Test
  public void testSepiaWithAllMaxValues() {
    int[][][] pixels = {
            {{255, 255, 255}}
    };
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = {{{255, 255, 238}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSepiaWithAllMinValues() {
    int[][][] pixels = {
            {{0, 0, 0}}
    };
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = {{{0, 0, 0}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSepiaOnGrayscaleImage() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = {
            {{135, 120, 93}, {203, 180, 140}}
    };

    assertArrayEqualsWithTolerance(expectedPixels, result.getPixels(), 1);
  }

  @Test
  public void testSepiaOnLargeImage() {
    int[][][] pixels = new int[5][5][3];
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        pixels[y][x][0] = 100;
        pixels[y][x][1] = 150;
        pixels[y][x][2] = 200;
      }
    }
    Image image = new Image(5, 5, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    assertNotNull(result);
    assertEquals(5, result.getWidth());
    assertEquals(5, result.getHeight());
  }

  @Test
  public void testSepiaOnNonSquareImage() {
    int[][][] pixels = {
            {{100, 150, 200}},
            {{50, 100, 150}}
    };
    Image image = new Image(1, 2, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = {
            {{192, 171, 134}},
            {{124, 111, 86}}
    };

    assertArrayEqualsWithTolerance(expectedPixels, result.getPixels(), 1);
  }

  @Test
  public void testSepiaOnMixedPixelValues() {
    int[][][] pixels = {
            {{100, 50, 150}, {200, 100, 50}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sepia(image);

    int[][][] expectedPixels = {
            {{106, 95, 74}, {164, 146, 114}}
    };

    assertArrayEqualsWithTolerance(expectedPixels, result.getPixels(), 3);
  }

  /**
   * Asserts that two 3D arrays are equal within a given tolerance.
   *
   * @param expected  3D array of expected values
   * @param actual    3D array of actual values
   * @param tolerance Maximum difference allowed between expected and actual values
   */
  public void assertArrayEqualsWithTolerance(int[][][] expected, int[][][] actual, int tolerance) {
    for (int y = 0; y < expected.length; y++) {
      for (int x = 0; x < expected[0].length; x++) {
        for (int i = 0; i < 3; i++) {
          assertTrue(
                  "Pixel value at [" + y + "][" + x + "][" + i + "] differed. " +
                          "Expected: " + expected[y][x][i] + ", Actual: " + actual[y][x][i],
                  Math.abs(expected[y][x][i] - actual[y][x][i]) <= tolerance
          );
        }
      }
    }
  }

  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }
}
