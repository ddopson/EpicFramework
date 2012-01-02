#import "xmlvm.h"
#import "java_lang_Object.h"

// For circular include:
@class java_lang_Object;
@class com_epic_framework_implementation_EpicFontImplementationNative;
@class com_epic_framework_common_Ui_EpicFile;
@class java_lang_String;
@class com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation;
@class org_xmlvm_iphone_CGContext;
@class org_xmlvm_iphone_CGFont;

// Automatically generated by xmlvm2obj. Do not edit!


	
@interface com_epic_framework_implementation_EpicFontImplementationNative : java_lang_Object
{

}

+ (org_xmlvm_iphone_CGFont *) CGFontCreateFromName___java_lang_String
  : (java_lang_String *) fontName;

+ (void) CGContextSetFont___java_lang_Object_org_xmlvm_iphone_CGContext
  : (java_lang_Object *) font
  : (org_xmlvm_iphone_CGContext*) context;


+ (int) CGFontGetAscent___java_lang_Object
  : (java_lang_Object *) fontObject;

+ (int) CGFontGetDescent___java_lang_Object
  : (java_lang_Object *) fontObject;

+ (int) CGFontGetUnitsPerEm___java_lang_Object
  : (java_lang_Object *) fontObject;

+ (int) measureAdvance___java_lang_Object_java_lang_String
  : (java_lang_Object*) fontObject
  : (java_lang_String *) text;

@end

