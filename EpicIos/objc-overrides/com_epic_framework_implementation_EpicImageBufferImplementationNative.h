#import "xmlvm.h"
#import "java_lang_Object.h"

// For circular include:
@class com_epic_framework_implementation_EpicImageBufferImplementation;
@class java_lang_Object;
@class java_lang_String;
@class com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation;
@class com_epic_framework_implementation_EpicImageBufferImplementationNative;

@interface com_epic_framework_implementation_EpicImageBufferImplementationNative : java_lang_Object
{

}

+ (void) createImageBuffer___com_epic_framework_implementation_EpicImageBufferImplementation_int_int_boolean
  : (com_epic_framework_implementation_EpicImageBufferImplementation*) object
  : (int) width
  : (int) height
  : (int) opaque;

@end
