package model.operationinterface;

import model.image.ImageInterface;

/**
 * Extended interface for advanced image processing operations.
 * This interface includes all basic operations from ImageProcessor
 * and adds advanced functionalities like compression, histogram generation,
 * color correction, levels adjustment, and split view operations.
 */
public interface AdvancedImageProcessor extends ImageProcessor {

  /**
   * Downscales the image to the specified width and height.
   *
   * @param image        The source image to downscale.
   * @param targetWidth  The target width.
   * @param targetHeight The target height.
   * @return A new downscaled version of the input image.
   * @throws IllegalArgumentException if the input image is null or invalid.
   */
  ImageInterface downscale(ImageInterface image, int targetWidth, int targetHeight);

  /**
   * Applies the specified operation to the image using the mask image.
   *
   * @param image     the source image
   * @param maskImage the mask image
   * @param operation the operation to apply
   * @return the resulting image after applying the mask operation
   */
  ImageInterface applyMask(ImageInterface image, ImageInterface maskImage, String operation);
}
