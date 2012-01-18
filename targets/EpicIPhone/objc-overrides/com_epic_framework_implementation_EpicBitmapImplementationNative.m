#import "java_lang_String.h"
#import "com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation.h"
#import "org_xmlvm_iphone_UIImage.h"
#import "org_xmlvm_iphone_CGPoint.h"
#import "com_epic_framework_implementation_EpicBitmapImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"

@implementation com_epic_framework_implementation_EpicBitmapImplementationNative;

+ (org_xmlvm_iphone_UIImage*) resizeImage___org_xmlvm_iphone_UIImage_int_int
  : (org_xmlvm_iphone_UIImage*) src 
  : (int) width 
  : (int) height
{
  // Create Drawing Context
  CGSize size = CGSizeMake(width, height);
  UIGraphicsBeginImageContextWithOptions(
    size, 
    false,   // opaque
    0.0f     // use "scale-factor" from screen
  );
  CGContextRef context = UIGraphicsGetCurrentContext();

  // Flip the context because UIKit coordinate system is upside down to Quartz coordinate system
  //CGContextTranslateCTM(context, 0.0, height);
  //CGContextScaleCTM(context, 1.0, -1.0);

  // Render Src Image
  CGRect destRect = CGRectMake(0, 0, width, height);
  CGContextSetInterpolationQuality(context, kCGInterpolationHigh);
  CGContextSetBlendMode(context, kCGBlendModeCopy);
  CGContextDrawImage(context, destRect, src.CGImage);
  
  // Extract Image Contents
  UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  return [XMLVM_NIL2NULL(newImage) retain];
}

@end
