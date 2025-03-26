import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * A class that tests the BlurOperation via the ImageProcessor interface.
 */
public class BlurOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testBlurSimpleImage() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    assertNotNull(result);
    assertEquals(2, result.getWidth());
    assertEquals(2, result.getHeight());
  }

  @Test
  public void testBlurWithNullImage() {
    try {
      imageProcessor.blur(null);
      fail("Expected IllegalArgumentException for null image");
    } catch (IllegalArgumentException e) {
      assertEquals("Image cannot be null", e.getMessage());
    }
  }

  @Test
  public void testBlur1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    int[][][] expectedPixels = {{{25, 38, 50}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBlurEdgePixels() {
    int[][][] pixels = new int[3][3][3];
    int value = 10;
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        pixels[y][x][0] = value;
        pixels[y][x][1] = value;
        pixels[y][x][2] = value;
        value += 10;
      }
    }
    Image image = new Image(3, 3, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    assertNotNull(result);
  }

  @Test
  public void testBlurPixelValueRanges() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

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
  public void testBlur3x3Image() {
    int[][][] pixels = {
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}}
    };
    Image image = new Image(3, 3, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    int[][][] expectedPixels = {
            {{56, 84, 113}, {75, 113, 150}, {56, 84, 113}},
            {{75, 113, 150}, {100, 150, 200}, {75, 113, 150}},
            {{56, 84, 113}, {75, 113, 150}, {56, 84, 113}}
    };

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBlurWithMaxPixelValues() {
    int[][][] pixels = {
            {{255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    int[][][] resultPixels = result.getPixels();
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        for (int i = 0; i < 3; i++) {
          assertTrue("Pixel value should be " +
                  "<= 255", resultPixels[y][x][i] <= 255);
        }
      }
    }
  }

  @Test
  public void testBlurWithMinPixelValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    int[][][] resultPixels = result.getPixels();
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        for (int i = 0; i < 3; i++) {
          assertEquals("Pixel value should remain " +
                  "at 0", 0, resultPixels[y][x][i]);
        }
      }
    }
  }

  @Test
  public void testBlurOnNonSquareImage() {
    int[][][] pixels = {
            {{100, 150, 200}},
            {{200, 250, 100}}
    };
    Image image = new Image(1, 2, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    assertNotNull(result);
  }

  @Test
  public void testBlurOnLargerImage() {
    int[][][] pixels = new int[10][10][3];
    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        pixels[y][x][0] = 100;
        pixels[y][x][1] = 150;
        pixels[y][x][2] = 200;
      }
    }
    Image image = new Image(10, 10, 255, pixels);


    ImageInterface result = imageProcessor.blur(image);

    assertNotNull(result);
    assertEquals(10, result.getWidth());
    assertEquals(10, result.getHeight());
  }
}
