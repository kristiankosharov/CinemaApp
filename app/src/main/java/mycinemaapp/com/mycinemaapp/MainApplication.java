package mycinemaapp.com.mycinemaapp;

import android.app.Application;
import android.graphics.Bitmap;

import Helpers.ImageCacheManager;
import Helpers.RequestManager;

/**
 * Example application for adding an L1 image cache to Volley. 
 * 
 * @author Trey Robinson
 *
 */
public class MainApplication extends Application {
	
	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	
	private static final String PROPERTY_ID = "UA-57494228-1";
	
	public MainApplication() {
        super();
    }
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void init() {
		RequestManager.init(this);
		createImageCache();
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, ImageCacheManager.CacheType.MEMORY);
	}
	
	  /**
	   * Enum used to identify the tracker that needs to be used for tracking.
	   *
	   * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
	   * storing them all in Application object helps ensure that they are created only once per
	   * application instance.
	   */
//	public enum TrackerName {
//        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
//    }
//
//	  HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
//	  
//	  synchronized Tracker getTracker(TrackerName trackerId) {
//	        if (!mTrackers.containsKey(trackerId)) {
//	 
//	            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//	            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
//	                    .newTracker(R.xml.app_tracker)
//	                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
//	                            .newTracker(PROPERTY_ID) : analytics
//	                            .newTracker(R.xml.global_tracker);
//	            mTrackers.put(trackerId, t);
//	 
//	        }
//	        return mTrackers.get(trackerId);
//	}
}