#import "java_lang_String.h"

// Automatically generated by xmlvm2obj. Do not edit!


#import "com_epic_framework_implementation_UniqueIdGenerator.h"
#import "org_xmlvm_iphone_NSObject.h"


@implementation com_epic_framework_implementation_UniqueIdGenerator;

+ (java_lang_String*) getUniqueDeviceId__
{
  return [[UIDevice currentDevice] uniqueDeviceIdentifier];
}

@end
