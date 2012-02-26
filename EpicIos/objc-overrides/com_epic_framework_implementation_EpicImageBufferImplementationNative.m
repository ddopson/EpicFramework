#import "com_epic_framework_implementation_EpicImageBufferImplementation.h"
#import "java_lang_String.h"
#import "com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation.h"
#import "com_epic_framework_implementation_EpicImageBufferImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"
#import "org_xmlvm_iphone_CGContext.h"

@implementation com_epic_framework_implementation_EpicImageBufferImplementationNative;

+ (void) createImageBuffer___com_epic_framework_implementation_EpicImageBufferImplementation_int_int_boolean
  : (com_epic_framework_implementation_EpicImageBufferImplementation*) object
  : (int) width
  : (int) height
  : (int) opaque
{
  // Transform from density adjusted user-coordinates to "real pixels" (eg iPhone4 w/ density=2.0)
  CGContextRef screenContext = UIGraphicsGetCurrentContext();
  CGSize psize = CGContextConvertSizeToDeviceSpace (screenContext, CGSizeMake(width, height));
  int pwidth = (int)psize.width;
  int pheight = (int)psize.height;
  CGSize scale = CGContextConvertSizeToDeviceSpace (screenContext, CGSizeMake(1.0f, 1.0f));
  float xscale = scale.width;

  // Allocate the pixel array
  //NSLog(@"Allocating Array for %d x %d pixels (log = %d x %d)", pwidth, pheight, width, height);
  size_t bitmapDataSize = pwidth * pheight * 4;
  void *bitmapData = malloc(bitmapDataSize);
  
  // ColorSpace / BitmapInfo
  CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
  CGBitmapInfo bitmapInfo = (opaque ? kCGImageAlphaNoneSkipFirst : kCGImageAlphaPremultipliedFirst) | kCGBitmapByteOrder32Little;

  // Create the Render Context
  CGContextRef context = CGBitmapContextCreate(
    bitmapData,        // "data" - the actual array
    pwidth,            // "width" in pixels (real pixels, not density adjusted)
    pheight,           // "height" in pixels (real pixels, not density adjusted)
    8,                 // "bits-per-component"
    pwidth * 4,        // "bytes-per-row"
    colorSpace,        // "colorspace"
    bitmapInfo         // "bitmapInfo"
  );
  CGContextScaleCTM(context, scale.width, scale.height);

  // Create the Bitmap's data provider
  CGDataProviderRef provider = CGDataProviderCreateWithData(
    NULL,              // "info" - optional app specific data
    bitmapData,        // "data" - the actual array
    bitmapDataSize,    // "size" in bytes
    NULL               // "releaseCallback" - called when Quartz is going to GC the provider object
  );

  // Create the Bitmap
  CGImageRef cgi = CGImageCreate(
    pwidth,            // "width" in pixels (real pixels, not density adjusted)
    pheight,           // "height" in pixels (real pixels, not density adjusted)
    8,                 // "bits-per-component"
    32,                // "bits-per-pixel"
    pwidth * 4,        // "bytes-per-row"
    colorSpace,        // "colorspace"
    bitmapInfo,        // "bitmapInfo"
    provider,          // "provider" - the CGDataProviderRef for the bitmap
    NULL,              // the "decode" array - passing NULL disallows remapping of the image's color values
    false,             // "should-interpolate" - only relevant if we render to the wrong size
    kCGRenderingIntentDefault // color rendering "intent" - more irrelevant colorspace crap.
  );
  
  // Release and return
  CGColorSpaceRelease( colorSpace );
  UIImage *uii = [UIImage imageWithCGImage: cgi scale: xscale orientation: UIImageOrientationUp];
  object->platformGraphicsObject_org_xmlvm_iphone_CGContext = context ? [[org_xmlvm_iphone_CGContext alloc] initWithCGContextRef: context] : JAVA_NULL;
  object->platformBitmapObject_org_xmlvm_iphone_UIImage = [XMLVM_NIL2NULL(uii) retain];
}

@end
