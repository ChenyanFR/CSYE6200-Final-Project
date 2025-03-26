import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests the CombineOperation via the ImageProcessor interface.
 */
public class CombineOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testCombineOperation() {
    int[][][] redPixels = {{{100, 0, 0}, {150, 0, 0}}};
    int[][][] greenPixels = {{{0, 50, 0}, {0, 75, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}, {0, 0, 250}}};

    Image redImage = new Image(2, 1, 255, redPixels);
    Image greenImage = new Image(2, 1, 255, greenPixels);
    Image blueImage = new Image(2, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {
            {{100, 50, 200}, {150, 75, 250}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCombineWithNullImages() {
    imageProcessor.combine(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineWithDifferentDimensions() {
    int[][][] redPixels = {{{100, 0, 0}}};
    int[][][] greenPixels = {{{0, 50, 0}, {0, 75, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}}};

    Image redImage = new Image(1, 1, 255, redPixels);
    Image greenImage = new Image(2, 1, 255, greenPixels);
    Image blueImage = new Image(1, 1, 255, bluePixels);

    imageProcessor.combine(redImage, greenImage, blueImage);
  }

  @Test
  public void testCombine1x1Images() {
    int[][][] redPixels = {{{100, 0, 0}}};
    int[][][] greenPixels = {{{0, 150, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}}};

    Image redImage = new Image(1, 1, 255, redPixels);
    Image greenImage = new Image(1, 1, 255, greenPixels);
    Image blueImage = new Image(1, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {{{100, 150, 200}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testCombineWithAllZeros() {
    int[][][] redPixels = {{{0, 0, 0}}};
    int[][][] greenPixels = {{{0, 0, 0}}};
    int[][][] bluePixels = {{{0, 0, 0}}};

    Image redImage = new Image(1, 1, 255, redPixels);
    Image greenImage = new Image(1, 1, 255, greenPixels);
    Image blueImage = new Image(1, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {{{0, 0, 0}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testCombineWithAllMaxValues() {
    int[][][] redPixels = {{{255, 0, 0}}};
    int[][][] greenPixels = {{{0, 255, 0}}};
    int[][][] bluePixels = {{{0, 0, 255}}};

    Image redImage = new Image(1, 1, 255, redPixels);
    Image greenImage = new Image(1, 1, 255, greenPixels);
    Image blueImage = new Image(1, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {{{255, 255, 255}}};
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testCombineWithMixedValues() {
    int[][][] redPixels = {{{50, 0, 0}, {100, 0, 0}}};
    int[][][] greenPixels = {{{0, 50, 0}, {0, 100, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}, {0, 0, 250}}};

    Image redImage = new Image(2, 1, 255, redPixels);
    Image greenImage = new Image(2, 1, 255, greenPixels);
    Image blueImage = new Image(2, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {
            {{50, 50, 200}, {100, 100, 250}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testCombineWithLargeImage() {
    int[][][] redPixels = new int[10][10][3];
    int[][][] greenPixels = new int[10][10][3];
    int[][][] bluePixels = new int[10][10][3];

    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        redPixels[y][x][0] = 100;
        greenPixels[y][x][1] = 150;
        bluePixels[y][x][2] = 200;
      }
    }

    Image redImage = new Image(10, 10, 255, redPixels);
    Image greenImage = new Image(10, 10, 255, greenPixels);
    Image blueImage = new Image(10, 10, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

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
  public void testCombineOnNonSquareImages() {
    int[][][] redPixels = {{{50, 0, 0}, {100, 0, 0}, {150, 0, 0}}};
    int[][][] greenPixels = {{{0, 50, 0}, {0, 100, 0}, {0, 150, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}, {0, 0, 250}, {0, 0, 255}}};

    Image redImage = new Image(3, 1, 255, redPixels);
    Image greenImage = new Image(3, 1, 255, greenPixels);
    Image blueImage = new Image(3, 1, 255, bluePixels);


    ImageInterface result = imageProcessor.combine(redImage, greenImage, blueImage);

    int[][][] expectedPixels = {
            {{50, 50, 200}, {100, 100, 250}, {150, 150, 255}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineDifferentMaxValues() {
    int[][][] redPixels = {{{100, 0, 0}}};
    int[][][] greenPixels = {{{0, 50, 0}}};
    int[][][] bluePixels = {{{0, 0, 200}}};

    Image redImage = new Image(1, 1, 255, redPixels);
    Image greenImage = new Image(1, 1, 100, greenPixels);
    Image blueImage = new Image(1, 1, 255, bluePixels);

    imageProcessor.combine(redImage, greenImage, blueImage);
  }
}
