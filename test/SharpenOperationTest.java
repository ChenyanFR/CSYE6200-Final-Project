import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the SharpenOperation class via the ImageProcessor interface.
 */
public class SharpenOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testSharpenSimpleImage() {
    int[][][] pixels = {
            {{50, 50, 50}, {100, 100, 100}},
            {{150, 150, 150}, {200, 200, 200}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    assertNotNull(result);
    assertEquals(2, result.getWidth());
    assertEquals(2, result.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenWithNullImage() {
    imageProcessor.sharpen(null);
  }

  @Test
  public void testSharpen1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    assertArrayEquals(pixels, result.getPixels());
  }

  @Test
  public void testSharpenPixelValueRanges() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

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
  public void testSharpenWithAllMaxValues() {
    int[][][] pixels = {
            {{255, 255, 255}}
    };
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {{{255, 255, 255}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSharpenWithAllMinValues() {
    int[][][] pixels = {
            {{0, 0, 0}}
    };
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {{{0, 0, 0}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSharpenGrayscaleImage() {
    int[][][] pixels = {
            {{50, 50, 50}, {100, 100, 100}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {
            {{75, 75, 75}, {113, 113, 113}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSharpenOnLargeImage() {
    int[][][] pixels = new int[5][5][3];
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        pixels[y][x][0] = 100;
        pixels[y][x][1] = 150;
        pixels[y][x][2] = 200;
      }
    }
    Image image = new Image(5, 5, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    assertNotNull(result);
    assertEquals(5, result.getWidth());
    assertEquals(5, result.getHeight());
  }

  @Test
  public void testSharpenOnNonSquareImage() {
    int[][][] pixels = {
            {{100, 150, 200}},
            {{50, 100, 150}}
    };
    Image image = new Image(1, 2, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    assertNotNull(result);
    assertEquals(1, result.getWidth());
    assertEquals(2, result.getHeight());
  }

  @Test
  public void testSharpenOnMixedPixelValues() {
    int[][][] pixels = {
            {{50, 50, 50}, {200, 200, 200}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {
            {{100, 100, 100}, {213, 213, 213}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSharpenOnFullyBlackImage() {
    int[][][] pixels = {
            {{0, 0, 0}, {0, 0, 0}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {
            {{0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testSharpenOnFullyWhiteImage() {
    int[][][] pixels = {
            {{255, 255, 255}, {255, 255, 255}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.sharpen(image);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {255, 255, 255}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

}
