/* Copyright (c) 2002-2011 by MYLIB.org
 *
 * Project Info:  http://www.mylib.org
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

#import "MyUIImage.h"

@interface CroppedImageArgs : NSObject {
@public	CGRect cropRect;
@public	UIImage* croppedImage;
}
@end

@implementation CroppedImageArgs
@end


// UIImage
//----------------------------------------------------------------------------
@implementation UIImage(cat_MyUIImage);

+ (MyUIImage*)imageNamedx_Iq:(Iq*)path
{
    path=MYLIB_NULL2NIL(path);
	return_MYLIB_SELECTOR(UIImage imageNamed:path)
}

+ (MyUIImage*) imageWithContentsOfFilex_Iq :(Iq*)path
{
    path=MYLIB_NULL2NIL(path);
    UIImage * img = [[UIImage alloc] initWithContentsOfFile:path];
    img=MYLIB_NIL2NULL(img);
    return img;
}

+ (MyUIImage*) imageWithDatax_MyNSData: (MyNSData*) data
{
	return [[UIImage alloc] initWithData:data];
	//return_MYLIB_SELECTOR(UIImage imageWithData:data)
}

- (MyUIImage*) stretchableImagexMwMw :(int)leftCapWidth :(int)topCapHeight
{
	return_MYLIB(stretchableImageWithLeftCapWidth:leftCapWidth topCapHeight:topCapHeight)
}

- (MyCGSize*) getSizex
{
    MyCGSize* s = [[MyCGSize alloc] init];
    s->widthIh = [self size].width;
    s->heightIh = [self size].height;
    return s;
}

- (void) drawInRectx_MyCGRect: (MyCGRect*) rect
{
    CGRect r = [rect getCGRect];
    [self drawInRect: r];
}

- (void) drawAtPointxMwMw :(int)x :(int)y
{
	CGPoint p = CGPointMake(x,y);
	p.x = x;
	p.y = y;
	[self drawAtPoint: p];
}

- (MyCGImage*) getCGImagex
{
	MyCGImage* retval = [[MyCGImage alloc] init];
	retval->image = [self CGImage];

	return retval;
}

/*
 * We perform the cropping on the main thread in case the cropping is
 * done in a thread. Quartz is not thread-safe.
 */
- (void) cropImage: (id) args
{
	CGRect cropRect = ((CroppedImageArgs*) args)->cropRect;
	CGSize size = cropRect.size;
	UIGraphicsBeginImageContext(size);
	CGContextRef context = UIGraphicsGetCurrentContext();
	CGImageRef subImage = CGImageCreateWithImageInRect([self CGImage], cropRect);
	CGRect myRect = CGRectMake(0.0f, 0.0f, size.width, size.height);
	CGContextScaleCTM(context, 1.0f, -1.0f);
	CGContextTranslateCTM(context, 0.0f, -size.height);
	CGContextFlush(context);
	CGContextDrawImage(context, myRect, subImage);
	CGContextFlush(context);
	UIImage* croppedImage = UIGraphicsGetImageFromCurrentImageContext();
	UIGraphicsEndImageContext();
	[croppedImage retain];
	CGImageRelease(subImage);
	((CroppedImageArgs *) args)->croppedImage = croppedImage;
}

- (MyUIImage *) cropImagexMwMwMwMw: (int) x :(int) y :(int) width :(int) height
{
	CroppedImageArgs* args = [[CroppedImageArgs alloc] init];
	args->cropRect = CGRectMake(x, y, width, height);
	[self performSelectorOnMainThread:@selector(cropImage:) withObject:args waitUntilDone:TRUE];
	UIImage* croppedImage = args->croppedImage;
	[args release];
	return croppedImage;
}

- (MyNSData*) PNGRepresentationx
{
	NSData * data = UIImagePNGRepresentation(self);
	return [data retain];
}

- (MyNSData*) JPEGRepresentationxIh:(float) compression
{
	NSData * data = UIImageJPEGRepresentation(self, compression);
	return [data retain];
}


@end
