package com.isotopeneo.mediaboxbeta.request;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.isotopeneo.mediaboxbeta.application.IWatchOnlineApplication;
import com.isotopeneo.mediaboxbeta.util.LoggerClass;

public class BitmapRequestExecutor {

	private ImageDownloaderTask imageDownloaderTask;
	private LruCache<String, Bitmap> imageCache;
	private IWatchOnlineApplication application;
	
	public void downloadAsync(ImageView coverImage, String url, IWatchOnlineApplication application, String key) {
		this.application = application;
		//this.imageCache = application.getImageCache();
		imageDownloaderTask = new ImageDownloaderTask(coverImage, key);
		imageDownloaderTask.execute(url);
		/*if (imageCache.get(key) != null) {
			LoggerClass.log("Image Found in image cache");
			coverImage.setImageBitmap(imageCache.get(key));
		} else*/ {
			try {
				coverImage.setImageBitmap(imageDownloaderTask.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL imageURL = new URL(url);
			URLConnection conn = imageURL.openConnection();
			conn.connect();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.inInputShareable = true;
			
			bm = BitmapFactory.decodeStream(bis, null, options);
			bis.close();
			is.close();
		} catch (Exception e) {
			LoggerClass.log(e.getMessage());
			return null;
		}
		return bm;
	}
	
	class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

		private String url;
		private ImageView coverImage;
		private Object imageTag;
		private final WeakReference<ImageView> imageViewReference;
		private String key;
		
		public ImageDownloaderTask(ImageView coverImage, String key) {
			this.coverImage = coverImage;
			this.imageTag = coverImage.getTag();
			this.imageViewReference = new WeakReference<ImageView>(coverImage);
			this.key = key;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			this.url = params[0];
			return downloadImage();
		}
		
		public Bitmap downloadImage() {
			Bitmap bm = null;
			try {
				URL imageURL = new URL(url);
				URLConnection conn = imageURL.openConnection();
				conn.connect();
				conn.setConnectTimeout(3000);
				conn.setReadTimeout(3000);
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPurgeable = true;
				options.inInputShareable = true;
				
				bm = BitmapFactory.decodeStream(bis, null, options);
				bis.close();
				is.close();
			} catch (Exception e) {
				LoggerClass.log(e.getMessage());
				return null;
			}
			return bm;
		}
		
		public void updateCoverImage(Bitmap bm) {
			if (null != bm) {
				if (imageTag.equals(coverImage.getTag())) {
					coverImage.setImageBitmap(bm);
				}
			}
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (null != result) {
				if (null != imageViewReference) {
					ImageView coverImageView = imageViewReference.get();
					//addBitMapToImageCache(key, result);
					if (null != coverImageView) {
						coverImageView.setImageBitmap(result);
					}
				}
			} else {
				
			}
		}
		
		public void addBitMapToImageCache(String key, Bitmap image) {
			synchronized (imageCache) {
				imageCache.put(key, image);
				//application.setImageCache(imageCache);
			}
		}
		
	}
}
