#import "java_lang_String.h"
#import "com_epic_framework_common_util_exceptions_EpicNativeMethodMissingImplementation.h"

// Automatically generated by xmlvm2obj. Do not edit!


#import "com_epic_framework_implementation_EpicIOImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"
#import "org_xmlvm_iphone_NSFileManager.h"


const char* digits = "0123456789ABCDEF";

char outputbuff[1024];

void dumpBuff(char * buff, int length) {
  int p=0;
  NSLog(@"Hi");
  
  for(int i = 0; i < length; i++) {
    int c = (int)buff[i];
    if(c < 0) c += 256;
    int a = c & 0x0F;
    int b = (c & 0xF0) >> 4;
    outputbuff[p++] = digits[a];
    outputbuff[p++] = digits[b];
    outputbuff[p++] = " ";
  }
  outputbuff[p++] = 0;
  NSLog(@"Buffer: %s", outputbuff);
}

@implementation com_epic_framework_implementation_EpicIOImplementationNative;

NSString *getFullPath(java_lang_String *filename) {
  NSArray *appDocumentPaths = NSSearchPathForDirectoriesInDomains(
    NSDocumentDirectory,
    NSUserDomainMask, 
    YES
  );
  NSString *docsDirectory = [appDocumentPaths objectAtIndex: 0];
  NSString *fullPathToFile = [docsDirectory stringByAppendingPathComponent: filename];
  NSLog(@"filename=%@", fullPathToFile);
  return fullPathToFile;
}

+ (void) getFullPath___java_lang_String
  : (java_lang_String*) filename
{
  return [getFullPath(filename) retain];  
}

+ (int) isExistsFile___java_lang_String : (java_lang_String*) filename
{
    NSString* docPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    NSString* fullPath = [docPath stringByAppendingPathComponent:filename];
    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:fullPath];

    if(fileExists) {
        return 1;
    } else {
        return 0;
    }
}

+ (void) writeFile___java_lang_String_byte_ARRAYTYPE
  : (java_lang_String*) filename
  : (XMLVMArray*) bytes
{
  NSLog(@"About to write %@ with %d bytes", filename, bytes->length);
  //dumpBuff(bytes->array.b, bytes->length);
    NSArray *paths = NSSearchPathForDirectoriesInDomains
    (NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *docPath = [paths objectAtIndex:0];
    NSString *fullPath = [NSString stringWithFormat:@"%@/%@", 
                          docPath, filename];


  NSData *data = [NSData dataWithBytesNoCopy: bytes->array.b length: bytes->length freeWhenDone: NO];
  if ([data writeToFile: fullPath atomically: YES])
    NSLog(@"%@ saved.", fullPath);
  else
    NSLog(@"Error writing %@", fullPath);
}
#define BYTE_TYPE 3
+ (XMLVMArray*) readFile___java_lang_String
  : (java_lang_String*) filename
{
  //NSLog(@"About to read %@", filename);
    NSArray *paths = NSSearchPathForDirectoriesInDomains
    (NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *docPath = [paths objectAtIndex:0];
    NSString *fullPath = [NSString stringWithFormat:@"%@/%@", 
                          docPath, filename];
    
  NSData *data = [NSData dataWithContentsOfFile: fullPath];
  
  if(data) {
    XMLVMArray *array = [XMLVMArray createSingleDimensionWithType: BYTE_TYPE size: [data length] andData: [data bytes]];
    //[data release];
    NSLog(@"Read %d bytes from file %@", [data length], fullPath);
    //dumpBuff(array->array.b, array->length);
    return array;
  } else {
    NSLog(@"Error reading %@", fullPath);
  }

}

@end

