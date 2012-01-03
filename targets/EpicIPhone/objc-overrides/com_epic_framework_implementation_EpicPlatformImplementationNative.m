#import "com_epic_framework_common_util_EpicFail.h"
#import "com_epic_framework_common_util_exceptions_EpicRuntimeException.h"
#import "org_xmlvm_iphone_UIImage.h"
#import "com_epic_framework_implementation_EpicPlatformImplementationNative.h"
#import "org_xmlvm_iphone_NSObject.h"
#import "java_lang_Runnable.h"
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

@implementation com_epic_framework_implementation_EpicPlatformImplementationNative;

+ (void) setupDebugHandlers__
{
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

+ (java_lang_String*) getUniqueDeviceId__
{
  return [[UIDevice currentDevice] uniqueDeviceIdentifier];
}

+ (void) runOnUiThread___java_lang_Runnable :(java_lang_Runnable*) callback;
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [callback run__];
  });
}

@end

