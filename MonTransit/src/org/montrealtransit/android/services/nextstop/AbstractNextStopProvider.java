package org.montrealtransit.android.services.nextstop;

import java.util.Map;

import org.montrealtransit.android.MyLog;
import org.montrealtransit.android.data.BusStopHours;
import org.montrealtransit.android.provider.StmStore.BusStop;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Abstract task for next bus stop services.
 * @author Mathieu Méa
 */
public abstract class AbstractNextStopProvider extends AsyncTask<Void, String, Map<String, BusStopHours>> {

	/**
	 * The class that will handle the response.
	 */
	protected/* WeakReference< */NextStopListener/* > */from;
	/**
	 * The class asking for the info.
	 */
	protected Context context;
	/**
	 * The bus stop.
	 */
	protected BusStop busStop;

	/**
	 * Default constructor.
	 * @param context the context
	 * @param from the class asking for the info
	 */
	public AbstractNextStopProvider(Context context, NextStopListener from, BusStop busStop) {
		this.context = context;
		this.from = /* new WeakReference<NextStopListener>( */from/* ) */;
		this.busStop = busStop;
	}

	/**
	 * @return the log tag for the implementation.
	 */
	public abstract String getTag();

	public abstract String getSourceName();

	@Override
	protected void onPostExecute(Map<String, BusStopHours> results) {
		MyLog.v(getTag(), "onPostExecute()");
		// MyLog.d(getTag(), "results null?: " + (results == null));
		if (results == null) {
			return;
		}
		NextStopListener fromWR = this.from;// == null ? null : this.from.get();
		// MyLog.d(getTag(), "fromWR null?: " + (fromWR == null));
		if (fromWR != null) {
			fromWR.onNextStopsLoaded(results);
		}
	}

	@Override
	protected void onProgressUpdate(String... values) {
		MyLog.v(getTag(), "onProgressUpdate()");
		if (values.length <= 0) {
			return;
		}
		NextStopListener fromWR = this.from; // == null ? null : this.from.get();
		// MyLog.d(getTag(), "fromWR null?: " + (fromWR == null));
		if (fromWR != null) {
			fromWR.onNextStopsProgress(values[0]);
		}
		super.onProgressUpdate(values);
	}
}
