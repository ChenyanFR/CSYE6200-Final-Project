package model.operationinterface;


import model.image.ImageInterface;

/**
 * Interface defining operations for processing and manipulating images.
 * Provides methods for various image transformations including filters, color adjustments,
 * geometric operations, and color channel manipulations.
 */
public interface ImageProcessor {

  /**
   * Adjusts the brightness of an image by adding a constant value to all color channels.
   * Positive values brighten the image, negative values darken it.
   *
   * @param image     The source image to adjust
   * @param increment The amount to add to each pixel's RGB values (negative for darkening)
   * @return A new image with adjusted brightness
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  ImageInterface brighten(ImageInterface image, int increment);

  /**
   * Flips the image horizontally (left to right).
   *
   * @param image The source image to flip
   * @return A new horizontally flipped version of the input image
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  ImageInterface flipHorizontal(ImageInterface image);

  /**
   * Flips the image vertically (top to bottom).
   *
   * @param image The source image to flip
   * @return A new vertically flipped version of the input image
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  ImageInterface flipVertical(ImageInterface image);

  /**
   * Extracts a specific component (color channel or intensity measure) from the image.
   *
   * @param image         The source image to extract component from
   * @param componentType The type of component to extract (RED, GREEN, BLUE, etc.)
   * @return A new grayscale image representing the extracted component
   * @throws IllegalArgumentException if the input image is null or invalid
   */
  ImageInterface extractComponent(ImageInterface image, String componentType);
}