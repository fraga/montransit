package org.montrealtransit.android.services;

import java.io.IOException;
import java.util.List;

import org.montrealtransit.android.Constant;
import org.montrealtransit.android.MyLog;
import org.montrealtransit.android.R;
import org.montrealtransit.android.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

/**
 * This task obtain the GPS location of the location name.
 * @author Mathieu Méa
 */
public class GeocodingTask extends AsyncTask<String, String, List<Address>> {

	/**
	 * The log tag.
	 */
	private static final String TAG = GeocodingTask.class.getSimpleName();

	/**
	 * The activity context calling the dialog.
	 */
	private Context context;

	/**
	 * The max number of possible location match to return.
	 */
	private int maxResults;

	/**
	 * This class will process the result.
	 */
	private GeocodingTaskListener listener;

	/**
	 * True if notifying the user.
	 */
	private boolean notify = false;

	/**
	 * Default constructor.
	 * @param context the activity
	 * @param maxResults the max number of results necessary
	 * @param notify true if notifying the user
	 * @param listener the class that will process the result
	 */
	public GeocodingTask(Context context, int maxResults, boolean notify, GeocodingTaskListener listener) {
		this.context = context;
		this.maxResults = maxResults;
		this.listener = listener;
		this.notify = notify;
	}

	@Override
	protected List<Address> doInBackground(String... params) {
		MyLog.v(TAG, "doInBackground()");
		try {
			String locationName = params[0];
			publishProgress(this.context.getString(R.string.geocoding_and_location, locationName));
			// MyLog.v(TAG, "Geocode: " + locationName + ".");
			return new Geocoder(this.context).getFromLocationName(locationName, this.maxResults, Constant.STM_LOWER_LEFT_LAT, Constant.STM_LOWER_LEFT_LNG,
					Constant.STM_UPPER_RIGHT_LAT, Constant.STM_UPPER_RIGHT_LNG);
		} catch (IOException ioe) {
			MyLog.w(TAG, ioe, "INTERNAL ERROR: the network is unavailable or any other I/O problem occurs");
		} catch (Exception e) {
			MyLog.w(TAG, e, "INTERNAL ERROR: unknown problem");
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if (this.notify && values != null && values.length > 0 && values[0] != null) {
			Utils.notifyTheUser(this.context, values[0]);
		}
	}

	@Override
	protected void onPostExecute(List<Address> result) {
		MyLog.v(TAG, "onPostExecute()");
		this.listener.processLocation(result);
	}
}
