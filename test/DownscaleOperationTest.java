import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.AdvancedImageProcessor;
import model.operationimpls.AdvancedImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests the DownscaleOperation class via the AdvancedImageProcessor interface.
 */
public class DownscaleOperationTest {

  private final AdvancedImageProcessor advancedImageProcessor = new AdvancedImageProcessorImpl();

  @Test
  public void testDownscaleSimpleSquareImage() {
    int[][][] pixels = {
            {{25, 25, 25}, {75, 75, 75}, {50, 50, 50}},
            {{100, 100, 100}, {150, 150, 150}, {200, 200, 200}},
            {{25, 25, 25}, {75, 75, 75}, {50, 50, 50}}
    };

    int[][][] expectedPixels = {
            {{25, 25, 25}, {50, 50, 50}},
            {{25, 25, 25}, {50, 50, 50}}
    };

    Image image = new Image(3, 3, 255, pixels);

    ImageInterface result = advancedImageProcessor.downscale(image, 2, 2);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testDownscaleNonSquareImage() {
    int[][][] pixels = {
            {{25, 25, 25}, {75, 75, 75}, {50, 50, 50}},
            {{100, 100, 100}, {150, 150, 150}, {200, 200, 200}},
            {{25, 25, 25}, {75, 75, 75}, {50, 50, 50}},
            {{100, 100, 100}, {150, 150, 150}, {200, 200, 200}},
    };

    int[][][] expectedPixels = {
            {{25, 25, 25}, {50, 50, 50}},
            {{100, 100, 100}, {200, 200, 200}},
    };

    Image image = new Image(3, 4, 255, pixels);

    ImageInterface result = advancedImageProcessor.downscale(image, 2, 2);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testDownscaleWithAspectRatioChange() {
    int[][][] pixels = {
            {{10, 10, 10}, {20, 20, 20}, {30, 30, 30}},
            {{40, 40, 40}, {50, 50, 50}, {60, 60, 60}},
            {{70, 70, 70}, {80, 80, 80}, {90, 90, 90}}
    };

    int[][][] expectedPixels = {
            {{10, 10, 10}, {30, 30, 30}},
            {{70, 70, 70}, {90, 90, 90}}
    };

    Image image = new Image(3, 3, 255, pixels);

    ImageInterface result = advancedImageProcessor.downscale(image, 2, 2);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testDownscaleLargeImage() {
    int[][][] pixels = {
            {{10, 10, 10}, {20, 20, 20}, {30, 30, 30}, {40, 40, 40}},
            {{50, 50, 50}, {60, 60, 60}, {70, 70, 70}, {80, 80, 80}},
            {{90, 90, 90}, {100, 100, 100}, {110, 110, 110}, {120, 120, 120}},
            {{130, 130, 130}, {140, 140, 140}, {150, 150, 150}, {160, 160, 160}}
    };

    int[][][] expectedPixels = {
            {{10, 10, 10}, {40, 40, 40}},
            {{130, 130, 130}, {160, 160, 160}}
    };

    Image image = new Image(4, 4, 255, pixels);

    ImageInterface result = advancedImageProcessor.downscale(image, 2, 2);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testDownscaleToSmallerNonSquareImage() {
    int[][][] pixels = {
            {{5, 5, 5}, {10, 10, 10}, {15, 15, 15}, {20, 20, 20}},
            {{25, 25, 25}, {30, 30, 30}, {35, 35, 35}, {40, 40, 40}},
            {{45, 45, 45}, {50, 50, 50}, {55, 55, 55}, {60, 60, 60}}
    };

    int[][][] expectedPixels = {
            {{0, 0, 0}, {0, 0, 0}}
    };

    Image image = new Image(4, 3, 255, pixels);

    ImageInterface result = advancedImageProcessor.downscale(image, 2, 1);

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleInvalidDimensions() {
    int[][][] pixels = {
            {{10, 10, 10}, {20, 20, 20}},
            {{30, 30, 30}, {40, 40, 40}}
    };

    Image image = new Image(2, 2, 255, pixels);

    advancedImageProcessor.downscale(image, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleToLargerDimensions() {
    int[][][] pixels = {
            {{10, 10, 10}, {20, 20, 20}},
            {{30, 30, 30}, {40, 40, 40}}
    };

    Image image = new Image(2, 2, 255, pixels);

    advancedImageProcessor.downscale(image, 4, 4);
  }
}