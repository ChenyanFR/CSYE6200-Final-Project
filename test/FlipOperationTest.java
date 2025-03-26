import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests the FlipOperation class via the ImageProcessor interface.
 */
public class FlipOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();


  @Test
  public void testHorizontalFlip() {
    int[][][] pixels = {
            {{1, 2, 3}, {4, 5, 6}}
    };
    Image image = new Image(2, 1, 255, pixels);


    ImageInterface result = imageProcessor.flipHorizontal(image);

    int[][][] expectedPixels = {
            {{4, 5, 6}, {1, 2, 3}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testHorizontalFlip1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.flipHorizontal(image);

    assertArrayEquals(pixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipWithNullImage() {
    imageProcessor.flipHorizontal(null);
  }

  @Test
  public void testHorizontalFlip3x3Image() {
    int[][][] pixels = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    Image image = new Image(3, 3, 255, pixels);


    ImageInterface result = imageProcessor.flipHorizontal(image);

    int[][][] expectedPixels = {
            {{3, 3, 3}, {2, 2, 2}, {1, 1, 1}},
            {{6, 6, 6}, {5, 5, 5}, {4, 4, 4}},
            {{9, 9, 9}, {8, 8, 8}, {7, 7, 7}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testHorizontalFlipOnLargerImage() {
    int[][][] pixels = new int[5][5][3];
    int value = 1;
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        pixels[y][x][0] = value;
        pixels[y][x][1] = value + 1;
        pixels[y][x][2] = value + 2;
        value++;
      }
    }

    Image image = new Image(5, 5, 255, pixels);


    ImageInterface result = imageProcessor.flipHorizontal(image);

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        assertArrayEquals(pixels[y][x], result.getPixels()[y][5 - x - 1]);
      }
    }
  }

  @Test
  public void testVerticalFlip() {
    int[][][] pixels = {
            {{1, 2, 3}},
            {{4, 5, 6}}
    };
    Image image = new Image(1, 2, 255, pixels);


    ImageInterface result = imageProcessor.flipVertical(image);

    int[][][] expectedPixels = {
            {{4, 5, 6}},
            {{1, 2, 3}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testVerticalFlip1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);


    ImageInterface result = imageProcessor.flipVertical(image);

    assertArrayEquals(pixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipWithNullImage() {
    imageProcessor.flipVertical(null);
  }

  @Test
  public void testVerticalFlip3x3Image() {
    int[][][] pixels = {
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}}
    };
    Image image = new Image(3, 3, 255, pixels);


    ImageInterface result = imageProcessor.flipVertical(image);

    int[][][] expectedPixels = {
            {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}},
            {{4, 4, 4}, {5, 5, 5}, {6, 6, 6}},
            {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testVerticalFlipOnLargerImage() {
    int[][][] pixels = new int[5][5][3];
    int value = 1;
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        pixels[y][x][0] = value;
        pixels[y][x][1] = value + 1;
        pixels[y][x][2] = value + 2;
        value++;
      }
    }

    Image image = new Image(5, 5, 255, pixels);


    ImageInterface result = imageProcessor.flipVertical(image);

    for (int y = 0; y < 5; y++) {
      assertArrayEquals(pixels[y], result.getPixels()[5 - y - 1]);
    }
  }

  @Test
  public void testFlipOnGrayscaleImage() {
    int[][][] pixels = {
            {{50, 50, 50}, {100, 100, 100}},
            {{150, 150, 150}, {200, 200, 200}}
    };
    Image image = new Image(2, 2, 255, pixels);


    ImageInterface hResult = imageProcessor.flipHorizontal(image);
    ImageInterface vResult = imageProcessor.flipVertical(image);

    int[][][] expectedHorizontalFlip = {
            {{100, 100, 100}, {50, 50, 50}},
            {{200, 200, 200}, {150, 150, 150}}
    };

    int[][][] expectedVerticalFlip = {
            {{150, 150, 150}, {200, 200, 200}},
            {{50, 50, 50}, {100, 100, 100}}
    };


    assertArrayEquals(expectedHorizontalFlip, hResult.getPixels());
    assertArrayEquals(expectedVerticalFlip, vResult.getPixels());
  }
}
