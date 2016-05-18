package com.cookie.ui.util.image;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;


public class ImageUtil {

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	public static InputStream compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.WEBP, 70, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 60;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.WEBP, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		return isBm;
	}

	public static InputStream getCompressImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap getCompressImage(String srcPath, int ww, int hh) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}

	public static void writeFile(InputStream ins, String path) {
		try {
			File file = new File(path);
			file.createNewFile();
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param imageUrl
	 *            图片网络路径
	 * @return
	 */
	public static Bitmap getRemoteImage(String image) {
		try {
			URL imageUrl = new URL(image);
			URLConnection conn = imageUrl.openConnection();
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			return bm;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param path
	 *            保存图片路径
	 * @return
	 */
	public static boolean saveBitmap(Bitmap bitmap, String path) {
		File file = new File(path);
		FileOutputStream out;
		try {
			file.createNewFile();
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
				out.flush();
				out.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {
		if (scale == 1.0f) {
			return bitmap;
		} else {
			return Bitmap.createScaledBitmap(bitmap,
					(int) (scale * (float) bitmap.getWidth()),
					(int) (scale * (float) bitmap.getHeight()), true);
		}
	}

	public static Bitmap scaleBitmap(String filePath, float scale) {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		if (bitmap != null)
			return scaleBitmap(bitmap, scale);
		return null;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		// 根据原来图片大小画一个矩形
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		// 圆角弧度参数,数值越大圆角越大,甚至可以画圆形
		final float roundPx = 7;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// 画出一个圆角的矩形
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		// 取两层绘制交集,显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 显示图片
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// 返回Bitmap对象
		return output;
	}

	public static Bitmap getRoundedCornerBitmapBig(Bitmap bitmap) {
		int min = 0;
		if (bitmap.getWidth() < bitmap.getHeight()) {
			min = bitmap.getWidth();
		} else {
			min = bitmap.getHeight();
		}
		Bitmap outBitmap = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, min, min);
		// final RectF rectF = new RectF(rect);
		final float roundPX = min / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		canvas.drawCircle(roundPX, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	public static Bitmap getRoundedCornerBitmapBig2(Bitmap bitmap) {
		int min = 0;
		if (bitmap.getWidth() < bitmap.getHeight()) {
			min = bitmap.getWidth();
		} else {
			min = bitmap.getHeight();
		}
		Bitmap outBitmap = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, min, min);
		final RectF rectF = new RectF(rect);
		final float roundPX = 7;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	public static DisplayImageOptions getHeaderOptions() {
		return new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565)
				.preProcessor(new BitmapProcessor() {

					@Override
					public Bitmap process(Bitmap bitmap) {
						return getRoundedCornerBitmapBig(bitmap);
					}
				}).cacheInMemory(true).cacheOnDisk(true)
//				.showImageOnLoading(R.drawable.ic_header)
//				.showImageOnFail(R.drawable.ic_header)
				.build();
	}

	public static DisplayImageOptions getDefaultOptions() {
		return new DisplayImageOptions.Builder().bitmapConfig(
				Config.RGB_565).build();
	}

//	public static DisplayImageOptions getAdPhotoOptions() {
//		return new DisplayImageOptions.Builder()
//				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
//				.cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//				.showImageForEmptyUri(R.drawable.ic_ad_banner)
//				.showImageOnLoading(R.drawable.ic_active_photo_loading)
//				.showImageOnFail(R.drawable.ic_ad_banner).build();
//	}
}
