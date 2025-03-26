import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests the BrightenOperation class via the ImageProcessor interface.
 */
public class BrightenOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testBrightenWithPositiveIncrement() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 30);

    int[][][] expectedPixels = {
            {{130, 130, 130}, {180, 180, 180}},
            {{230, 230, 230}, {255, 255, 255}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenWithNegativeIncrement() {
    int[][][] pixels = {
            {{100, 100, 100}, {50, 50, 50}},
            {{0, 0, 0}, {30, 30, 30}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, -20);

    int[][][] expectedPixels = {
            {{80, 80, 80}, {30, 30, 30}},
            {{0, 0, 0}, {10, 10, 10}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenClampingAtMaxValue() {
    int[][][] pixels = {
            {{240, 240, 240}, {250, 250, 250}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 20);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {255, 255, 255}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenClampingAtMinValue() {
    int[][][] pixels = {
            {{10, 10, 10}, {5, 5, 5}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, -20);

    int[][][] expectedPixels = {
            {{0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenWithNullImage() {
    imageProcessor.brighten(null, 10);
  }

  @Test
  public void testBrighten1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 50);

    int[][][] expectedPixels = {{{150, 200, 250}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenWithZeroIncrement() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 0);

    assertArrayEquals(pixels, result.getPixels());
  }

  @Test
  public void testBrightenWithMaxIncrement() {
    int[][][] pixels = {
            {{0, 0, 0}, {50, 50, 50}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 255);

    int[][][] expectedPixels = {
            {{255, 255, 255}, {255, 255, 255}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenWithMinIncrement() {
    int[][][] pixels = {
            {{255, 255, 255}, {200, 200, 200}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, -255);

    int[][][] expectedPixels = {
            {{0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenEdgePixelValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{127, 127, 127}, {128, 128, 128}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 5);

    int[][][] expectedPixels = {
            {{5, 5, 5}, {255, 255, 255}},
            {{132, 132, 132}, {133, 133, 133}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenOnLargeImage() {
    int[][][] pixels = new int[10][10][3];
    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        pixels[y][x][0] = 50;
        pixels[y][x][1] = 100;
        pixels[y][x][2] = 150;
      }
    }
    Image image = new Image(10, 10, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 50);

    int[][][] expectedPixels = new int[10][10][3];
    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        expectedPixels[y][x][0] = 100;
        expectedPixels[y][x][1] = 150;
        expectedPixels[y][x][2] = 200;
      }
    }
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBrightenGrayscaleImage() {
    int[][][] pixels = {
            {{100, 100, 100}, {50, 50, 50}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.brighten(image, 30);

    int[][][] expectedPixels = {
            {{130, 130, 130}, {80, 80, 80}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }
}
