package model.operationimpls;


import model.image.ImageInterface;
import model.operationinterface.ImageProcessor;

import model.utilities.ImageValidator;

/**
 * Implementation of ImageProcessor interface that provides various image manipulation operations.
 * Supports operations like blurring, sharpening, brightening, sepia, and color transformations.
 * All operations validate input images before processing and return new ImageInterface instances.
 */
public class ImageProcessorImpl implements ImageProcessor {


  /**
   * Adjusts the brightness of an image by adding an increment to each color channel.
   *
   * @param image     The source image to brighten
   * @param increment The amount to add to each pixel's RGB values (negative for darkening)
   * @return A new image with adjusted brightness
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  @Override
  public ImageInterface brighten(ImageInterface image, int increment) {
    ImageValidator.validate(image);
    return new BrightenOperation(increment).execute(image);
  }



  /**
   * Flips the image horizontally (left to right).
   *
   * @param image The source image to flip
   * @return A new horizontally flipped version of the input image
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  @Override
  public ImageInterface flipHorizontal(ImageInterface image) {
    ImageValidator.validate(image);
    return new HorizontalFlipOperation().execute(image);
  }

  /**
   * Flips the image vertically (top to bottom).
   *
   * @param image The source image to flip
   * @return A new vertically flipped version of the input image
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  @Override
  public ImageInterface flipVertical(ImageInterface image) {
    ImageValidator.validate(image);
    return new VerticalFlipOperation().execute(image);
  }

  /**
   * Extracts a single color component (red, green, or blue) from the image.
   *
   * @param image         The source image to extract component from
   * @param componentName The color component to extract (RED, GREEN, or BLUE)
   * @return A new grayscale image representing the extracted component
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  @Override
  public ImageInterface extractComponent(ImageInterface image, String componentName) {
    ImageValidator.validate(image);
    return new ComponentOperation(componentName).execute(image);
  }


}