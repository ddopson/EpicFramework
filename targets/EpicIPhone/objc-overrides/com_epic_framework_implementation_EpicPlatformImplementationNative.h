#import "xmlvm.h"
#import "java_lang_Object.h"
#import <Foundation/Foundation.h>

// For circular include:
@class com_epic_framework_common_util_EpicFail;
@class java_lang_Object;
@class com_epic_framework_common_util_exceptions_EpicRuntimeException;
@class com_epic_framework_implementation_EpicPlatformImplementationNative;
@class org_xmlvm_iphone_UIImage;

@class Reachability;

@interface ConnectionManager : NSObject {
    Reachability *internetReachable;
    Reachability *hostReachable;
}

@property BOOL internetActive;
@property BOOL hostActive;

- (void) checkNetworkStatus:(NSNotification *)notice;
- (int) isNetworkAvailable;

@end

@interface com_epic_framework_implementation_EpicPlatformImplementationNative : java_lang_Object
{

}

+ (void) setupDebugHandlers__;


+ (org_xmlvm_iphone_UIImage*) resizeImage___org_xmlvm_iphone_UIImage_int_int
  : (org_xmlvm_iphone_UIImage*) src 
  : (int) width 
  : (int) height
;

@end

