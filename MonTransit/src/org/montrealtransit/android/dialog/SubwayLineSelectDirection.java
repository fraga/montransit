package org.montrealtransit.android.dialog;

import org.montrealtransit.android.MyLog;
import org.montrealtransit.android.R;
import org.montrealtransit.android.Utils;
import org.montrealtransit.android.activity.SubwayLineInfo;
import org.montrealtransit.android.provider.StmManager;
import org.montrealtransit.android.provider.StmStore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * This class handle the subway line direction selection.
 * @author Mathieu M�a
 */
public class SubwayLineSelectDirection implements android.view.View.OnClickListener, android.content.DialogInterface.OnClickListener,
        SubwayLineSelectDirectionDialogListener {

	/**
	 * The log tag.
	 */
	private static final String TAG = SubwayLineSelectDirection.class.getSimpleName();
	/**
	 * The context asking for the dialog.
	 */
	private Context context;
	/**
	 * The class who's going to handle the dialog response.
	 */
	private SubwayLineSelectDirectionDialogListener listener;
	/**
	 * The direction IDs.
	 */
	private String[] orderId;
	/**
	 * The subway line.
	 */
	private StmStore.SubwayLine subwayLine;

	/**
	 * Default constructor that will launch a new activity.
	 * @param context the caller context
	 * @param subwayLineId the bus line number
	 */
	public SubwayLineSelectDirection(Context context, int subwayLineId) {
		MyLog.v(TAG, "subwayLineId:" + subwayLineId);
		this.context = context;
		this.listener = this;
		this.subwayLine = StmManager.findSubwayLine(context.getContentResolver(), subwayLineId);
	}

	/**
	 * This constructor allow the caller to specify which class will manage the answer of the dialog.
	 * @param context the caller context
	 * @param subwayLineId the line number
	 * @param listener the dialog listener
	 */
	public SubwayLineSelectDirection(Context context, int subwayLineId, SubwayLineSelectDirectionDialogListener listener) {
		MyLog.v(TAG, "lineNumber:" + subwayLineId);
		this.subwayLine = StmManager.findSubwayLine(context.getContentResolver(), subwayLineId);
		this.context = context;
		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(View v) {
		MyLog.v(TAG, "onListItemClick()");
		showDialog();
	}

	/**
	 * Show the dialog.
	 */
	public void showDialog() {
		MyLog.v(TAG, "showDialog()");
		getAlertDialog().show();
	}

	/**
	 * @return the dialog.
	 */
	private AlertDialog getAlertDialog() {
		MyLog.v(TAG, "getAlertDialog()");
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		builder.setTitle(this.context.getResources().getString(Utils.getSubwayLineName(this.subwayLine.getNumber())) + " - "
		        + this.context.getResources().getString(R.string.select_subway_direction));
		builder.setItems(getItems(), this);
		builder.setNegativeButton(R.string.cancel, this);
		AlertDialog alert = builder.create();
		return alert;
	}

	private String[] getItems() {
		MyLog.v(TAG, "getItems()");
		StmStore.SubwayStation firstSubwayStationDirection = StmManager.findSubwayLineLastSubwayStation(this.context.getContentResolver(), this.subwayLine
		        .getNumber(), StmStore.SubwayLine.NATURAL_SORT_ORDER);
		//MyTrace.d(TAG, "First station: " + firstSubwayStationDirection.getName());
		StmStore.SubwayStation lastSubwayStationDirection = StmManager.findSubwayLineLastSubwayStation(this.context.getContentResolver(), this.subwayLine
		        .getNumber(), StmStore.SubwayLine.NATURAL_SORT_ORDER_DESC);
		//MyTrace.d(TAG, "Last station: " + lastSubwayStationDirection.getName());

		String[] items = new String[3];
		orderId = new String[3];

		orderId[0] = StmStore.SubwayLine.DEFAULT_SORT_ORDER;
		items[0] = this.context.getResources().getString(R.string.alphabetical_order);
		orderId[1] = StmStore.SubwayLine.NATURAL_SORT_ORDER;
		items[1] = firstSubwayStationDirection.getName();
		orderId[2] = StmStore.SubwayLine.NATURAL_SORT_ORDER_DESC;
		items[2] = lastSubwayStationDirection.getName();
		return items;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		MyLog.v(TAG, "onClick(" + which + ")");
		if (which == -2) {
			dialog.dismiss(); // CANCEL
		} else {
			this.listener.showNewSubway(this.subwayLine.getNumber(), this.orderId[which]);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showNewSubway(int subwayLineId, String orderId) {
		MyLog.v(TAG, "showNewSubway(" + subwayLineId + ", " + orderId + ")");
		Intent mIntent = new Intent(this.context, SubwayLineInfo.class);
		mIntent.putExtra(SubwayLineInfo.EXTRA_LINE_NUMBER, String.valueOf(subwayLineId));
		mIntent.putExtra(SubwayLineInfo.EXTRA_ORDER_ID, orderId);
		this.context.startActivity(mIntent);
	}
}