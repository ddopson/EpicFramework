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

#import "MyFileInputStream.h"
#import "MyFileNotFoundException.h"


@implementation MyFileInputStream

- (void) initMyWith_MyFileInputStreamx_Iq :(Iq*) path
{
	MyFile *fi = [[MyFile alloc] init];
	[fi initMyWith_MyFilex_Iq: path];
	[self initMyWith_MyFileInputStreamx_MyFile: fi];
	[fi release];
}

- (void) initMyWith_MyFileInputStreamx_MyFileDescriptor :(MyFileDescriptor*) fdpar
{
	self->fd = [fdpar retain];
}

- (void) initMyWith_MyFileInputStreamx_MyFile: (MyFile*) f 
{
	NSFileHandle *fdImpl;
	Iq* path = [f getCanonicalPathx];
	NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
	fdImpl = [NSFileHandle fileHandleForReadingAtPath: path];
	[path release];
	if (fdImpl == nil) {
		[pool release];
		MyFileNotFoundException* ex = [[MyFileNotFoundException alloc] init];
		@throw ex;
	}
	self->fd = [[MyFileDescriptor alloc] init];
	[fd initMyWith_MyFileDescriptorx_NSFileHandle: fdImpl];
	[pool release];
}

- (void) dealloc
{
	[self->fd release];
	[super dealloc];
}

- (int) availablex
{
    NSFileHandle* fh = [self->fd getFileHandle];
    unsigned long long offset = [fh offsetInFile];
    NSData* data = [fh availableData];
    [fh seekToFileOffset:offset];
    return [data length];
}

- (int) readx
{
	NSFileHandle* fh = [self->fd getFileHandle];
	NSData *data = [fh readDataOfLength: 1];
	if (data == nil) {
		return -1;
	}
	unsigned char * ptr = (unsigned char *) [data bytes];
	if (ptr == NULL) {
		return -1;
	}	
	int i = ptr[0];
	return i;
}

- (JAVA_LONG) skipxXu: (JAVA_LONG) n
{
	NSFileHandle* fh = [self->fd getFileHandle];
	long initialPos = [fh offsetInFile];
	long newPos = n+initialPos;
	[fh seekToFileOffset: newPos];
	return [fh offsetInFile] - initialPos;
}


- (void) closex
{
	if (self->fd == JAVA_NULL) {
		return;
	}
	
	NSFileHandle* fh = [self->fd getFileHandle];
	[fh closeFile];
}

- (MyFileDescriptor*) getFDx
{
	return [self->fd retain];
}

- (bool) markSupportedx
{
	return true;
}

- (void) markxMw: (int) max
{
	NSFileHandle* fh = [self->fd getFileHandle];
	marked = [fh offsetInFile];
}

- (void) markxXu: (JAVA_LONG) max
{
	NSFileHandle* fh = [self->fd getFileHandle];
	marked = [fh offsetInFile];
}

- (void) resetx
{
	NSFileHandle* fh = [self->fd getFileHandle];
	[fh seekToFileOffset: marked];
}

- (int) readxBr_BxWd :(MYLIBArray*)buf
{
	NSFileHandle* fh = [self->fd getFileHandle];
	int len = [buf count];
	NSData *data = [fh readDataOfLength: len];
	if (data == nil) {
		return -1;
	}
	char * ptr = (char *) [data bytes];
	if (ptr == NULL) {
		return -1;
	} else {
		for (int i = 0; i < [data length]; i++) {
			buf->array.b[i] = ptr[i];
		}
		return [data length];
	}
}	

- (int) readxBr_BxWdMwMw :(MYLIBArray*)buf :(int)offs :(int)len
{
	NSFileHandle* fh = [self->fd getFileHandle];
	NSData *data = [fh readDataOfLength: len];
	if (data == nil) {
		return -1;
	}
	char * ptr = (char *) [data bytes];
	if (ptr == NULL) {
		return -1;
	}	else {
		for (int i = 0; i < [data length]; i++) {
			buf->array.b[offs+i] = ptr[i];
		}
		return [data length];
	}
}	

@end
