import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationimpls.AdvancedImageProcessorImpl;
import model.operationinterface.AdvancedImageProcessor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * A class that tests the CompressOperation class via the AdvancedImageProcessor interface.
 */
public class CompressOperationTest {
  private final AdvancedImageProcessor advancedImageProcessor = new AdvancedImageProcessorImpl();

  @Test
  public void test2x2ImageCompression() {
    int[][][] originalPixels = {
            {{100, 150, 200}, {150, 200, 250}},
            {{200, 250, 100}, {250, 100, 150}}
    };
    ImageInterface image = new Image(2, 2, 255, originalPixels);

    // Test with 50% compression
    ImageInterface result = advancedImageProcessor.compress(image, 50);

    int[][][] expectedPixels = {
            {{125, 125, 225}, {125, 225, 225}},
            {{225, 225, 125}, {225, 125, 125}}
    };
    assertArrayEquals("Pixels should match",
            expectedPixels, result.getPixels());
  }

  @Test
  public void test4x4ImageWithNoCompression() {
    // Create original 4x4 test image
    int[][][] originalPixels = {
            {{30, 40, 40}, {60, 80, 40}, {90, 120, 40}, {120, 160, 40}},
            {{60, 40, 80}, {90, 80, 80}, {120, 120, 80}, {150, 160, 80}},
            {{90, 40, 120}, {120, 80, 120}, {150, 120, 120}, {180, 160, 120}},
            {{120, 40, 160}, {150, 80, 160}, {180, 120, 160}, {210, 160, 160}}
    };
    ImageInterface image = new Image(4, 4, 255, originalPixels);

    int[][][] expectedPixels = {
            {{30, 40, 40}, {60, 80, 40}, {90, 120, 40}, {120, 160, 40}},
            {{60, 40, 80}, {90, 80, 80}, {120, 120, 80}, {150, 160, 80}},
            {{90, 40, 120}, {120, 80, 120}, {150, 120, 120}, {180, 160, 120}},
            {{120, 40, 160}, {150, 80, 160}, {180, 120, 160}, {210, 160, 160}}
    };

    // Test the actual compression
    ImageInterface result = advancedImageProcessor.compress(image, 0);
    int[][][] actualPixels = result.getPixels();

    // Compare with reasonable tolerance due to floating point calculations
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        for (int c = 0; c < 3; c++) {
          assertEquals("Pixel at position " + x + "," + y + " channel " + c,
                  expectedPixels[y][x][c], actualPixels[y][x][c], 2);
        }
      }
    }
  }


  @Test
  public void testGrayscalePatternCompression() {
    int[][][] originalPixels = new int[4][4][3];
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        int value = ((x + y) % 2) * 255;
        originalPixels[y][x][0] = value;
        originalPixels[y][x][1] = value;
        originalPixels[y][x][2] = value;
      }
    }
    ImageInterface image = new Image(4, 4, 255, originalPixels);

    int[][][] expectedPixels = new int[4][4][3];
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        expectedPixels[y][x][0] = 127;
        expectedPixels[y][x][1] = 127;
        expectedPixels[y][x][2] = 127;
      }
    }

    ImageInterface result = advancedImageProcessor.compress(image, 75);
    int[][][] actualPixels = result.getPixels();

    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        for (int c = 0; c < 3; c++) {
          assertEquals(
                  String.format("Pixel mismatch at position (%d,%d) channel %d%n" +
                                  "Original value: %d%n" +
                                  "Expected: %d%n" +
                                  "Actual: %d",
                          x, y, c,
                          originalPixels[y][x][c],
                          expectedPixels[y][x][c],
                          actualPixels[y][x][c]),
                  expectedPixels[y][x][c],
                  actualPixels[y][x][c], 2);
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressWithInvalidPercentageNegative() {
    int[][][] pixels = {{{100, 150, 200}}};
    ImageInterface image = new Image(1, 1, 255, pixels);
    advancedImageProcessor.compress(image, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressWithInvalidPercentageOver100() {
    int[][][] pixels = {{{100, 150, 200}}};
    ImageInterface image = new Image(1, 1, 255, pixels);
    advancedImageProcessor.compress(image, 110);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressWithNullImage() {
    advancedImageProcessor.compress(null, 50);
  }


}