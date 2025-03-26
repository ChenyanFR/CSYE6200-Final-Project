import org.junit.Before;
import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationimpls.AdvancedImageProcessorImpl;
import model.operationinterface.AdvancedImageProcessor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class that tests the ColorCorrectionOperation class via the AdvancedImageProcessor interface.
 */
public class ColorCorrectionOperationTest {
  private AdvancedImageProcessor advancedImageProcessor;

  @Before
  public void setUp() {
    advancedImageProcessor = new AdvancedImageProcessorImpl();
  }

  @Test
  public void testColorCorrectRedCastImage() {
    // Create a test image with red color cast
    int[][][] pixels = {
            {{200, 100, 100}, {220, 120, 120}},
            {{180, 80, 80}, {240, 140, 140}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Verify dimensions
    assertEquals(2, result.getWidth());
    assertEquals(2, result.getHeight());
    assertEquals(255, result.getMaxValue());

    // Check each pixel's color balance
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] correctedPixel = result.getPixel(x, y);

        // Original red difference should be greater than corrected red difference
        int originalRedDiff = Math.abs(originalPixel[0] - originalPixel[1]);
        int correctedRedDiff = Math.abs(correctedPixel[0] - correctedPixel[1]);
        assertTrue("Color correction should reduce red cast",
                correctedRedDiff < originalRedDiff);
      }
    }
  }

  @Test
  public void testColorCorrectExactPixels() {
    // Create a test image with known red color cast
    int[][][] pixels = {
            {{200, 100, 100}, {180, 80, 80}},
            {{220, 120, 120}, {240, 140, 140}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Calculate expected values based on the algorithm
    // From the peaks in the histograms:
    // Red peak ≈ 210 (average of red values)
    // Green/Blue peak ≈ 110 (average of green/blue values)
    // Average peak = (210 + 110 + 110) / 3 ≈ 143
    // Shifts: Red = 143 - 210 = -67, Green/Blue = 143 - 110 = 33

    int[][][] expectedPixels = {
            {{133, 133, 133}, {113, 113, 113}},  // Original pixels shifted to average
            {{153, 153, 153}, {173, 173, 173}}
    };

    assertArrayEquals("Color corrected pixels don't match expected values",
            expectedPixels, result.getPixels());
  }

  @Test
  public void testColorCorrectEdgeCases() {
    // Test image with extreme values and pure colors
    int[][][] pixels = {
            {{255, 0, 0}, {0, 255, 0}},    // Pure red and pure green
            {{0, 0, 255}, {255, 255, 255}}  // Pure blue and white
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Expected values after color correction
    // Peaks will be at 255 for each channel
    // Average peak = 255
    // No shifts needed as peaks are already aligned
    int[][][] expectedPixels = {
            {{255, 0, 0}, {0, 255, 0}},    // Values remain the same
            {{0, 0, 255}, {255, 255, 255}}  // as peaks are already aligned
    };

    assertArrayEquals("Color corrected edge case pixels don't match expected values",
            expectedPixels, result.getPixels());
  }

  @Test
  public void testColorCorrectGrayscale() {
    // Test image with grayscale values
    int[][][] pixels = {
            {{128, 128, 128}, {64, 64, 64}},
            {{192, 192, 192}, {255, 255, 255}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Expected values after color correction
    // For grayscale, all channels have same peaks
    // No shifts should occur
    int[][][] expectedPixels = {
            {{128, 128, 128}, {64, 64, 64}},
            {{192, 192, 192}, {255, 255, 255}}
    };

    assertArrayEquals("Color corrected grayscale pixels don't match expected values",
            expectedPixels, result.getPixels());
  }

  @Test
  public void testColorCorrectBlueCastImage() {
    // Create a test image with blue color cast
    int[][][] pixels = {
            {{100, 100, 200}, {120, 120, 220}},
            {{80, 80, 180}, {140, 140, 240}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Check if blue cast is reduced
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] originalPixel = image.getPixel(x, y);
        int[] correctedPixel = result.getPixel(x, y);

        int originalBlueDiff = Math.abs(originalPixel[2] - originalPixel[0]);
        int correctedBlueDiff = Math.abs(correctedPixel[2] - correctedPixel[0]);
        assertTrue("Color correction should reduce blue cast",
                correctedBlueDiff < originalBlueDiff);
      }
    }
  }

  @Test
  public void testColorCorrectGrayscaleImage() {
    // Create a grayscale image
    int[][][] pixels = {
            {{128, 128, 128}, {128, 128, 128}},
            {{128, 128, 128}, {128, 128, 128}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Verify grayscale is preserved
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] pixel = result.getPixel(x, y);
        int maxDiff = Math.max(
                Math.abs(pixel[0] - pixel[1]),
                Math.max(
                        Math.abs(pixel[1] - pixel[2]),
                        Math.abs(pixel[0] - pixel[2])
                )
        );
        assertTrue("Grayscale values should remain similar", maxDiff <= 5);
      }
    }
  }

  @Test
  public void testColorCorrectPreservesRange() {
    // Create an image with extreme values
    int[][][] pixels = {
            {{255, 200, 200}, {0, 0, 0}},
            {{200, 255, 200}, {128, 128, 128}}
    };
    ImageInterface image = new Image(2, 2, 255, pixels);

    ImageInterface result = advancedImageProcessor.colorCorrect(image);

    // Verify values stay within valid range
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] pixel = result.getPixel(x, y);
        for (int c = 0; c < 3; c++) {
          assertTrue("Pixel values should not exceed maximum",
                  pixel[c] <= 255);
          assertTrue("Pixel values should not be negative",
                  pixel[c] >= 0);
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectNullImage() {
    advancedImageProcessor.colorCorrect(null);
  }
}