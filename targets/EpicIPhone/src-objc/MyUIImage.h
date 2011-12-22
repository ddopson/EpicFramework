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

#import "mylib.h"
#import "MyCGRect.h"
#import "MyCGSize.h"
#import "MyCGImage.h"
#import "MyNSData.h"
#import "Iq.h"

// UIImage
//----------------------------------------------------------------------------
typedef UIImage MyUIImage;
@interface UIImage (cat_MyUIImage)
+ (MyUIImage*)imageNamedx_Iq:(Iq*)n1;
+ (MyUIImage*) imageWithContentsOfFilex_Iq :(Iq*)n1;
+ (MyUIImage*) imageWithDatax_MyNSData: (MyNSData*) data;
- (MyUIImage*) stretchableImagexMwMw :(int)leftCapWidth :(int)topCapHeight;
- (MyCGSize*) getSizex;
- (void) drawInRectx_MyCGRect: (MyCGRect*) rect;
- (void) drawAtPointx_MyCGPoint: (MyCGPoint*) point;
- (void) drawAtPointxMwMw :(int)x :(int)y;
- (MyCGImage*) getCGImagex;
- (void) cropImage: (id) dataPtr;
- (MyUIImage *) cropImagexMwMwMwMw: (int) x :(int) y :(int) width :(int) height;
- (MyNSData*) PNGRepresentationx;
- (MyNSData*) JPEGRepresentationxIh:(float) compression;
@end
