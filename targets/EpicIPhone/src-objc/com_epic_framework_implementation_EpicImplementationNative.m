#import "MyUIImage.h"
#import "com_epic_framework_implementation_EpicImplementationNative.h"
#import "CoreGraphics/CGBitmapContext.h"

@implementation com_epic_framework_implementation_EpicImplementationNative;

+ (MyUIImage*) resizeImagex_MyUIImageMwMw :(MyUIImage*)src :(int)width :(int)height
{
  CGInterpolationQuality quality =  kCGInterpolationHigh;
  CGImageRef imageRef = src.CGImage;
  CGRect destRect = CGRectMake(0, 0, width, height);

  
  CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
  void *bitmapData = malloc( 4 * width * height );
  NSLog(@"About to create context");
  CGContextRef destContext = CGBitmapContextCreate(
    bitmapData,                     // image byte array
    width,                          // width
    height,                         // height
    8,                              // bits-per-component
    4*width,                        // bytes-per-row (is 0 ok?)
    colorSpace,                     // colorSpace
    kCGImageAlphaPremultipliedFirst // bitmapFormat
  );
  NSLog(@"Context Created");
  CGContextSetInterpolationQuality(destContext, quality);
  CGContextDrawImage(destContext, destRect, imageRef);
  NSLog(@"Image Drawn");
  CGImageRef newImageRef = CGBitmapContextCreateImage(destContext);
  UIImage *newImage = [UIImage imageWithCGImage:newImageRef];
  NSLog(@"Image Created.  Freeing stuff...");
  CGContextRelease(destContext);
  CGImageRelease(newImageRef);
  CGColorSpaceRelease(colorSpace);
  NSLog(@"Returning...");
  return MYLIB_NIL2NULL(newImage);
}

@end

