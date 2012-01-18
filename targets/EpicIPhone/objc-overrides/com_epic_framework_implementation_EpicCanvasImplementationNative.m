#import "java_lang_String.h"
#import "com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation.h"
#import "org_xmlvm_iphone_UIImage.h"
#import "org_xmlvm_iphone_CGContext.h"
#import "com_epic_framework_implementation_EpicCanvasImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"

@implementation com_epic_framework_implementation_EpicCanvasImplementationNative;
+ (org_xmlvm_iphone_UIImage*) drawImage___org_xmlvm_iphone_CGContext_org_xmlvm_iphone_UIImage_int_int_int
  : (org_xmlvm_iphone_CGContext*) xcontext
  : (org_xmlvm_iphone_UIImage*) image
  : (int) left
  : (int) top
  : (int) alpha
{
  CGImageRef cgimage = [image CGImage];
  CGContextRef context = xcontext->context;
  CGSize size = [image size];
  CGRect rect = CGRectMake(left, top, size.width, size.height);
  bool opaque = (alpha == 255 && CGImageGetAlphaInfo(cgimage) != kCGImageAlphaPremultipliedFirst);
  CGContextSetBlendMode(context, opaque ? kCGBlendModeCopy : kCGBlendModeNormal);
  float falpha = alpha / 255.0f;
  CGContextSetAlpha(context, falpha);
  //NSLog(@"Drawing image to (%f, %f) - %fx%f, or is it %dx%d", rect.origin.x, rect.origin.y, rect.size.width, rect.size.height, CGImageGetWidth(cgimage), CGImageGetHeight(cgimage));
  CGContextDrawImage(context, rect, cgimage);

  //float falpha = alpha / 255.0f;
 // UIImage* uii = (UIImage*) image;
  //CGPoint point = CGPointMake(left, top);
  //[uii drawAtPoint: point blendMode: kCGBlendModeNormal alpha: falpha];
}

+ (void) inspectImage___org_xmlvm_iphone_UIImage
  : (org_xmlvm_iphone_UIImage*) uii
{
  CGImageRef cg = [uii CGImage];


  #define DEFINE_ALPHA(a) alpha[a+64] = #a;
  const char * alpha[128];
  DEFINE_ALPHA(kCGImageAlphaNone);
  DEFINE_ALPHA(kCGImageAlphaPremultipliedLast);
  DEFINE_ALPHA(kCGImageAlphaPremultipliedFirst);
  DEFINE_ALPHA(kCGImageAlphaLast);
  DEFINE_ALPHA(kCGImageAlphaFirst);
  DEFINE_ALPHA(kCGImageAlphaNoneSkipLast);
  DEFINE_ALPHA(kCGImageAlphaNoneSkipFirst);

  #define DEFINE_CSM(a) csm[a+64] = #a;
  const char * csm[128];
  DEFINE_CSM(kCGColorSpaceModelUnknown);
  DEFINE_CSM(kCGColorSpaceModelMonochrome);
  DEFINE_CSM(kCGColorSpaceModelRGB);
  DEFINE_CSM(kCGColorSpaceModelCMYK);
  DEFINE_CSM(kCGColorSpaceModelLab);
  DEFINE_CSM(kCGColorSpaceModelDeviceN);
  DEFINE_CSM(kCGColorSpaceModelIndexed);
  DEFINE_CSM(kCGColorSpaceModelPattern);

  #define DEFINE_BYTEORDER(a) byte_order[(a>>12)] = #a;
  const char * byte_order[7];
  #define GET_BYTEORDER(bi) (byte_order[(bi & kCGBitmapByteOrderMask) >> 12])
  DEFINE_BYTEORDER(kCGBitmapByteOrderDefault); 
  DEFINE_BYTEORDER(kCGBitmapByteOrder16Little); 
  DEFINE_BYTEORDER(kCGBitmapByteOrder32Little); 
  DEFINE_BYTEORDER(kCGBitmapByteOrder16Big); 
  DEFINE_BYTEORDER(kCGBitmapByteOrder32Big); 

  size_t h = CGImageGetHeight (cg);
  size_t w = CGImageGetWidth (cg);
  CGImageAlphaInfo alphaInfo = CGImageGetAlphaInfo (cg);
  CGBitmapInfo bitmapInfo = CGImageGetBitmapInfo(cg);
  size_t bpc = CGImageGetBitsPerComponent(cg);
  size_t bpp = CGImageGetBitsPerPixel(cg);
  size_t bpr = CGImageGetBytesPerRow(cg);
  CGColorSpaceRef colorSpace = CGImageGetColorSpace(cg);
  size_t colorSpaceNumComponents = CGColorSpaceGetNumberOfComponents(colorSpace);
  CGColorSpaceModel colorSpaceModel = CGColorSpaceGetModel(colorSpace);

  [colorSpace release];
  NSLog(@"size=%d x %d, bpc=%d, bpp=%d, bpr=%d, alpha=%s, bitmapInfo=%p (a=%p, is_fl=%d, bo=%s), colorSpace(n=%d, m=%s)", w, h, bpc, bpp, bpr, alpha[alphaInfo+64], bitmapInfo, (bitmapInfo & kCGBitmapAlphaInfoMask), (bitmapInfo & kCGBitmapFloatComponents), GET_BYTEORDER(bitmapInfo), colorSpaceNumComponents, csm[colorSpaceModel+64]);
}

+ (void) setCrop___org_xmlvm_iphone_CGContext_int_int_int_int
  : (org_xmlvm_iphone_CGContext*) context 
  : (int) left
  : (int) top
  : (int) width
  : (int) height
{
  CGRect clipRect = CGRectMake(left, top, width, height);
  CGContextSaveGState(context->context);
  CGContextClipToRect(context->context, clipRect);
}

+ (void) restoreContext___org_xmlvm_iphone_CGContext
  : (org_xmlvm_iphone_CGContext*) context
{
  CGContextRestoreGState(context->context);
}

@end

