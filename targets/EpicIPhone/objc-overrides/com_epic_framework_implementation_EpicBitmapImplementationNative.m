#import "java_lang_String.h"
#import "com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation.h"
#import "org_xmlvm_iphone_UIImage.h"
#import "org_xmlvm_iphone_CGPoint.h"
#import "com_epic_framework_implementation_EpicBitmapImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"

@implementation com_epic_framework_implementation_EpicBitmapImplementationNative;

+ (org_xmlvm_iphone_UIImage*) resizeImage2___org_xmlvm_iphone_UIImage_int_int_boolean
  : (org_xmlvm_iphone_UIImage*) src 
  : (int) width 
  : (int) height
  : (int) opaque
{
  CGSize lsize = CGSizeMake(width, height);
  CGContextRef screenContext = UIGraphicsGetCurrentContext();
  CGSize psize = CGContextConvertSizeToDeviceSpace (screenContext, lsize);      
  CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
  int pwidth = (int)psize.width;
  int pheight = (int)psize.height;
  size_t bitmapDataSize = pwidth * pheight * 4;
  void *bitmapData = malloc(bitmapDataSize);
  CGBitmapInfo bitmapInfo = (opaque ? kCGImageAlphaNoneSkipFirst : kCGImageAlphaPremultipliedFirst) | kCGBitmapByteOrder32Little;

  CGContextRef context = CGBitmapContextCreate(
    bitmapData,            // void* bitmapData
    (int)psize.width,      // width
    (int)psize.height,     // height
    8,                     // bits-per-component
    width * 4,             // bytes-per-row
    colorSpace,            // color-space
    bitmapInfo             // bitmapInfo
  );
  CGColorSpaceRelease( colorSpace );

  // Flip the context because UIKit coordinate system is upside down to Quartz coordinate system
  CGContextTranslateCTM(context, 0.0, height);
  CGContextScaleCTM(context, 1.0, -1.0);

  
  // Render Src Image
  CGRect destRect = CGRectMake(0, 0, width, height);
  CGContextSetInterpolationQuality(context, kCGInterpolationHigh);
  CGContextSetBlendMode(context, kCGBlendModeCopy);
  CGContextDrawImage(context, destRect, src.CGImage);
  
  // Extract Image Contents
  CGDataProviderRef provider = CGDataProviderCreateWithData(
    NULL,             // "info" - optional app specific data
    bitmapData,       // "data" - the actual array
    bitmapDataSize,   // "size" in bytes
    NULL             // "releaseCallback" - called when Quartz is going to GC the provider object
  );

  CGImageRef cgi = CGImageCreate(
    pwidth,           // "width" in pixels (real pixels, not density adjusted)
    pheight,          // "height" in pixels (real pixels, not density adjusted)
    8,                // "bits-per-component"
    32,               // "bits-per-pixel"
    width * 4,        // "bytes-per-row"
    colorSpace,       // "colorspace"
    bitmapInfo,
    provider,         // "provider" - the CGDataProviderRef for the bitmap
    NULL,             // the "decode" array - passing NULL disallows remapping of the image's color values
    false,            // "should-interpolate" - only relevant if we render to the wrong size
    kCGRenderingIntentDefault // color rendering "intent" - more irrelevant colorspace crap.
  );
  //CGImageRef cgi = CGBitmapContextCreateImage(context);
  UIImage *uii = [UIImage imageWithCGImage: cgi];
  return [XMLVM_NIL2NULL(uii) retain];
}

+ (org_xmlvm_iphone_UIImage*) resizeImage___org_xmlvm_iphone_UIImage_int_int
  : (org_xmlvm_iphone_UIImage*) src 
  : (int) width 
  : (int) height
{
  // Create Drawing Context
  CGSize size = CGSizeMake(width, height);
  UIGraphicsBeginImageContextWithOptions(
    size, 
    false, // opaque
    0.0f  // use "scale-factor" from screen
  );
  CGContextRef context = UIGraphicsGetCurrentContext();

  // Flip the context because UIKit coordinate system is upside down to Quartz coordinate system
  CGContextTranslateCTM(context, 0.0, height);
  CGContextScaleCTM(context, 1.0, -1.0);

  // Render Src Image
  CGRect destRect = CGRectMake(0, 0, width, height);
  CGContextSetInterpolationQuality(context, kCGInterpolationHigh);
  CGContextDrawImage(context, destRect, src.CGImage);
  
  // Extract Image Contents
  UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return [XMLVM_NIL2NULL(newImage) retain];
}

+ (void) drawAtPoint___org_xmlvm_iphone_UIImage_org_xmlvm_iphone_CGPoint
  : (org_xmlvm_iphone_UIImage *) image
  : (org_xmlvm_iphone_CGPoint *) point
{
  CGPoint p = [point getCGPoint];
  [image drawAtPoint: p blendMode: kCGBlendModeCopy alpha: 1.0f ];
}

@end
