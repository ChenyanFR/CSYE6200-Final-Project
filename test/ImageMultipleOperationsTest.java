import org.junit.Test;

import model.image.Image;
import model.image.ImageInterface;
import model.operationimpls.ImageProcessorImpl;
import model.operationinterface.ImageProcessor;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class that tests back to back pixel operations and verifies the final pixels.
 */
public class ImageMultipleOperationsTest {

  private final ImageProcessor imageProcessor = new ImageProcessorImpl();

  @Test
  public void testMultipleOperations() {
    int[][][] pixels = {
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}}
    };

    ImageInterface image = new Image(3, 3, 255, pixels);

    ImageInterface blurredImage = imageProcessor.blur(image);
    ImageInterface sharpenedImage = imageProcessor.sharpen(blurredImage);

    ImageInterface grayscaleImage =
            imageProcessor.extractComponent(sharpenedImage, "luma");

    int[][][] expectedPixels = {
            {{112, 112, 112}, {201, 201, 201}, {112, 112, 112}},
            {{201, 201, 201}, {249, 249, 249}, {201, 201, 201}},
            {{112, 112, 112}, {201, 201, 201}, {112, 112, 112}}
    };

    assertArrayEquals(expectedPixels, grayscaleImage.getPixels());
  }

  @Test
  public void testMultipleOperationsIncludingExtractComponent() {
    int[][][] pixels = {
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}},
            {{100, 150, 200}, {100, 150, 200}, {100, 150, 200}}
    };

    ImageInterface image = new Image(3, 3, 255, pixels);

    ImageInterface blurredImage = imageProcessor.blur(image);

    ImageInterface sharpenedImage = imageProcessor.sharpen(blurredImage);

    ImageInterface grayscaleImage =
            imageProcessor.extractComponent(sharpenedImage, "luma");

    ImageInterface redComponentImage =
            imageProcessor.extractComponent(grayscaleImage, "red");

    ImageInterface extractedImage =
            imageProcessor.extractComponent(redComponentImage, "red");

    int[][][] expectedPixels = {
            {{112, 112, 112}, {201, 201, 201}, {112, 112, 112}},
            {{201, 201, 201}, {249, 249, 249}, {201, 201, 201}},
            {{112, 112, 112}, {201, 201, 201}, {112, 112, 112}}
    };

    assertArrayEquals(expectedPixels, extractedImage.getPixels());
  }
}
