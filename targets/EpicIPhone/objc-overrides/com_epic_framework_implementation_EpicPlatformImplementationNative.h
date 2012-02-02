#import "xmlvm.h"
#import "java_lang_Object.h"
#import <Foundation/Foundation.h>

// For circular include:
@class com_epic_framework_common_util_EpicFail;
@class java_lang_Object;
@class com_epic_framework_common_util_exceptions_EpicRuntimeException;
@class com_epic_framework_implementation_EpicPlatformImplementationNative;
@class org_xmlvm_iphone_UIImage;

@interface com_epic_framework_implementation_EpicPlatformImplementationNative : java_lang_Object
{

}

+ (void) setupDebugHandlers__;
+ (void) loginToFacebook__;
+ (void) setAppBadge___int : (int) newCount;
+ (void) requestPurchase___java_lang_String: (java_lang_String *) whichItem;
+ (void) postToFacebook___java_lang_String: (java_lang_String *) fbMessage;
+ (void) requestFacebookFriends___java_lang_String : (java_lang_String*) message;
+ (void) launchBrowserTo___java_lang_String : (java_lang_String*) url;
+ (int) isNetworkAvailable__;

+ (org_xmlvm_iphone_UIImage*) resizeImage___org_xmlvm_iphone_UIImage_int_int
  : (org_xmlvm_iphone_UIImage*) src 
  : (int) width 
  : (int) height
;

@end

