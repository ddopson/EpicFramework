#!/usr/bin/perl
use strict;
use warnings;
use Carp;

use lib '/opt/local/lib/perl5/site_perl/5.12.3/darwin-multi-2level/';
use lib '/opt/local/lib/perl5/site_perl/5.8.9/darwin-2level/';

#use Image::Magick;
use File::Path;
use File::Copy;

#my $magick = Image::Magick->new();

our $EPIC_IMAGES_FILE   = 'com/epic/resources/EpicImages.java';
our $EPIC_SOUNDS_FILE   = 'com/epic/resources/EpicSounds.java';
our $EPIC_FILES_FILE   = 'com/epic/resources/EpicFiles.java';
our $R_CLASS = 'com.realcasualgames.words.R';

our $RESOURCE_PLAT     = 'RESOURCE_PLAT';
our $RESOURCE_DIR      = 'RESOURCE_DIR';
our $RESOURCE_FILENAME = 'RESOURCE_FILENAME';
our $RESOURCE_NAME     = 'RESOURCE_NAME';
our $RESOURCE_EXT      = 'RESOURCE_EXT';
our $RESOURCE_ISNULL   = 'RESOURCE_ISNULL';

our $IMAGE_PLAT      = $RESOURCE_PLAT;
our $IMAGE_FILENAME  = $RESOURCE_FILENAME;
our $IMAGE_NAME      = $RESOURCE_NAME;
our $IMAGE_EXT       = $RESOURCE_EXT;
our $IMAGE_WIDTH     = 'IMAGE_WIDTH';
our $IMAGE_HEIGHT    = 'IMAGE_HEIGHT';
our $IMAGE_LPAD      = 'IMAGE_LPAD';
our $IMAGE_TPAD      = 'IMAGE_TPAD';
our $IMAGE_RPAD      = 'IMAGE_RPAD';
our $IMAGE_BPAD      = 'IMAGE_BPAD';
our $IMAGE_GLOBALPHA = 'IMAGE_GLOBALPHA';
our $IMAGE_IMAGE_OBJ = 'IMAGE_IMAGE_OBJ';

our $SOUND_PLAT      = $RESOURCE_PLAT;
our $SOUND_FILENAME  = $RESOURCE_FILENAME;
our $SOUND_NAME      = $RESOURCE_NAME;
our $SOUND_EXT       = $RESOURCE_EXT;


our $PLAT_IMAGE_OUTPUT_DIR = 'ImageOutputDir';
our $PLAT_SOUND_OUTPUT_DIR = 'SoundOutputDir';
our $PLAT_CODE_OUTPUT_DIR  = 'CodeOutputDir';
our $PLAT_SOURCE_IMAGE_DIR = 'SourceImageDir';
our $PLAT_SOURCE_SOUND_DIR = 'SourceSoundDir';
our $PLAT_SOURCE_FILES_DIR = 'SourceFilesDir';
our $PLAT_FILES_OUTPUT_DIR = 'FilesOutputDir';
our $PLAT_IS_ANDROID       = 'IsAndroidPlatform';
our $PLAT_PLATFORM_NAME    = 'PlatformName';

sub load_trim_blacklist {
	my %hash = ();
	open my $fd, " < trim-blacklist";
	while(<$fd>) {
		chomp;
		next if /^\s*$/;
		$hash{$_} = 1;
	}
	return \%hash;
}

sub load_desired_sizes {
	my ($filename) = @_;
	open my $fd, " < $filename" or return {};
	my %desired_sizes = ();
	while (<$fd>) {
		next if /^\s*$/;
		chomp;
		my ( $name, $w, $h, $a ) = split;
		$desired_sizes{$name} = {
			$IMAGE_WIDTH     => $w,
			$IMAGE_HEIGHT    => $h,
			$IMAGE_GLOBALPHA => $a
		};
		print "loading desired size for '$name'\n";
	}
	return \%desired_sizes;
}


sub switch {
	my ( $str, $regex, $replace ) = @_;
	$str =~ s/$regex/$replace/;
	return $str;
}


#sub println {
#	my ($line) = @_;
#	chomp $line;
#	print $line, "\n";
#}


sub mkdirs {
	my ($dir) = @_;
	my $d;
	my @dirs = map {$d .= ($d ? '/' : '') . $_; } split '/', $dir;
	pop @dirs; # get rid of filename
	for (@dirs) {
		if( ! -d $_ ) {
			print STDERR "creating dir: '$_'\n";
			mkdir $_;
		}
	}
}

sub write_epic_images_file {
	my ($plat, $images) = @_;
	my $filename = "$plat->{$PLAT_CODE_OUTPUT_DIR}/$EPIC_IMAGES_FILE";
	mkdirs $filename;
	open my $fd, " > $filename" or die "failed to open '$filename' for writing";
	
	our $TYPE = 'EpicBitmap';
	
	print {$fd} "package com.epic.resources;\n";
	print {$fd} "\nimport com.epic.framework.common.Ui.$TYPE;\n";
	if($plat->{$PLAT_IS_ANDROID}) {
		print {$fd} "import $R_CLASS;\n";
	}
	print {$fd} "\npublic class EpicImages {\n\n";
	my @image_names = keys %{ { map { $_->{$RESOURCE_NAME} => 1 } @$images } };
	
	for (@image_names) {
		print {$fd} sprintf(qq(\tpublic static EpicBitmap %-50s = null;\n), $_);
	}

	print {$fd} "\n\tpublic static void init() {\n";
	sub qcomma {
		my ($s, $d) = @_;
		return sprintf("%-${d}s", sprintf('"%s",', $s));
	}
	for (@$images) {
		next if($_->{$RESOURCE_ISNULL});
		print {$fd} sprintf (
			qq(\t\tEpicBitmap.register(%s %s %s %d, %d, %d, %d, %d, %d, %d);\n),
			qcomma($_->{$RESOURCE_NAME}, 50),
			qcomma($_->{$RESOURCE_PLAT}, 10),
			qcomma($_->{$RESOURCE_EXT}, 7),
			($plat->{$PLAT_IS_ANDROID} ? "R.drawable.$_->{$RESOURCE_NAME}" : -1),
			$_->{$IMAGE_WIDTH},
			$_->{$IMAGE_HEIGHT},
			$_->{$IMAGE_LPAD},
			$_->{$IMAGE_TPAD},
			$_->{$IMAGE_RPAD},
			$_->{$IMAGE_BPAD}
		);
	}
	print {$fd} "\n\n";
	for (@image_names) {
		print {$fd} sprintf(qq(\t\tEpicImages.%-50s = EpicBitmap.lookupByName("%s");\n), $_, $_);
	}
	print {$fd} "\n\t}\n";
	print {$fd} "};\n";
}

sub write_epic_files_file {
	my ($plat, $sounds) = @_;
	my $filename = "$plat->{$PLAT_CODE_OUTPUT_DIR}/$EPIC_FILES_FILE";
	mkdirs $filename;
	open my $fd, " > $filename" or die "failed to open '$filename' for writing";
	
	our $TYPE = 'EpicFile';
	
	print {$fd} "package com.epic.resources;\n";
	print {$fd} "\nimport com.epic.framework.common.Ui.$TYPE;\n";
	if($plat->{$PLAT_IS_ANDROID}) {
		print {$fd} "import $R_CLASS;\n";
	}
	print {$fd} "\npublic class ${TYPE}s {\n";
	for (@$sounds) {
		print {$fd} "\tpublic static final $TYPE $_->{$RESOURCE_NAME} = new $TYPE(";
		print {$fd} join (', ',
			qq("$_->{$RESOURCE_FILENAME}"),
#			($plat->{$PLAT_IS_ANDROID} ? "R.raw.$_->{$RESOURCE_NAME}" : -1)
		);
		print {$fd} ");\n";
		
	}
	print {$fd} "};\n";
}

sub scan_resource_dir {
	my ($dir, $plat) = @_;
	my @resources;
	if(ref($dir) eq 'HASH') {
		for my $k (keys %$dir) {
			push @resources, scan_resource_dir($dir->{$k}, $k);
		}
	} else {
		$plat = ($plat || "");
		print "Scanning $dir as '$plat'\n";
		$dir or confess "dir is not defined";
		open my $fd_ls, "ls $dir |";
		while (<$fd_ls>) {
			next if /[.]svn/;
			chomp;
			my $r = { $RESOURCE_FILENAME => $_ };
			my @tokens = split /[.]/;
			if(@tokens == 2) {
				( $r->{$RESOURCE_NAME}, $r->{$RESOURCE_EXT} ) = @tokens;
				$r->{$RESOURCE_DIR} = $dir;
				$r->{$RESOURCE_PLAT} = ($plat || "");
			} else {
				die "unparsable filename: $_";
			}
			push @resources, $r;
		}
	}
	return @resources;
}

sub identify_image {
	my ($filename) = @_;
	open IDENTIFY, "identify $filename |";
	my $line = <IDENTIFY>;
	my ($n, $w,$h,$lw,$lh,$pl,$pt) = ($line =~ /^([^\[]+).* (\d+)x(\d+) (\d+)x(\d+)[+](\d+)[+](\d+)/);
	
	return ($w,$h,$lw,$lh,$pl,$pt);
}

sub process_image {
	my ($plat, $image, $desired_sizes, $trim_blacklist) = @_;
	my $filename = "$image->{$IMAGE_NAME}.$image->{$IMAGE_EXT}";
	my $source_filename = "$image->{$RESOURCE_DIR}/$image->{$IMAGE_FILENAME}";
	my $dest_filename = sprintf("%s/%s_%s.%s", 
		$plat->{$PLAT_IMAGE_OUTPUT_DIR},
		$image->{$RESOURCE_NAME},
		$image->{$RESOURCE_PLAT},
		$image->{$RESOURCE_EXT}
	);
	my $lookup_name = switch ( $filename, '(_[0-9]{3})?.(png|jpg|jpeg)', '' );

	my ($width, $height, $resize_string);
	if ( my $desired_size = $desired_sizes->{$lookup_name} ) {
		$width = $desired_size->{$IMAGE_WIDTH};
		$height = $desired_size->{$IMAGE_HEIGHT};
		$resize_string = "-resize '${width}x${height}!'";
	}
	else {
		$resize_string = "";
		($width, $height) = identify_image($source_filename);
#		print "Warning: $filename does not have a defined desired size.\n";
	}
	if($plat->{resizer} and ! ($source_filename =~ m/icon.png$/)) {
		my $resizer = $plat->{resizer};
		($width, $height) = &$resizer($width, $height);
		$resize_string = "-resize '${width}x${height}!'";
	}
	$image->{$IMAGE_WIDTH} = $width;
	$image->{$IMAGE_HEIGHT} = $height;
	mkdirs $dest_filename;
	
     # DDOPSON-2012-02-20 - adding a fake border prevents trim from deleting solid color non-transparent pixels
     my $trim_str = ($image->{$RESOURCE_EXT} eq 'jpg') ? '' : "-bordercolor transparent -border 1 -trim"; 
	my $strip_str = ($image->{$RESOURCE_EXT} eq 'jpg') ? '-strip' : '';
	my $cmd = "convert $source_filename $resize_string $trim_str $strip_str $dest_filename";
	
	print "Running $cmd\n";	
	`$cmd`;
	
	my ($w,$h,$lw,$lh,$pl,$pt) = identify_image($dest_filename);
     if ($image->{$RESOURCE_EXT} ne 'jpg') {
       $lw -= 2;  # compensate for fake trim border
       $lh -= 2;  # compensate for fake trim border
       $pl -= 1;
       $pt -= 1;
     }
	$image->{$IMAGE_WIDTH} == $lw or die "ACK, something is wrong $lw != $image->{$IMAGE_WIDTH}";
	$image->{$IMAGE_HEIGHT} == $lh or die "ACK, something is wrong $lh != $image->{$IMAGE_HEIGHT}";
#	$image->{$IMAGE_WIDTH} = $w;
#	$image->{$IMAGE_HEIGHT} = $h;
	$image->{$IMAGE_LPAD} = $pl;
	$image->{$IMAGE_TPAD} = $pt;
	$image->{$IMAGE_RPAD} = $lw - $pl - $w;
	$image->{$IMAGE_BPAD} = $lh - $pt - $h;	
}

sub println {
	my ($fd, $line) = @_;
	print {$fd} "$line\n";
}


sub process_sounds {
	my ($source, $output, $gen, $is_android) = @_;
	my @sounds = scan_resource_dir($source);
	
	my $TYPE = 'EpicSound';
	my $filename = "$gen/com/epic/resources/EpicSounds.java";
	print "Filename=$filename\n";
	mkdirs $filename;
	mkdirs $output . '/f';
	
	open my $fd, " > $filename" or die "failed to open '$filename' for writing";
	
	println $fd, "package com.epic.resources;";
	println $fd, "\nimport com.epic.framework.common.Ui.$TYPE;";
	if($is_android) {
		println $fd, "import $R_CLASS;";
	}
	println $fd, "\npublic class ${TYPE}s {";
	for (@sounds) {
		my $target = $_->{$RESOURCE_DIR} . '/' . $_->{$RESOURCE_FILENAME};
		my $lfile = $output . '/' .  $_->{$RESOURCE_FILENAME};

		print sprintf("sound: %s ==> %s\n", $target, $lfile);
		copy($target,  $lfile);
		
		my $R = ($is_android ? "R.raw.$_->{$RESOURCE_NAME}" : -1);
		println $fd, qq(\tpublic static final $TYPE $_->{$RESOURCE_NAME} = new $TYPE("$_->{$RESOURCE_FILENAME}", $R););
	}
	println $fd, "};";
}

sub process_images {
	my ($plat) = @_;
	# Images
	my @images = scan_resource_dir($plat->{$PLAT_SOURCE_IMAGE_DIR});
	my $desired_sizes = load_desired_sizes("desired-sizes-$plat->{$PLAT_PLATFORM_NAME}.tbl");
	my $trim_blacklist = load_trim_blacklist();
	for my $image (@images) {
#		load_image($plat, $image);
		process_image($plat, $image, $desired_sizes, $trim_blacklist);
	}
	write_epic_images_file($plat, \@images);
}

sub process_files {
	my ($plat) = @_;
	my @files = scan_resource_dir($plat->{$PLAT_SOURCE_FILES_DIR});
	mkdirs $plat->{$PLAT_FILES_OUTPUT_DIR} . '/f';
	for (@files) {
		my $target = $_->{$RESOURCE_DIR} . '/' . $_->{$RESOURCE_FILENAME};
		my $lfile = $plat->{$PLAT_FILES_OUTPUT_DIR} . '/' .  $_->{$RESOURCE_FILENAME};

		print sprintf("File: %s ==> %s\n", $target, $lfile);
		copy($target,  $lfile) or die $!;
	}
	write_epic_files_file($plat, \@files);
	
}

our %PLATFORMS = (
#	blackberry => {
#		$PLAT_PLATFORM_NAME    => 'blackberry',
#		$PLAT_SOURCE_IMAGE_DIR => 'source-images',
#		$PLAT_SOURCE_SOUND_DIR => 'source-sounds',
#		$PLAT_SOURCE_FILES_DIR => 'source-files',
#		$PLAT_CODE_OUTPUT_DIR  => 'output.new/blackberry/gen',
#		$PLAT_IMAGE_OUTPUT_DIR => 'output.new/blackberry/images',
#		$PLAT_FILES_OUTPUT_DIR => 'output.new/blackberry/files',
#		$PLAT_SOUND_OUTPUT_DIR => 'output.new/blackberry/sound',
#		$PLAT_IS_ANDROID => 0,	
#		resizer => sub {
#			my ($width, $height) = @_;
#			return (int($width * 480 / 800), int($height * 360 / 480));
#		}
#	},
	android => {
		$PLAT_PLATFORM_NAME    => 'android',
		$PLAT_SOURCE_IMAGE_DIR => {
			'android' => 'source-images/android'
		},
		$PLAT_SOURCE_SOUND_DIR => 'source-sounds',
		$PLAT_SOURCE_FILES_DIR => 'source-files',
		$PLAT_CODE_OUTPUT_DIR  => 'output.new/android/gen',
		$PLAT_IMAGE_OUTPUT_DIR => 'output.new/android/res/drawable',
		$PLAT_SOUND_OUTPUT_DIR => 'output.new/android/res/raw',
		$PLAT_FILES_OUTPUT_DIR => 'output.new/android/assets',
		$PLAT_IS_ANDROID => 1,
	},
	ios => {
		$PLAT_PLATFORM_NAME    => 'ios',
		$PLAT_SOURCE_IMAGE_DIR => {
			'ipad' => 'source-images/ipad',
			'iphone' => 'source-images/iphone'
		},
		$PLAT_SOURCE_SOUND_DIR => 'source-sounds',
		$PLAT_SOURCE_FILES_DIR => 'source-files',
		$PLAT_CODE_OUTPUT_DIR  => 'output.new/ios/gen',
		$PLAT_IMAGE_OUTPUT_DIR => 'output.new/ios/resources',
		$PLAT_SOUND_OUTPUT_DIR => 'output.new/ios/resources',
		$PLAT_FILES_OUTPUT_DIR => 'output.new/ios/resources',
		$PLAT_IS_ANDROID => 0,
	}
);

sub main {
	for my $plat (values %PLATFORMS) {
		print "Platform Configuration:\n", join '', map { "\t$_ = $plat->{$_}\n" } sort keys %$plat;
		
		# Images
		process_images($plat);
	
		# Sounds
		process_sounds($plat->{$PLAT_SOURCE_SOUND_DIR}, $plat->{$PLAT_SOUND_OUTPUT_DIR}, $plat->{$PLAT_CODE_OUTPUT_DIR}, $plat->{$PLAT_IS_ANDROID});

		# Files
		process_files($plat);	
	}
}

main();
rmtree 'output';
rename 'output.new', 'output';
