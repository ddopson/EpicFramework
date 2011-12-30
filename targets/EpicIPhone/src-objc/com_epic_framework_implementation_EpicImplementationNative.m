#import "MyUIImage.h"
#import "com_epic_framework_implementation_EpicImplementationNative.h"
#import "CoreGraphics/CGBitmapContext.h"
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>
#include <execinfo.h>

#define STDERR_FD 2

static void segfault_handler(int sig, siginfo_t *si, void *signame) {
  void    *array[32]; // Array to store backtrace symbols
  size_t  size;       // To store the size of the stack backtrace
  char    sbuff[128];
  int     n;          // chars written to buffer
  int     pid;

  // Write the header line
  pid = getpid();
  n = snprintf(sbuff, sizeof(sbuff), "PID %d received %s (%d) for address: 0x%lx\n", pid, (const char *)signame, sig, (long) si->si_addr);
  write(STDERR_FD, sbuff, n);

  // Write the Backtrace
  size = backtrace(array, 32);
  backtrace_symbols_fd(array, size, STDERR_FD);

  // Exit violently
  exit(-1);
}

void uncaught_exception_handler(NSException *exception) {
  NSLog(@"Unhandled Exception: %@: %@", [exception name], [exception reason]);
  NSArray *myArray = [exception callStackSymbols];
  NSEnumerator *enumerator = [myArray objectEnumerator];
  id anObject;
  while (anObject = [enumerator nextObject]) {
    NSLog(@"%@", anObject);
  }
}

@implementation com_epic_framework_implementation_EpicImplementationNative;

+ (void) setupDebugHandlersx {
  NSLog(@"Initializing Debug Handlers to cat SIGSEGV / SIGTRAP");
  struct sigaction sa;
  memset(&sa, 0, sizeof(struct sigaction));
  sigemptyset(&sa.sa_mask);
  sa.sa_sigaction = segfault_handler;
  sa.sa_flags   = SA_SIGINFO;
  sigaction(SIGSEGV, &sa, (void *)"SIGSEGV");
  sigaction(SIGABRT, &sa, (void *)"SIGABRT");
  sigaction(SIGTRAP, &sa, (void *)"SIGTRAP");
  sigaction(SIGSTOP, &sa, (void *)"SIGSTOP");
  sigaction(SIGQUIT, &sa, (void *)"SIGQUIT");
  sigaction(SIGHUP, &sa, (void *)"SIGHUP");
  sigaction(SIGINT, &sa, (void *)"SIGINT");
  sigaction(SIGFPE, &sa, (void *)"SIGFPE");
  sigaction(SIGTERM, &sa, (void *)"SIGTERM");
  sigaction(SIGILL, &sa, (void *)"SIGILL");
  sigaction(SIGBUS, &sa, (void *)"SIGBUS");

  NSSetUncaughtExceptionHandler(uncaught_exception_handler);
}

+ (MyUIImage*) resizeImagex_MyUIImageMwMw :(MyUIImage*)src :(int)width :(int)height
{
  CGInterpolationQuality quality =  kCGInterpolationHigh;
  CGImageRef imageRef = src.CGImage;
  CGRect destRect = CGRectMake(0, 0, width, height);
  CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
  void *bitmapData = 0; //malloc( 4 * width * height );
  CGContextRef destContext = CGBitmapContextCreate(
    bitmapData,                     // image byte array
    width,                          // width
    height,                         // height
    8,                              // bits-per-component
    4*width,                        // bytes-per-row (is 0 ok?)
    colorSpace,                     // colorSpace
    kCGImageAlphaPremultipliedFirst // bitmapFormat
  );
  CGContextSetInterpolationQuality(destContext, quality);
  CGContextDrawImage(destContext, destRect, imageRef);
  CGImageRef newImageRef = CGBitmapContextCreateImage(destContext);
  MyUIImage *newImage = [UIImage imageWithCGImage:newImageRef];
  CGContextRelease(destContext);
  CGImageRelease(newImageRef);
  CGColorSpaceRelease(colorSpace);
  return [MYLIB_NIL2NULL(newImage) retain];
}

@end

