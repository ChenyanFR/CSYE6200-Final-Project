import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.AdvancedImageProcessor;
import model.operationimpls.AdvancedImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests the ApplyMaskOperation class via the AdvancedImageProcessor interface.
 */
public class MaskOperationTest {

  private final AdvancedImageProcessor advancedImageProcessor = new AdvancedImageProcessorImpl();

  @Test
  public void testApplyMaskWithFullyBlackMask_Blur() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "blur");
    ImageInterface expected = advancedImageProcessor.blur(image);

    assertArrayEquals(expected.getPixels(), result.getPixels());
  }

  @Test
  public void testApplyMaskWithFullyWhiteMask_Blur() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };

    int[][][] maskPixels = {
            {{255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "blur");

    assertArrayEquals(image.getPixels(), result.getPixels());
  }

  @Test
  public void testApplyMaskWithMixedMask_Blur() {
    int[][][] pixels = {
            {{100, 0, 0}, {150, 0, 0}},
            {{200, 0, 0}, {250, 0, 0}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };

    int[][][] expectedPixels = {
            {{84, 0, 0}, {150, 0, 0}},
            {{200, 0, 0}, {113, 0, 0}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "blur");

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testApplyMaskWithMixedMask_Sharpen() {
    int[][][] pixels = {
            {{50, 50, 50}, {100, 100, 100}},
            {{150, 150, 150}, {200, 200, 200}}
    };

    int[][][] maskPixels = {
            {{255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "sharpen");

    ImageInterface sharpenedImage = advancedImageProcessor.sharpen(image);
    int[][][] sharpenedPixels = sharpenedImage.getPixels();
    int[][][] expectedPixels = new int[2][2][3];

    expectedPixels[0][0] = pixels[0][0];
    expectedPixels[0][1] = sharpenedPixels[0][1];
    expectedPixels[1][0] = sharpenedPixels[1][0];
    expectedPixels[1][1] = pixels[1][1];

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testApplyMaskWithMixedMask_Sepia() {
    int[][][] pixels = {
            {{20, 40, 60}, {80, 100, 120}},
            {{140, 160, 180}, {200, 220, 240}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {255, 255, 255}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "sepia");

    ImageInterface sepiaImage = advancedImageProcessor.sepia(image);
    int[][][] sepiaPixels = sepiaImage.getPixels();
    int[][][] expectedPixels = new int[2][2][3];

    expectedPixels[0][0] = sepiaPixels[0][0];
    expectedPixels[0][1] = pixels[0][1];
    expectedPixels[1][0] = sepiaPixels[1][0];
    expectedPixels[1][1] = pixels[1][1];

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testApplyMask_ComponentOperation() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };

    int[][][] maskPixels = {
            {{255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}}
    };

    int[][][] expectedPixels = {
            {{10, 20, 30}, {40, 40, 40}},
            {{70, 70, 70}, {100, 110, 120}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "red-component");

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyMask_DifferentDimensions() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };

    ImageInterface image = new Image(2, 1, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    advancedImageProcessor.applyMask(image, maskImage, "blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyMask_NullImage() {
    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };

    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    advancedImageProcessor.applyMask(null, maskImage, "blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyMask_NullMask() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);

    advancedImageProcessor.applyMask(image, null, "blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyMask_UnsupportedOperation() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}},
            {{200, 200, 200}, {250, 250, 250}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    advancedImageProcessor.applyMask(image, maskImage, "unsupported-operation");
  }

  @Test
  public void testApplyMask_FullyBlackMask_Sharpen() {
    int[][][] pixels = {
            {{50, 50, 50}, {100, 100, 100}},
            {{150, 150, 150}, {200, 200, 200}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "sharpen");
    ImageInterface expected = advancedImageProcessor.sharpen(image);

    assertArrayEquals(expected.getPixels(), result.getPixels());
  }

  @Test
  public void testApplyMask_FullyWhiteMask_Sepia() {
    int[][][] pixels = {
            {{20, 40, 60}, {80, 100, 120}},
            {{140, 160, 180}, {200, 220, 240}}
    };

    int[][][] maskPixels = {
            {{255, 255, 255}, {255, 255, 255}},
            {{255, 255, 255}, {255, 255, 255}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image, maskImage, "sepia");

    assertArrayEquals(image.getPixels(), result.getPixels());
  }

  @Test
  public void testApplyMask_MixedMask_ValueComponent() {
    int[][][] pixels = {
            {{10, 20, 30}, {30, 20, 10}},
            {{50, 60, 70}, {70, 60, 50}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 255, 255}, {0, 0, 0}}
    };

    int[][][] expectedPixels = {
            {{30, 30, 30}, {30, 20, 10}},
            {{50, 60, 70}, {70, 70, 70}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image,
            maskImage, "value-component");

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testApplyMask_MixedMask_IntensityComponent() {
    int[][][] pixels = {
            {{30, 60, 90}, {90, 60, 30}},
            {{120, 150, 180}, {180, 150, 120}}
    };

    int[][][] maskPixels = {
            {{255, 255, 255}, {0, 0, 0}},
            {{0, 0, 0}, {255, 255, 255}}
    };

    int[][][] expectedPixels = {
            {{30, 60, 90}, {60, 60, 60}},
            {{150, 150, 150}, {180, 150, 120}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image,
            maskImage, "intensity-component");

    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testApplyMask_LumaComponent() {
    int[][][] pixels = {
            {{50, 100, 150}, {150, 100, 50}},
            {{200, 220, 240}, {240, 220, 200}}
    };

    int[][][] maskPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{0, 0, 0}, {255, 255, 255}}
    };

    int[][][] expectedPixels = {
            {{92, 92, 92}, {150, 100, 50}},
            {{217, 217, 217}, {240, 220, 200}}
    };

    ImageInterface image = new Image(2, 2, 255, pixels);
    ImageInterface maskImage = new Image(2, 2, 255, maskPixels);

    ImageInterface result = advancedImageProcessor.applyMask(image,
            maskImage, "luma-component");

    assertArrayEquals(expectedPixels, result.getPixels());
  }
}