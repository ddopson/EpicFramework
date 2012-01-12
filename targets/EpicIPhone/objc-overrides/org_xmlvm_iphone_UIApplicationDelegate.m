/* Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

#import "org_xmlvm_iphone_UIApplicationDelegate.h"

@implementation org_xmlvm_iphone_UIApplicationDelegate

@synthesize facebook;

- (void) __init_org_xmlvm_iphone_UIApplicationDelegate__
{
}

- (void) applicationDidBecomeActive: (org_xmlvm_iphone_UIApplication*) app
{    
	[self applicationDidBecomeActive___org_xmlvm_iphone_UIApplication: app];
}

- (void) applicationDidBecomeActive___org_xmlvm_iphone_UIApplication :(org_xmlvm_iphone_UIApplication*) app
{    
	// Do nothing here
}

- (void) applicationDidFinishLaunching: (UIApplication*) app
{
    if ( [app isKindOfClass:[org_xmlvm_iphone_UIApplication class]] ) {
        SEL appsel = NSSelectorFromString([NSString stringWithFormat:@"__init_%s__", class_getName([app class])]);
        [app performSelector:appsel];
    }
    SEL delsel = NSSelectorFromString([NSString stringWithFormat:@"__init_%s__", class_getName([self class])]);
    [self performSelector:delsel];
    
    facebook = [[Facebook alloc] initWithAppId:@"172905469435543" andDelegate:self];
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    if ([defaults objectForKey:@"FBAccessTokenKey"] && [defaults objectForKey:@"FBExpirationDateKey"]) {
        facebook.accessToken = [defaults objectForKey:@"FBAccessTokenKey"];
        facebook.expirationDate = [defaults objectForKey:@"FBExpirationDateKey"];
    }
    
    // [self doFbLogin];
    
    [self applicationDidFinishLaunching___org_xmlvm_iphone_UIApplication: app];
        
    //[self doFbLogin];
}

- (void) applicationDidFinishLaunching___org_xmlvm_iphone_UIApplication :(org_xmlvm_iphone_UIApplication*) app
{
    // Do nothing here
}

- (void) applicationWillResignActive___org_xmlvm_iphone_UIApplication :(org_xmlvm_iphone_UIApplication*) app
{
    // Do nothing here
}

- (void) applicationWillResignActive: (org_xmlvm_iphone_UIApplication*) app
{
	[self applicationWillResignActive___org_xmlvm_iphone_UIApplication: app];
}

- (void) applicationWillTerminate: (UIApplication*) app
{
    [self applicationWillTerminate___org_xmlvm_iphone_UIApplication: app];
}

- (void) applicationWillTerminate___org_xmlvm_iphone_UIApplication :(org_xmlvm_iphone_UIApplication*) app
{
    // Do nothing here
}

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application
{
	[self applicationDidReceiveMemoryWarning___org_xmlvm_iphone_UIApplication:application];
}

- (void) applicationDidReceiveMemoryWarning___org_xmlvm_iphone_UIApplication :(org_xmlvm_iphone_UIApplication*)application
{
}

- (void)doFbLogin {
    //if (![facebook isSessionValid]) {
        NSLog(@"login requested.");
        [facebook authorize:nil];
    //} else {
    //    NSLog(@"Using stored Facebook credentials.");
    //}    
}

// Pre 4.2 support
- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url {
    return [facebook handleOpenURL:url]; 
}

// For 4.2+ support
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication annotation:(id)annotation {
    NSLog(@"Inside openURL");
    return [facebook handleOpenURL:url]; 
}

- (void)fbDidNotLogin:(BOOL)cancelled {
    NSLog(@"Failed to login");
}

- (void)fbDidLogin {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:[facebook accessToken] forKey:@"FBAccessTokenKey"];
    [defaults setObject:[facebook expirationDate] forKey:@"FBExpirationDateKey"];
    [defaults synchronize];
    
    NSLog(@"Got login, about to request details.");
    dispatch_async(dispatch_get_main_queue(), ^{
        [facebook requestWithGraphPath:@"me" andDelegate:self];
        NSLog(@"Details requested.");
    });
}

- (void)request:(FBRequest *)request didReceiveResponse:(NSURLResponse *)response {
    NSLog(@"didReceiveResponse");
}

- (void)request:(FBRequest *)request didFailWithError:(NSError *)error {
    NSLog(@"didFailWithError");    
}

- (void)request:(FBRequest *)request didLoad:(id)result {
    NSLog(@"didLoad");
    
    if ([result isKindOfClass:[NSArray class]]) {
        NSLog(@"Found array, getting first item...");
        result = [result objectAtIndex:0];
    }
    
    // This callback can be a result of getting the user's basic
    // information or getting the user's permissions.
    if ([result objectForKey:@"username"]) {
        // If basic information callback, set the UI objects to
        // display this.
        NSLog(@"Got username %@", [result objectForKey:@"username"]);
        // [self postToWall:@"Can you beat me in Word Farm?" withCaption:@"My top score is X, can you beat it? Play for free!"];
        
    } else {
        NSLog(@"Username not found.");
    }
}

- (void) postToWall: (NSString*) description withCaption:(NSString*) caption {
    // post to wall
    NSLog(@"Attempting to post to wall...");
    NSMutableDictionary* params = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                                   @"172905469435543", @"app_id",
                                   @"https://developers.facebook.com/docs/reference/dialogs/", @"link",
                                   @"http://fbrell.com/f8.jpg", @"picture",
                                   @"Word Farm", @"name",
                                   caption, @"caption",
                                   description, @"description",
                                   @"",  @"message",
                                   nil];
    
    [facebook dialog:@"feed" andParams:params andDelegate:self];
}

@end

