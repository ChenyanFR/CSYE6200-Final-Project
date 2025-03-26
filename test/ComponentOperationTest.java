import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;
import model.operationimpls.ImageProcessorImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * A class that tests the ComponentOperation class via the ImageProcessor interface.
 */
public class ComponentOperationTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  @Test
  public void testRedComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "red");

    int[][][] expectedPixels = {
            {{10, 10, 10}, {40, 40, 40}},
            {{70, 70, 70}, {100, 100, 100}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testGreenComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "green");

    int[][][] expectedPixels = {
            {{20, 20, 20}, {50, 50, 50}},
            {{80, 80, 80}, {110, 110, 110}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testBlueComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "blue");

    int[][][] expectedPixels = {
            {{30, 30, 30}, {60, 60, 60}},
            {{90, 90, 90}, {120, 120, 120}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testLumaComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "luma");

    int[][][] expectedPixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] rgb = pixels[y][x];
        int value = clamp((int) (0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]),
                0, 255);
        expectedPixels[y][x][0] = value;
        expectedPixels[y][x][1] = value;
        expectedPixels[y][x][2] = value;
      }
    }
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testIntensityComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "intensity");

    int[][][] expectedPixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] rgb = pixels[y][x];
        int value = (rgb[0] + rgb[1] + rgb[2]) / 3;
        expectedPixels[y][x][0] = value;
        expectedPixels[y][x][1] = value;
        expectedPixels[y][x][2] = value;
      }
    }
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testValueComponentExtraction() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}},
            {{70, 80, 90}, {100, 110, 120}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "value");

    int[][][] expectedPixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] rgb = pixels[y][x];
        int value = Math.max(rgb[0], Math.max(rgb[1], rgb[2]));
        expectedPixels[y][x][0] = value;
        expectedPixels[y][x][1] = value;
        expectedPixels[y][x][2] = value;
      }
    }
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testComponentExtractionWithMinAndMaxValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface resultRed = imageProcessor.extractComponent(image, "red");
    int[][][] expectedRedPixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{128, 128, 128}, {64, 64, 64}}
    };
    assertArrayEquals(expectedRedPixels, resultRed.getPixels());

    ImageInterface resultLuma = imageProcessor.extractComponent(image, "luma");
    int[][][] expectedLumaPixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] rgb = pixels[y][x];
        int value = clamp((int) (0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]),
                0, 255);
        expectedLumaPixels[y][x][0] = value;
        expectedLumaPixels[y][x][1] = value;
        expectedLumaPixels[y][x][2] = value;
      }
    }
    assertArrayEquals(expectedLumaPixels, resultLuma.getPixels());
  }

  @Test
  public void testComponentExtraction1x1Image() {
    int[][][] pixels = {{{100, 150, 200}}};
    Image image = new Image(1, 1, 255, pixels);

    ImageInterface resultRed = imageProcessor.extractComponent(image, "red");
    int[][][] expectedRedPixels = {{{100, 100, 100}}};
    assertArrayEquals(expectedRedPixels, resultRed.getPixels());

    ImageInterface resultLuma = imageProcessor.extractComponent(image, "luma");
    int value = clamp((int) (0.2126 * 100 + 0.7152 * 150 + 0.0722 * 200), 0, 255);
    int[][][] expectedLumaPixels = {{{value, value, value}}};
    assertArrayEquals(expectedLumaPixels, resultLuma.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExtractComponentWithNullImage() {
    imageProcessor.extractComponent(null, "red");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidComponentName() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}}
    };
    Image image = new Image(2, 1, 255, pixels);

    imageProcessor.extractComponent(image, "invalid");
  }

  @Test
  public void testComponentExtractionOnGrayscaleImage() {
    int[][][] pixels = {
            {{100, 100, 100}, {150, 150, 150}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface resultRed = imageProcessor.extractComponent(image, "red");
    int[][][] expectedRedPixels = {
            {{100, 100, 100}, {150, 150, 150}}
    };
    assertArrayEquals(expectedRedPixels, resultRed.getPixels());

    ImageInterface resultLuma = imageProcessor.extractComponent(image, "luma");
    int value0 = clamp((int) (0.2126 * 100 + 0.7152 * 100 + 0.0722 * 100), 0, 255);
    int value1 = clamp((int) (0.2126 * 150 + 0.7152 * 150 + 0.0722 * 150), 0, 255);
    int[][][] expectedLumaPixels = {
            {{value0, value0, value0}, {value1, value1, value1}}
    };
    assertArrayEquals(expectedLumaPixels, resultLuma.getPixels());
  }

  @Test
  public void testComponentExtractionOnLargeImage() {
    int[][][] pixels = new int[5][5][3];
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        pixels[y][x][0] = 50;
        pixels[y][x][1] = 100;
        pixels[y][x][2] = 150;
      }
    }
    Image image = new Image(5, 5, 255, pixels);

    ImageInterface resultGreen = imageProcessor.extractComponent(image, "green");

    int[][][] expectedGreenPixels = new int[5][5][3];
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        expectedGreenPixels[y][x][0] = 100;
        expectedGreenPixels[y][x][1] = 100;
        expectedGreenPixels[y][x][2] = 100;
      }
    }
    assertArrayEquals(expectedGreenPixels, resultGreen.getPixels());
  }

  @Test
  public void testComponentExtractionWithEdgeValues() {
    int[][][] pixels = {
            {{0, 0, 0}, {255, 255, 255}},
            {{255, 0, 0}, {0, 255, 0}},
            {{0, 0, 255}, {255, 255, 0}}
    };
    Image image = new Image(2, 3, 255, pixels);

    ImageInterface resultValue = imageProcessor.extractComponent(image, "value");

    int[][][] expectedValuePixels = new int[3][2][3];
    expectedValuePixels[0][0][0] = 0;
    expectedValuePixels[0][0][1] = 0;
    expectedValuePixels[0][0][2] = 0;

    expectedValuePixels[0][1][0] = 255;
    expectedValuePixels[0][1][1] = 255;
    expectedValuePixels[0][1][2] = 255;

    expectedValuePixels[1][0][0] = 255;
    expectedValuePixels[1][0][1] = 255;
    expectedValuePixels[1][0][2] = 255;

    expectedValuePixels[1][1][0] = 255;
    expectedValuePixels[1][1][1] = 255;
    expectedValuePixels[1][1][2] = 255;

    expectedValuePixels[2][0][0] = 255;
    expectedValuePixels[2][0][1] = 255;
    expectedValuePixels[2][0][2] = 255;

    expectedValuePixels[2][1][0] = 255;
    expectedValuePixels[2][1][1] = 255;
    expectedValuePixels[2][1][2] = 255;

    assertArrayEquals(expectedValuePixels, resultValue.getPixels());
  }

  @Test
  public void testComponentExtractionPreservesImageProperties() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "red");

    assertEquals("Width should be preserved", image.getWidth(), result.getWidth());
    assertEquals("Height should be preserved", image.getHeight(), result.getHeight());
    assertEquals("Max value should be preserved",
            image.getMaxValue(), result.getMaxValue());
  }

  @Test
  public void testComponentExtractionWithMixedCaseComponentName() {
    int[][][] pixels = {
            {{10, 20, 30}, {40, 50, 60}}
    };
    Image image = new Image(2, 1, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "ReD");

    int[][][] expectedPixels = {
            {{10, 10, 10}, {40, 40, 40}}
    };
    assertArrayEquals(expectedPixels, result.getPixels());
  }

  @Test
  public void testComponentExtractionAfterOtherOperations() {
    int[][][] pixels = {
            {{100, 150, 200}, {150, 200, 250}},
            {{200, 250, 100}, {250, 100, 150}}
    };
    Image image = new Image(2, 2, 255, pixels);

    ImageInterface brightenedImage = imageProcessor.brighten(image, -50);

    ImageInterface lumaImage = imageProcessor.extractComponent(brightenedImage,
        "luma");

    int[][][] expectedPixels = new int[2][2][3];
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        int[] rgb = brightenedImage.getPixels()[y][x];
        int value = clamp((int) (0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]),
                0, 255);

        expectedPixels[y][x][0] = value;
        expectedPixels[y][x][1] = value;
        expectedPixels[y][x][2] = value;
      }
    }
    assertArrayEquals(expectedPixels, lumaImage.getPixels());
  }

  @Test
  public void testComponentExtractionOnNonSquareImage() {
    int[][][] pixels = {
            {{10, 20, 30}},
            {{40, 50, 60}},
            {{70, 80, 90}}
    };
    Image image = new Image(1, 3, 255, pixels);

    ImageInterface result = imageProcessor.extractComponent(image, "green");

    int[][][] expectedPixels = new int[3][1][3];
    for (int y = 0; y < 3; y++) {
      int value = pixels[y][0][1];
      expectedPixels[y][0][0] = value;
      expectedPixels[y][0][1] = value;
      expectedPixels[y][0][2] = value;
    }
    assertArrayEquals(expectedPixels, result.getPixels());
  }
}
