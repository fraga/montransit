package org.montrealtransit.android.provider;

import org.montrealtransit.android.Constant;
import org.montrealtransit.android.Utils;
import org.montrealtransit.android.provider.common.AbstractScheduleManager;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class StmSubwayScheduleManager extends AbstractScheduleManager {

	public static final String TAG = StmSubwayScheduleManager.class.getSimpleName();

	public static final String AUTHORITY = "org.montrealtransit.android.schedule.stmsubway";

	public static final Uri CONTENT_URI = Utils.newContentUri(AUTHORITY);

	public static final String PROVIDER_APP_PACKAGE = Constant.PKG; // "org.montrealtransit.android";

	public static void wakeUp(final ContentResolver contentResolver) {
		AbstractScheduleManager.wakeUp(contentResolver, CONTENT_URI);
	}

	public static void ping(final ContentResolver contentResolver) {
		AbstractScheduleManager.ping(contentResolver, CONTENT_URI);
	}

	public static boolean isContentProviderAvailable(Context context) {
		return Utils.isContentProviderAvailable(context, AUTHORITY);
	}

	public static boolean isAppInstalled(Context context) {
		return Utils.isAppInstalled(context, PROVIDER_APP_PACKAGE);
	}
}
