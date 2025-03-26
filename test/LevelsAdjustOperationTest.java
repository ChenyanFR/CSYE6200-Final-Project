import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationimpls.AdvancedImageProcessorImpl;
import model.operationinterface.AdvancedImageProcessor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the LevelsAdjustOperation class via the AdvancedImageProcessor interface.
 */
public class LevelsAdjustOperationTest {

  private final AdvancedImageProcessor advancedImageProcessor = new AdvancedImageProcessorImpl();

  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  private double[] computeQuadraticCoefficients(int b, int m, int w, int maxValue) {
    double x0 = b;
    double x1 = m;
    double x2 = w;

    double y0 = 0;
    double y1 = maxValue / 2.0;
    double y2 = maxValue;

    double denom0 = (x0 - x1) * (x0 - x2);
    double denom1 = (x1 - x0) * (x1 - x2);
    double denom2 = (x2 - x0) * (x2 - x1);

    double a0 = y0 / denom0;
    double a1 = y1 / denom1;
    double a2 = y2 / denom2;

    double a = a0 + a1 + a2;
    double b_coef = -(a0 * (x1 + x2) + a1 * (x0 + x2) + a2 * (x0 + x1));
    double c = a0 * x1 * x2 + a1 * x0 * x2 + a2 * x0 * x1;

    return new double[]{a, b_coef, c};
  }

  private int adjustLevelsQuadratic(int value, int blackPoint, int midPoint, int whitePoint,
                                    int maxValue) {

    double[] coeffs = computeQuadraticCoefficients(blackPoint, midPoint, whitePoint, maxValue);

    double adjustedValue = coeffs[0] * value * value + coeffs[1] * value + coeffs[2];

    return clamp((int) Math.round(adjustedValue), 0, maxValue);
  }

  @Test
  public void testLevelsAdjustSimpleImage() {
    int[][][] pixels = {
            {{50, 100, 150}, {200, 150, 100}},
            {{100, 150, 200}, {150, 200, 50}}
    };
    int[][][] expectedPixels = {
            {{0, 80, 165}, {255, 165, 80}},
            {{80, 165, 255}, {165, 255, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    assertNotNull(result);
    assertEquals(2, result.getWidth());
    assertEquals(2, result.getHeight());

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithNullImage() {
    advancedImageProcessor.adjustLevels(null, 0, 128, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithInvalidBlackPoint() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 150, 128, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithInvalidMidPoint() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 0, 200, 150);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithInvalidWhitePoint() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 0, 128, 300);
  }

  @Test
  public void testLevelsAdjustWithEdgeValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}}
    };

    int[][][] expectedPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 0, 128, 255);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustOnGrayscaleImage() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {50, 50, 50}}
    };

    int[][][] expctedPixels = {
            {{80, 80, 80}, {165, 165, 165}},
            {{255, 255, 255}, {0, 0, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    assertArrayEquals(expctedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustPreservesImageProperties() {
    int[][][] pixels = {
            {{50, 100, 150}, {200, 150, 100}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    assertEquals("Width should be preserved", image.getWidth(), result.getWidth());
    assertEquals("Height should be preserved", image.getHeight(), result.getHeight());
    assertEquals("Max value should be preserved",
            image.getMaxValue(), result.getMaxValue());
  }

  @Test
  public void testLevelsAdjustPixelValueRanges() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    int[][][] resultPixels = result.getPixels();
    int maxValue = image.getMaxValue();
    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        for (int i = 0; i < 3; i++) {
          int value = resultPixels[y][x][i];
          assertTrue("Pixel value should be >= 0", value >= 0);
          assertTrue("Pixel value should be <= " + maxValue, value <= maxValue);
        }
      }
    }

  }

  @Test
  public void testLevelsAdjust1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    int[][][] expectedPixels = {
            {{80, 165, 255}}
    };

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    assertArrayEquals(expectedPixels, result.getPixels());
  }


  @Test
  public void testLevelsAdjustOnNonSquareImage() {
    int[][][] pixels = {
            {{10, 20, 30}},
            {{40, 50, 60}},
            {{70, 80, 90}}
    };

    int[][][] expectedPixels = {
            {{0, 32, 64}},
            {{96, 127, 159}},
            {{191, 223, 255}}
    };

    Image image = new Image(1, 3, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 10, 50, 90);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustAfterOtherOperations() {
    int[][][] pixels = {
            {{100, 150, 200}, {150, 200, 250}},
            {{200, 250, 100}, {250, 100, 150}}
    };

    int[][][] expectedPixels = {
            {{0, 80, 165}, {80, 165, 255}},
            {{165, 255, 0}, {255, 0, 80}}
    };

    Image image = new Image(2, 2, 255, pixels);

    ImageInterface brightenedImage = new AdvancedImageProcessorImpl().brighten(image, -50);

    ImageInterface result = advancedImageProcessor.adjustLevels(brightenedImage,
            50, 128, 200);

    assertNotNull(result);
    assertEquals(image.getWidth(), result.getWidth());
    assertEquals(image.getHeight(), result.getHeight());

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustWithDifferentMaxValues() {
    int[][][] pixels = {
            {{25, 50, 75}, {50, 75, 100}}
    };
    Image image = new Image(2, 1, 100, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 25, 50, 75);

    assertEquals("Max value should be preserved",
            image.getMaxValue(), result.getMaxValue());

    int[][][] expectedPixels = {
            {{0, 50, 100}, {50, 100, 100}}
    };

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustWithMinAndMaxValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {100, 100, 100}}
    };

    int[][][] expectedPixels = {
            {{0, 0, 0}, {100, 100, 100}}
    };

    Image image = new Image(2, 1, 100, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 0, 50, 100);


    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLevelsAdjustConsistency() {
    int[][][] pixels = {
            {{50, 100, 150}, {200, 150, 100}},
            {{100, 150, 200}, {150, 200, 50}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result1 =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);
    ImageInterface result2 =
            advancedImageProcessor.adjustLevels(image, 50, 128, 200);

    assertArrayEquals("Levels adjustment should be consistent",
            result1.getPixels(), result2.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithReversedPoints() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 200, 128, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithOutOfRange() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 560, 1280, 300);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLevelsAdjustWithExtremeMidPoint() {
    int[][][] pixels = {
            {{50, 100, 150}, {200, 150, 100}}
    };
    Image image = new Image(2, 1, 255, pixels);

    advancedImageProcessor.adjustLevels(image, 0, 0, 255);
  }

  @Test
  public void testLevelsAdjustOnAllWhiteImage() {
    int[][][] pixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        pixels[y][x][0] = 255;
        pixels[y][x][1] = 255;
        pixels[y][x][2] = 255;
      }
    }
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 0, 128, 255);

    assertArrayEquals("All white image should remain unchanged",
            image.getPixels(), result.getPixels());
  }

  @Test
  public void testLevelsAdjustOnAllBlackImage() {
    int[][][] pixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        pixels[y][x][0] = 0;
        pixels[y][x][1] = 0;
        pixels[y][x][2] = 0;
      }
    }
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result =
            advancedImageProcessor.adjustLevels(image, 0, 128, 255);

    assertArrayEquals("All black image should remain unchanged",
            image.getPixels(), result.getPixels());
  }
}