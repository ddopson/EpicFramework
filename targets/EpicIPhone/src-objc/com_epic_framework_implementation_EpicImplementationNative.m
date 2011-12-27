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

static void segfault_handler(int sig, siginfo_t *si, void *unused) {
  void    *array[32]; // Array to store backtrace symbols
  size_t  size;       // To store the size of the stack backtrace
  char    sbuff[128];
  int     n;          // chars written to buffer
  int     fd;
  time_t  now;
  int     pid;

  // Construct a filename
  time(&now);
  pid = getpid();
  snprintf(sbuff, sizeof(sbuff), "stacktrace-%d-%d.log", (int)now, pid );

  // Open the File
  fd = open(sbuff, O_CREAT | O_APPEND | O_WRONLY, S_IRUSR | S_IRGRP | S_IROTH);
  // Write the header line
  n = snprintf(sbuff, sizeof(sbuff), "PID %d received SIG %d for address: 0x%lx\n", pid, sig, (long) si->si_addr);
  if(fd > 0) write(fd, sbuff, n);
  write(STDERR_FD, sbuff, n);

  // Write the Backtrace
  size = backtrace(array, 32);
  if(fd > 0) backtrace_symbols_fd(array, size, fd);
  backtrace_symbols_fd(array, size, STDERR_FD);

  // Exit violently
  close(fd);
  exit(-1);
}

@implementation com_epic_framework_implementation_EpicImplementationNative;

+ (void) setupDebugHandlersx {
  NSLog(@"Initializing Debug Handlers to cat SIGSEGV / SIGTRAP");
  struct sigaction sa;
  memset(&sa, 0, sizeof(struct sigaction));
  sigemptyset(&sa.sa_mask);
  sa.sa_sigaction = segfault_handler;
  sa.sa_flags   = SA_SIGINFO;
  sigaction(SIGSEGV, &sa, NULL);
  sigaction(SIGTRAP, &sa, NULL);
  sigaction(SIGSTOP, &sa, NULL);
  sigaction(SIGQUIT, &sa, NULL);
  sigaction(SIGHUP, &sa, NULL);
  sigaction(SIGINT, &sa, NULL);
  sigaction(SIGFPE, &sa, NULL);
  sigaction(SIGTERM, &sa, NULL);
  sigaction(SIGILL, &sa, NULL);
  sigaction(SIGBUS, &sa, NULL);
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
  UIImage *newImage = [UIImage imageWithCGImage:newImageRef];
  CGContextRelease(destContext);
  CGImageRelease(newImageRef);
  CGColorSpaceRelease(colorSpace);
  return [MYLIB_NIL2NULL(newImage) retain];
}

@end

