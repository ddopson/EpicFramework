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
#import "Reachability.h"

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

+ (void) setAppBadge___int : (int) newCount
{
	[[UIApplication sharedApplication] setApplicationIconBadgeNumber:newCount];
}

+ (void) postToFacebook___java_lang_String : (java_lang_String *) fbMessage
{
    NSLog(@"About to post %@ to FB wall", fbMessage);
    [[[UIApplication sharedApplication] delegate] postToWall: (NSString*) fbMessage];
}

+ (void) requestPurchase___java_lang_String : (java_lang_String *) whichItem
{
    NSLog(@"About to try to buy %@", whichItem);
    [[[UIApplication sharedApplication] delegate] purchaseItem: (NSString*) whichItem];
}

+ (void) launchBrowserTo___java_lang_String : (java_lang_String*) url
{
    NSURL *appStoreUrl = [NSURL URLWithString:url];
    [[UIApplication sharedApplication] openURL:appStoreUrl];
}

+ (void) loginToFacebook__
{
    [[[UIApplication sharedApplication] delegate] doFbLogin];
    NSLog(@"Attempted fb login");
}

+ (void) requestFacebookFriends___java_lang_String : (java_lang_String*) message
{
    [[[UIApplication sharedApplication] delegate] requestFriendsOnFacebook: message];
    NSLog(@"Attempted fb request for friends");
}

+ (int) isNetworkAvailable__
{
    Reachability* networkReachability = [Reachability reachabilityForInternetConnection];
    NetworkStatus networkStatus = [networkReachability currentReachabilityStatus];
    if(networkStatus == NotReachable) {
        return 0;
    } else {
        return 1;
    }
}

+ (java_lang_String*) getUniqueDeviceId__
{
  return [[[UIDevice currentDevice] uniqueDeviceIdentifier] retain];
}

+ (java_lang_String*) getDeviceName__
{
	return [[[UIDevice currentDevice].model] retain];
}

+ (void) runOnUiThread___java_lang_Runnable :(java_lang_Runnable*) callback;
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [callback run__];
  });
}

@end


