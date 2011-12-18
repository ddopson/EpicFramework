package com.epic.framework.common.Ui;

//import android.graphics.Bitmap;


public class BitmapHelper {
	/*public static void rleDecompress(int[] dst, int[] rle_src, int src_width, int src_height, int offset, int scanLength, String name) {
		int p_src = 0;
		int row = 0;
		int p_dst = offset;
		int p_dst_row_end = p_dst + src_width;
		int tot_rle_cnt = 0;
		int tot_rle_len = 0;
		int max_rle_len = 0;

		final int MAX_HISTO=64;
		int[] rle_histo = new int[MAX_HISTO + 1];

		while(p_src < rle_src.length) {
			int pixel = rle_src[p_src++];
			if(EpicColor.isMagic(pixel)) {
				if(pixel == EpicColor.MAGIC_IGNORE) {
					p_dst++;
					if(p_dst == p_dst_row_end) {
						p_dst = p_dst + scanLength - src_width;  // first pixel of next line
						p_dst_row_end += scanLength;
						row++;
					}
				}
				else {
					int rle_len = EpicColor.getRleCnt(pixel);
					tot_rle_cnt++;
					tot_rle_len += rle_len;
					max_rle_len = rle_len > max_rle_len ? rle_len : max_rle_len;
					rle_histo[rle_len > MAX_HISTO ? MAX_HISTO : rle_len]++;

					pixel = rle_src[p_src++];
					if(pixel == EpicColor.MAGIC_IGNORE) {
						p_dst += rle_len;
						// now adjust for rows by restoring (p_dst < p_dst_row_end) invariant...
						while(p_dst >= p_dst_row_end) {
							p_dst += scanLength - src_width;
							p_dst_row_end += scanLength;
							row++;
						}
					}
					else {
						for(int k = 0; k < rle_len; k++) {
							dst[p_dst++] = pixel;
							if(p_dst == p_dst_row_end) {
								p_dst += scanLength - src_width;  // first pixel of next line
								p_dst_row_end += scanLength;
								row++;
							}
						}
					}
				}

			}
			else {
				dst[p_dst++] = pixel;
				if(p_dst == p_dst_row_end) {
					p_dst = p_dst + scanLength - src_width;  // first pixel of next line
					p_dst_row_end += scanLength;
					row++;
				}
			}
		}

		int y = (p_dst - offset) / scanLength;
		int x = (p_dst - offset) % scanLength;

		//		EpicLog.i("BitmapHelper.rleDecompress(" + name + ") - "
		//				+ " x=" + x
		//				+ " y=" + y
		//				+ " row=" + row
		//				+ " src_width=" + src_width
		//				+ " src_height=" + src_height
		//				+ " offset=" + offset
		//				+ " scan_length=" + scanLength
		//				+ " tot_rle_cnt=" + tot_rle_cnt
		//				+ " tot_rle_len=" + tot_rle_len
		//				+ " max_rle_len=" + max_rle_len
		//				+ " p_src=" + p_src
		//				+ " p_dst=" + p_dst
		//				+ " pix=" + p_dst - offset - y * (scanLength - src_width)
		//				+ " rle_histo[2]=" + rle_histo[2] + ""
		//				+ " rle_histo[3]=" + rle_histo[3] + ""
		//				+ " rle_histo[4]=" + rle_histo[4] + ""
		//				+ " rle_histo[5]=" + rle_histo[5] + ""
		//				+ " rle_histo[6]=" + rle_histo[6] + ""
		//		);
		EpicFail.assertEqual(x, 0, "BitmapHelper.rleDecompress() - WARNING: x != 0");
		EpicFail.assertEqual(y, src_height, "BitmapHelper.rleDecompress() - WARNING: x != 0");
		EpicFail.assertEqual(row, y, "BitmapHelper.rleDecompress() - WARNING: x != 0");
	}

	//	public static void rleDecompress(Bitmap dst, int[] rle_src, int src_width, int src_height, int x_offset, int y_offset, String name) {
	//		int p_src = 0;
	//		int row = 0;
	//		int x = x_offset;
	//		int y = y_offset;
	//		int x_row_end = x + src_width;
	//		int tot_rle_cnt = 0;
	//		int tot_rle_len = 0;
	//		int max_rle_len = 0;
	//
	//		final int MAX_HISTO=64;
	//		int[] rle_histo = new int[MAX_HISTO + 1];
	//
	//		while(p_src < rle_src.length) {
	//			int pixel = rle_src[p_src++];
	//			if(EpicColor.isMagic(pixel)) {
	//				if(pixel == EpicColor.MAGIC_IGNORE) {
	//					x++;
	//					if(x == x_row_end) {
	//						x = x_offset;  // first pixel of next line
	//						y++;
	//					}
	//				}
	//				else {
	//					int rle_len = EpicColor.getRleCnt(pixel);
	//					tot_rle_cnt++;
	//					tot_rle_len += rle_len;
	//					max_rle_len = rle_len > max_rle_len ? rle_len : max_rle_len;
	//					rle_histo[rle_len > MAX_HISTO ? MAX_HISTO : rle_len]++;
	//
	//					pixel = rle_src[p_src++];
	//					if(pixel == EpicColor.MAGIC_IGNORE) {
	//						x += rle_len;
	//						// now adjust for rows by restoring (p_dst < p_dst_row_end) invariant...
	//						while(x >= x_row_end) {
	//							x -= src_width;  // first pixel of next line
	//							y++;
	//						}
	//
	//					}
	//					else {
	//						for(int k = 0; k < rle_len; k++) {
	//							dst.setPixel(x, y, pixel);
	//							if(x == x_row_end) {
	//								x = x_offset;  // first pixel of next line
	//								y++;
	//							}
	//						}
	//					}
	//				}
	//			}
	//			else {
	//				dst.setPixel(x, y, pixel);
	//				if(x == x_row_end) {
	//					x = x_offset;  // first pixel of next line
	//					y++;
	//				}
	//			}
	//		}
	//
	//		//		EpicLog.i("BitmapHelper.rleDecompress(" + name + ") - "
	//		//				+ " x=" + x
	//		//				+ " y=" + y
	//		//				+ " row=" + row
	//		//				+ " src_width=" + src_width
	//		//				+ " src_height=" + src_height
	//		//				+ " offset=" + offset
	//		//				+ " scan_length=" + scanLength
	//		//				+ " tot_rle_cnt=" + tot_rle_cnt
	//		//				+ " tot_rle_len=" + tot_rle_len
	//		//				+ " max_rle_len=" + max_rle_len
	//		//				+ " p_src=" + p_src
	//		//				+ " p_dst=" + p_dst
	//		//				+ " pix=" + p_dst - offset - y * (scanLength - src_width)
	//		//				+ " rle_histo[2]=" + rle_histo[2] + ""
	//		//				+ " rle_histo[3]=" + rle_histo[3] + ""
	//		//				+ " rle_histo[4]=" + rle_histo[4] + ""
	//		//				+ " rle_histo[5]=" + rle_histo[5] + ""
	//		//				+ " rle_histo[6]=" + rle_histo[6] + ""
	//		//		);
	//		EpicFail.assertEqual(x - x_offset, 0, "BitmapHelper.rleDecompress() - WARNING: x != 0");
	//		EpicFail.assertEqual(y - y_offset, src_height, "BitmapHelper.rleDecompress() - WARNING: x != 0");
	//		//		EpicFail.assertEqual(row, y, "BitmapHelper.rleDecompress() - WARNING: x != 0");
	//	}

	public static void scale_bitmap_expensive(int[] src, int src_width, int src_height, int[] dst, int dst_width, int dst_height) {
		for(int y = 0; y < dst_height; y++) {
			for(int x = 0; x < dst_width; x++) {
				int _ox_12_12 = x * (src_width-1);
				int _oy_12_12 = y * (src_height-1);
				int iox = _ox_12_12 / (dst_width-1);
				int ioy = _oy_12_12 / (dst_height-1);
				int rem_x = _ox_12_12 % (dst_width-1);
				int rem_y = _oy_12_12 % (dst_height-1);
				int arem_x = (dst_width-1)-rem_x;
				int arem_y = (dst_height-1)-rem_y;
				int k = (dst_width - 1)*(dst_height - 1);

				int red = 0;
				int green = 0;
				int blue = 0;
				int alpha = 0;

				//				if((ox > _float(ow-1)) || (oy > _float(oh-1))) {
				//					EpicLog.d("EpicBitmap.scaler - x="+x+" y="+y+ " ox="+ox+" oy="+oy + " arem_x="+arem_x + " arem_y"+arem_y + " scale_x="+scale_x+" scale_y="+scale_y);
				//					if(errors++ > 10) {
				//						throw EpicFail.framework("Ack, bugs again");
				//					}
				//					continue;
				//				}

				try {
					int p0 = src[ioy * src_width + (iox)];
					int a0 = EpicColor.getAlpha(p0);
					int w0 = arem_x * arem_y * a0 >> 4;
			red += EpicColor.getRed(p0) * w0;
			green += EpicColor.getGreen(p0) * w0;
			blue += EpicColor.getBlue(p0) * w0;
			alpha += w0;

			if(rem_x > 0) {
				int p1 = src[(ioy) * src_width + (iox+1)];
				int a1 = EpicColor.getAlpha(p1);
				int w1 = rem_x * arem_y * a1 >> 4;
				red += EpicColor.getRed(p1) * w1;
				green += EpicColor.getGreen(p1) * w1;
				blue += EpicColor.getBlue(p1) * w1;
				alpha += w1;
			}

			if(rem_y > 0) {
				int p2 = src[(ioy+1) * src_width + (iox)];
				int a2 = EpicColor.getAlpha(p2);
				int w2 = arem_x * rem_y * a2 >> 4;
				red += EpicColor.getRed(p2) * w2;
				green += EpicColor.getGreen(p2) * w2;
				blue += EpicColor.getBlue(p2) * w2;
				alpha += w2;
			}

			if(rem_x > 0 && rem_y > 0) {
				int p3 = src[(ioy+1) * src_width + (iox+1)];
				int a3 = EpicColor.getAlpha(p3);
				int w3 = rem_x * rem_y * a3 >> 4;
				red += EpicColor.getRed(p3) * w3;
				green += EpicColor.getGreen(p3) * w3;
				blue += EpicColor.getBlue(p3) * w3;
				alpha += w3;
			}
			if(alpha > 0) {
				red = red / alpha;
				green = green / alpha;
				blue = blue / alpha;
				dst[y*dst_width + x] = EpicColor.fromInts(alpha * 16 / k, red, green, blue);
			}
			else {
				dst[y*dst_width + x] = EpicColor.TRANSPARENT;
			}
			//					if(x == y) {
				//					if(dbg++ < 20) {
			//						EpicLog.d("EpicBitmap.scaler - x="+x+" y="+y+ " ox="+ox+" oy="+oy + " arem_x="+arem_x + " arem_y"+arem_y + " scale_x="+scale_x+" scale_y="+scale_y);
			//						EpicLog.d("EpicBitmap.scaler - p0="+p0+" a0="+a0+" w0="+w0+" red="+red+" green="+green+" blue="+blue+" alpha="+alpha);
			//					}
			//					}
				}
				catch(IndexOutOfBoundsException e) {
					EpicLog.e(e.toString());
					//					EpicLog.d("EpicBitmap.scaler - x="+x+" y="+y+ " ox="+ox+" oy="+oy + " arem_x="+arem_x + " arem_y"+arem_y + " scale_x="+scale_x+" scale_y="+scale_y);
					//					if(errors++ > 10) {
					throw EpicFail.framework("Ack, bugs again");
					//					}
				}
			}
		}
	}
	public static void scale_bitmap_cheap(int[] src, int src_width, int src_height, int[] dst, int dst_width, int dst_height) {
		for(int dst_y = 0; dst_y < dst_height; dst_y++) {
			for(int dst_x = 0; dst_x < dst_width; dst_x++) {
				int src_x = dst_x * src_width / dst_width;
				int src_y = dst_y * src_height / dst_height;
				dst[dst_y * dst_width + dst_x] = src[src_y * src_width + src_x];
			}
		}
	}*/
}

